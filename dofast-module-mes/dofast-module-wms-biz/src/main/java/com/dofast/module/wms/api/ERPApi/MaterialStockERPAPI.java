package com.dofast.module.wms.api.ERPApi;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.dofast.framework.common.util.http.HttpUtils;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.controller.admin.interfacelog.vo.InterfaceLogCreateReqVO;
import com.dofast.module.mes.service.interfacelog.InterfaceLogService;
import com.dofast.module.system.api.dict.DictDataApi;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.system.dal.dataobject.dict.DictDataDO;
import com.dofast.module.system.service.dict.DictDataService;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MaterialStockERPAPI {

    @Resource
    private InterfaceLogService interfaceLogService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @Resource
    private DictDataApi dictDataApi;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private DictDataService dictDataService;

    /**
     * 采购收货/入库/仓退单生成接
     */
    public String purchaseDeliveryCreate(Map<String, Object> params) {
        // ，根据环境选择
        Map<String, Object> request = null;
        String pmds000 = (String) params.get("pmds000");// 单据类型 1：一般采购收货；2：无采购收货；3：采购收货入库单；6：一般采购入库；7：一般采购仓退；8：委外采购收货；12：委外采购入库；14：委外采购仓退；20：委外采购收货入库

        if("7".equals(pmds000)){
            request = createBaseRequest("Apmt580Upd");
        }else{
            request = createBaseRequest("PurchaseDeliveryCreate");
        }
        String poNo = params.get("poNo").toString(); // 采购单号
        // String ejectNo = Optional.ofNullable((String) params.get("ejectNo")).orElse(null); // 退料单号
        String sourceNo = (String) params.get("sourceNo"); // MES单头
        String supplierCode = (String) params.get("supplierCode"); // 供应商编码
        Integer wareHouseId = (Integer) params.get("warehouseId"); // 仓库Id
        Integer locationId = (Integer) params.get("locationId"); // 库区Id
        Integer areaId = (Integer) params.get("areaId"); //库位Id
        List<Map<String, Object>> goodsList = (List<Map<String, Object>>) params.get("goodsList"); // 入库商品详情
        String warehousingCode = "";
        String retreatCode = "";
        if("7".equals(pmds000)){
            retreatCode = (String) params.get("retreatCode");
        }
        StorageLocationDO storageLocationDO = null;
        StorageAreaDO storageAreaDO = null;
        if("6".equals(pmds000)){
            warehousingCode = String.valueOf(params.get("warehousingCode")); // 入库单号
            // 在入库时, 使用库区库位
            storageLocationDO = storageLocationService.getStorageLocation(locationId.longValue());
            storageAreaDO = storageAreaService.getStorageArea(areaId.longValue());
        }
        //String pmds000 = null;
        /*if(poNo!=null){
            pmds000 = "6"; // 一般采购入库
        }else{
            pmds000 = "7";
        }*/
        //List<Map<String, Object>> details = (List<Map<String, Object>>) params.get("details");
        List<Map<String, Object>> details = new ArrayList<>();
        int i = 1;
        for (Map<String, Object> good : goodsList) {
            Map<String, Object> detail = new HashMap<>();
            if("1".equals(pmds000)){
                detail.put("pmdt001", poNo); // 采购单号
                detail.put("pmdt002", good.get("consequence")); // 项次
                detail.put("pmdt003", good.get("purchaseConsequence")); // 采购项序
                detail.put("pmdt004", good.get("purchaseBatchConsequence")); // 采购分批序
                detail.put("pmdt027", ""); // 收货单号
                detail.put("pmdt028", ""); // 收获项次
                detail.put("pmdt016", ""); // 仓库
                detail.put("pmdt017", ""); // 库位
                detail.put("pmdt051", ""); // 理由码
            }else if("6".equals(pmds000)){
                detail.put("pmdt001", ""); // 采购单号
                detail.put("pmdt002", ""); // 采购项次
                detail.put("pmdt003", ""); // 采购项序
                detail.put("pmdt004", ""); // 采购分批序
                detail.put("pmdt027", warehousingCode); // 收货单号
                detail.put("pmdt028", good.get("consequence")); // 收获项次
                detail.put("pmdt016", storageLocationDO.getLocationCode()); // 仓库
                detail.put("pmdt017", storageAreaDO.getAreaCode()); // 库位
                detail.put("pmdt051", ""); // 理由码
            }else if("7".equals(pmds000)){
                detail.put("pmdt001", good.get("poNo")); // 采购单号
                detail.put("pmdt002", good.get("consequence")); // 项次
                detail.put("pmdt003", good.get("purchaseConsequence")); // 采购项序
                detail.put("pmdt004", good.get("purchaseBatchConsequence")); // 采购分批序
                detail.put("pmdt027", good.get("warehousingCode")); // 收货单号
                detail.put("pmdt028", good.get("warehousingSeq")); // 收获项次
                detail.put("pmdt016", good.get("locationCode")); // 库区
                detail.put("pmdt017", good.get("areaCode")); // 库位
                detail.put("pmdt051", good.get("reasonCode")); // 理由码
            }
            detail.put("pmdt006", good.get("goodsNumber")); // 料件
            detail.put("pmdt007", " "); // 特性
            detail.put("pmdt019", good.get("unitOfMeasure")); // 单位
            detail.put("pmdt020", good.get("receiveNum")); // 数量
            detail.put("pmdt025", "1"); // 紧急标识
            detail.put("pmdt026", "1"); // 检验标识
            detail.put("pmdt018", good.get("batchCode")); // 批号
            detail.put("pmdt036", 0); // 单价
            detail.put("source_seq", i);
            details.add(detail);
            i++;
        }
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> stdData = new HashMap<>();
        Map<String, Object> parameter = new HashMap<>();
        // 根据 pmds000 的值动态构建 master 或 pmds_info
        if (Arrays.asList("1", "2", "8").contains(pmds000)) { // 收货单
            parameter.put("master", createMaster(poNo, sourceNo, pmds000, details, supplierCode));
        } else if (Arrays.asList("3", "6", "12", "20").contains(pmds000)) { // 入库单
            parameter.put("master", createPmdsInfo(poNo, warehousingCode, pmds000, details, supplierCode));
        } else if (Arrays.asList("7", "14").contains(pmds000)) { // 仓退单
            parameter.put("master", createMaster(retreatCode, sourceNo, pmds000, details, supplierCode));
        }

        stdData.put("parameter", parameter);
        payload.put("std_data", stdData);
        request.put("payload", payload);
        System.out.println(JSONObject.toJSONString(request));

        String result = HttpUtils.doPost("http://192.168.127.7/wstopprd/ws/r/awsp920", JSONObject.toJSONString(request)); // 正式区

        //String result = HttpUtils.doPost("http://192.168.127.7/wtoptst/ws/r/awsp920", JSONObject.toJSONString(request)); // 测试区
        // 记录操作日志
        InterfaceLogCreateReqVO log = new InterfaceLogCreateReqVO();
        if(pmds000.equals("1")){
            log.setInterfaceName("采购收货接口");
        }else if(pmds000.equals("6")){
            log.setInterfaceName("采购入库接口");
        }else if (pmds000.equals("7")) {
            log.setInterfaceName("采购仓退接口");
        }
        log.setReceiver("ERP");
        log.setRequester("MES");
        log.setRequestType("POST");
        log.setRequestMap(JSONObject.toJSONString(request));
        log.setResultMap(result);
        interfaceLogService.createInterfaceLog(log);
        // 将String转为Json对象, 获取对象内code字段的值

        JSONObject jsonObject = JSONObject.parseObject(result);
        // 防止jsonObject.getString("code")空指针异常
        if (jsonObject == null) {
            return "error";
        }

        // 提取执行状态信息
        JSONObject reqPayload = jsonObject.getJSONObject("payload");
        JSONObject reqStdData = payload != null ? reqPayload.getJSONObject("std_data") : null;
        JSONObject reqExecution = stdData != null ? reqStdData.getJSONObject("execution") : null;
        String code = reqExecution != null ? reqExecution.getString("code") : null;
        String description = reqExecution != null ? reqExecution.getString("description") : null;

        if ("0".equals(code)) {
            // 若当前为采购收货, 则解析ERP回传的收货单号用于绑定入库单号
            if(pmds000.equals("1")){
                // 解析入库单号（source_no）
                JSONObject reqParameter = stdData != null ? reqStdData.getJSONObject("parameter") : null;
                JSONArray successReturn = parameter != null ? reqParameter.getJSONArray("success_return") : null;
                String reqWarehousingCode = null;
                if (successReturn != null && !successReturn.isEmpty()) {
                    JSONObject firstSuccess = successReturn.getJSONObject(0);
                    reqWarehousingCode = firstSuccess != null ? firstSuccess.getString("success_msg") : null; // 获取到的收货单号
                }
                // 示例：记录日志或处理sourceNo
                if (reqWarehousingCode != null) {
                    System.out.println("解析到的source_no: " + reqWarehousingCode);
                    return "success,"+reqWarehousingCode; // 返回sourceNo
                } else {
                    return "ERROR: 未找到ERP入库单号!";
                }
            }
            // 其余情况无需解析
            return "success";
        } else {
            return description != null ? description : "error";
        }

        //return "error";
    }

    /**
     * 调拨单生成接口
     */
    public String requisitionNoteCreate(Map<String, Object> params) {
        Map<String, Object> request = createBaseRequest("RequisitionNoteCreate");

        // 构建master
        List<Map<String, Object>> masterList = new ArrayList<>();
        Map<String, Object> master = new HashMap<>();
        master.put("source_no", params.get("allocatedId")); // MES单头Id
        master.put("indcdocno", ""); // 单别

        DictDataDO dataDO = dictDataService.getDictData("erp_close_date", "close_date");
        String closeDateStr = null;
        Date closeDateObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (dataDO != null) {
            closeDateStr = dataDO.getLabel();
            if (closeDateStr != null && !closeDateStr.isEmpty()) {
                try {
                    closeDateObj = sdf.parse(closeDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        boolean isCloseDateLastMonth = false;
        if (closeDateObj != null) {
            Calendar closeDateCal = Calendar.getInstance();
            closeDateCal.setTime(closeDateObj);

            Calendar lastMonthCal = Calendar.getInstance();
            lastMonthCal.add(Calendar.MONTH, -1);

            isCloseDateLastMonth = (closeDateCal.get(Calendar.YEAR) == lastMonthCal.get(Calendar.YEAR))
                    && (closeDateCal.get(Calendar.MONTH) == lastMonthCal.get(Calendar.MONTH));
        }

        // 设置过账日期（sfda001）
        if (isCloseDateLastMonth) {
            // 关账日期为上个月，直接使用当前日期
            master.put("indcdocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } else {
            // 2025-6-26 若当前存在关账日期, 则直接传递下个月
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            master.put("indcdocdt", sdf.format(calendar.getTime())); // 单据日期
        }


        // master.put("indcdocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // 单据日期 默认获取当日日期
        master.put("indc000", "1"); // 单据性质
        master.put("indc002", "1"); // 来源类别
        //获取当前用户信息
        // 获取当前用户信息
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        master.put("indc004", adminUserRespDTO.getUsername()); // 调拨人员-传递ERP编码
        //master.put("indc004", "tiptop"); // 调拨人员-测试账户

        master.put("indc005", ""); // 拨出据点
        master.put("indc006", "AM01"); // 拨入据点
        master.put("indc008", ""); // 备注
        master.put("indc102", "1"); // 检验否

        // 构建details
        List<Map<String, Object>> details = new ArrayList<>();
        List<Map<String, Object>> goodsList = (List<Map<String, Object>>) params.get("allocatedList"); // 商品详情
        for (Map<String, Object> good : goodsList) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("indd101", ""); // 来源单号
            detail.put("indd001", ""); // 来源项次
            detail.put("indd002", good.get("itemCode")); // 料号
            detail.put("indd004", ""); // 产品特征
            detail.put("indd006", good.get("unitOfMeasure")); // 库存单位
            detail.put("indd022", good.get("locationCode")); // 拨出库位
            detail.put("indd023", good.get("areaCode")); // 拨出储位
            detail.put("indd024", good.get("batchCode")); // 拨出批号
            detail.put("indd032", params.get("inLocationCode")); // 拨入库位
            detail.put("indd033", params.get("inAreaCode")); // 拨入储位
            detail.put("indd034", good.get("batchCode")); // 拨入批号
            detail.put("indd102", ""); // 库存管理特征
            detail.put("indd103", good.get("quantityAllocated")); // 申请数量
            detail.put("source_seq", ""); // MES项次
            details.add(detail);
        }
        master.put("detail", details); // 将details放入master
        masterList.add(master); // 将master放入masterList

        // 构建payload
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> stdData = new HashMap<>();
        Map<String, Object> masterData = new HashMap<>();
        masterData.put("master", masterList);
        stdData.put("parameter", masterData); // 使用masterList
        payload.put("std_data", stdData);

        request.put("payload", payload);
        System.out.println(JSONObject.toJSONString(request));
        String result = HttpUtils.doPost("http://192.168.127.7/wstopprd/ws/r/awsp920", JSONObject.toJSONString(request)); // 正式区
        // String result = HttpUtils.doPost("http://192.168.127.7/wtoptst/ws/r/awsp920", JSONObject.toJSONString(request)); // 测试区
        // 记录操作日志
        InterfaceLogCreateReqVO log = new InterfaceLogCreateReqVO();
        log.setInterfaceName("调拨单生成接口");
        log.setReceiver("ERP");
        log.setRequester("MES");
        log.setRequestType("POST");
        log.setRequestMap(JSONObject.toJSONString(request));
        log.setResultMap(result);
        interfaceLogService.createInterfaceLog(log);

        JSONObject jsonObject = JSONObject.parseObject(result);
        // 防止jsonObject.getString("code")空指针异常
        if (jsonObject == null) {
            return "error";
        }
        // 提取执行状态信息
        JSONObject reqPayload = jsonObject.getJSONObject("payload");
        JSONObject reqStdData = payload != null ? reqPayload.getJSONObject("std_data") : null;
        JSONObject reqExecution = stdData != null ? reqStdData.getJSONObject("execution") : null;
        String code = reqExecution != null ? reqExecution.getString("code") : null;
        String description = reqExecution != null ? reqExecution.getString("description") : null;

        if ("0".equals(code)) {
            return description != null ? description : "error";
        }
        return result;
    }

    private Map<String, Object> createBaseRequest(String interfaceName) {
        String ipAddress = "172.18.12.250";
        Map<String, Object> request = new HashMap<>();
        request.put("key", "f5458f5c0f9022db743a7c0710145903");
        request.put("type", "sync");
        Map<String, Object> hostInfo = new HashMap<>();
        hostInfo.put("prod", "MES");
        hostInfo.put("ip", ipAddress); // 替换为实际的IP地址
        hostInfo.put("lang", "zh_CN");
        // hostInfo.put("acct", "tiptop"); // 后续替换为实际的用户
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        hostInfo.put("acct", adminUserRespDTO.getUsername());

        hostInfo.put("timestamp", String.valueOf(System.currentTimeMillis()));
        request.put("host", hostInfo);

        Map<String, Object> serviceInfo = new HashMap<>();
        serviceInfo.put("prod", "T100");
        serviceInfo.put("name", interfaceName);
        serviceInfo.put("ip", "192.168.127.7");
        serviceInfo.put("id", "topprd"); // topprd正式库 toptst测试库
        request.put("service", serviceInfo);

        Map<String, Object> dataKey = new HashMap<>();
        dataKey.put("EntId", Constant.ERP_PROD_DODE);

        dataKey.put("CompanyId", "AM01");
        request.put("datakey", dataKey);

        return request;
    }

    /**
     * 出货单生成接口
     */
    public String deliveryOrderCreate(Map<String, Object> params) {
        Map<String, Object> request = createBaseRequest("DeliveryOrderCreate");

        // 构建details
        List<Map<String, Object>> details = new ArrayList<>();
        List<Map<String, Object>> goodsList = (List<Map<String, Object>>) params.get("goodsList"); // 商品详情
        for (Map<String, Object> good : goodsList) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("source_seq", good.get("source_seq")); // MES项次
            detail.put("xmdl001", good.get("xmdl001")); // 出通单号
            detail.put("xmdl002", good.get("xmdl002")); // 出通项次
            detail.put("xmdl003", good.get("xmdl003")); // 订单单号
            detail.put("xmdl004", good.get("xmdl004")); // 订单项次
            detail.put("xmdl005", good.get("xmdl005")); // 订单项序
            detail.put("xmdl006", good.get("xmdl006")); // 分批序
            detail.put("xmdl008", good.get("xmdl008")); // 料件
            detail.put("xmdl017", good.get("xmdl017")); // 单位
            detail.put("xmdl018", good.get("xmdl018")); // 数量
            detail.put("xmdl024", good.get("xmdl024")); // 单价
            detail.put("xmdl023", good.get("xmdl023")); // 检验否
            detail.put("xmdl007", good.get("xmdl007")); // 子件特性
            detail.put("xmdl030", good.get("xmdl030")); // 项目编号
            detail.put("xmdl014", good.get("xmdl014")); // 仓库
            detail.put("xmdl015", good.get("xmdl015")); // 储位
            detail.put("xmdl016", good.get("xmdl016")); // 批号
            detail.put("xmdl051", good.get("xmdl051")); // 备注
            details.add(detail);
        }

        // 构建master
        Map<String, Object> master = new HashMap<>();
        master.put("xmdkdocno", params.get("xmdkdocno")); // 单别
        master.put("xmdkdocdt", params.get("xmdkdocdt")); // 单据日期
        master.put("xmdk000", params.get("xmdk000")); // 单据性质
        master.put("xmdk002", params.get("xmdk002")); // 出货性质
        master.put("xmdk003", params.get("xmdk003")); // 业务人员
        master.put("xmdk004", params.get("xmdk004")); // 业务部门
        master.put("xmdk009", params.get("xmdk009")); // 送货客户
        master.put("xmdk008", params.get("xmdk008")); // 收款客户
        master.put("xmdk202", params.get("xmdk202")); // 发票客户
        master.put("xmdk021", params.get("xmdk021")); // 送货地址编号
        master.put("xmdk007", params.get("xmdk007")); // 客户编号
        master.put("xmdk039", params.get("xmdk039")); // 在途成本仓
        master.put("xmdk040", params.get("xmdk040")); // 非成本仓
        master.put("xmdk054", params.get("xmdk054")); // 备注
        master.put("source_no", params.get("source_no")); // MES单号
        master.put("detail", details); // 将details放入master

        // 构建parameter
        Map<String, Object> parameter = new HashMap<>();
        List<Map<String, Object>> masterList = new ArrayList<>();
        masterList.add(master);
        parameter.put("master", masterList);

        // 构建payload
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> stdData = new HashMap<>();
        stdData.put("parameter", parameter);
        payload.put("std_data", stdData);

        request.put("payload", payload);
        System.out.println(JSONObject.toJSONString(request));
        String result = HttpUtils.doPost("http://192.168.127.7/wstopprd/ws/r/awsp920", JSONObject.toJSONString(request)); // 正式区
        //String result = HttpUtils.doPost("http://192.168.127.7/wtoptst/ws/r/awsp920", JSONObject.toJSONString(request)); // 测试区

        // 记录操作日志
        InterfaceLogCreateReqVO log = new InterfaceLogCreateReqVO();
        log.setInterfaceName("出货单生成接口");
        log.setReceiver("ERP");
        log.setRequester("MES");
        log.setRequestType("POST");
        log.setRequestMap(JSONObject.toJSONString(request));
        log.setResultMap(result);
        interfaceLogService.createInterfaceLog(log);
        return result;
    }


    /**
     * 用于采购收货/仓退单
     *
     * @param pmds000
     * @param details
     * @return
     */
    private List<Map<String, Object>> createMaster(String mesDocNo, String sourceNo, String pmds000, List<Map<String, Object>> details, String supplierCode) {
        DictDataDO dataDO = dictDataService.getDictData("erp_close_date", "close_date");
        String closeDateStr = null;
        Date closeDateObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (dataDO != null) {
            closeDateStr = dataDO.getLabel();
            if (closeDateStr != null && !closeDateStr.isEmpty()) {
                try {
                    closeDateObj = sdf.parse(closeDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        // master 单头
        // detail 单身
        Map<String, Object> master = new HashMap<>();
        if("7".equals(pmds000)) {
            master.put("source_no", mesDocNo); // MES单头
            master.put("pmds000", Integer.valueOf(pmds000));
        }else{
            master.put("source_no", sourceNo); // MES单头
            master.put("pmds000", pmds000);
        }

        /*
        2025-07-26 收货接口不卡关账日期, 传递当日日期
        boolean isCloseDateLastMonth = false;
        if (closeDateObj != null) {
            Calendar closeDateCal = Calendar.getInstance();
            closeDateCal.setTime(closeDateObj);

            Calendar lastMonthCal = Calendar.getInstance();
            lastMonthCal.add(Calendar.MONTH, -1);

            isCloseDateLastMonth = (closeDateCal.get(Calendar.YEAR) == lastMonthCal.get(Calendar.YEAR))
                    && (closeDateCal.get(Calendar.MONTH) == lastMonthCal.get(Calendar.MONTH));
        }

        // 设置过账日期
        if (isCloseDateLastMonth) {
            // 关账日期为上个月，直接使用当前日期
            master.put("pmdsdocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } else {
            // 2025-6-26 若当前存在关账日期, 则直接传递下个月
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            master.put("pmdsdocdt", sdf.format(calendar.getTime())); // 单据日期
        }*/

        master.put("pmdsdocno", ""); // 单别
        master.put("pmdsdocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        master.put("pmds006", mesDocNo);
        // master.put("pmds002", "00000");
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        master.put("pmds002", adminUserRespDTO.getUsername());

        master.put("pmds007", supplierCode);
        master.put("detail", details);
        if("7".equals(pmds000)){
           master.put("typeud001",mesDocNo ); // T100仓退单
        }
        List<Map<String, Object>> masterList = new ArrayList<>();
        masterList.add(master);
        return masterList;
    }

    /**
     * 用于入库单
     *
     * @param mesDocNo mes单头单号
     * @param sourceNo mes单头Id
     * @param pmds000  单据性质
     * @param details  明细
     * @return
     */
    private List<Map<String, Object>> createPmdsInfo(String mesDocNo, String sourceNo, String pmds000, List<Map<String, Object>> details, String supplierCode) {
        DictDataDO dataDO = dictDataService.getDictData("erp_close_date", "close_date");
        String closeDateStr = null;
        Date closeDateObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (dataDO != null) {
            closeDateStr = dataDO.getLabel();
            if (closeDateStr != null && !closeDateStr.isEmpty()) {
                try {
                    closeDateObj = sdf.parse(closeDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        Map<String, Object> pmdsInfo = new HashMap<>();
        pmdsInfo.put("source_no", sourceNo); // MES单头Id
        pmdsInfo.put("pmdsdocno", "");


        boolean isCloseDateLastMonth = false;
        if (closeDateObj != null) {
            Calendar closeDateCal = Calendar.getInstance();
            closeDateCal.setTime(closeDateObj);

            Calendar lastMonthCal = Calendar.getInstance();
            lastMonthCal.add(Calendar.MONTH, -1);

            isCloseDateLastMonth = (closeDateCal.get(Calendar.YEAR) == lastMonthCal.get(Calendar.YEAR))
                    && (closeDateCal.get(Calendar.MONTH) == lastMonthCal.get(Calendar.MONTH));
        }

        // 设置过账日期
        if (isCloseDateLastMonth) {
            // 关账日期为上个月，直接使用当前日期
            pmdsInfo.put("pmdsdocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } else {
            // 2025-6-26 若当前存在关账日期, 则直接传递下个月
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            pmdsInfo.put("pmdsdocdt", sdf.format(calendar.getTime())); // 单据日期
        }

        //pmdsInfo.put("pmdsdocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        pmdsInfo.put("pmds000", pmds000);
        pmdsInfo.put("pmds006", sourceNo);

        //pmdsInfo.put("pmds002", "00000");
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        pmdsInfo.put("pmds002", adminUserRespDTO.getUsername());

        pmdsInfo.put("pmds007", supplierCode);// 后续询问供应商如何获取
        pmdsInfo.put("detail", details);
        List<Map<String, Object>> pmdsInfoList = new ArrayList<>();
        pmdsInfoList.add(pmdsInfo);
        return pmdsInfoList;
    }

  /*  private Map<String, Object> createPmdsInfo(String mesDocNo, Long sourceNo, String pmds000, List<Map<String, Object>> details) {
        Map<String, Object> pmdsInfo = new HashMap<>();
        pmdsInfo.put("source_no", sourceNo); // MES单头Id
        pmdsInfo.put("pmdsdocno", mesDocNo);
        pmdsInfo.put("pmdsdocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        pmdsInfo.put("pmds000", pmds000);
        pmdsInfo.put("pmds006", mesDocNo);
        pmdsInfo.put("pmds002", null);
        pmdsInfo.put("pmds007", " ");// 后续询问供应商如何获取
        pmdsInfo.put("pmdt_info", details);
        return pmdsInfo;
    }*/

    private String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "未知";
        }
    }

}
