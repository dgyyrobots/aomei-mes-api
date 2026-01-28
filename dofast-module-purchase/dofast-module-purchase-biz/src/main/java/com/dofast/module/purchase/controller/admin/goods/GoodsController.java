package com.dofast.module.purchase.controller.admin.goods;

import com.dofast.framework.common.util.bean.BeanUtils;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.service.mditem.MdItemService;
import com.dofast.module.pro.api.FeedbackApi.FeedbackApi;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.purchase.convert.order.OrderConvert;
import com.dofast.module.purchase.dal.dataobject.order.OrderDO;
import com.dofast.module.purchase.dal.mysql.order.PurchaseOrderMapper;
import com.dofast.module.purchase.enums.ErrorCodeConstants;
import com.dofast.module.purchase.service.order.OrderOracleService;
import com.dofast.module.purchase.service.order.OrderService;
import com.dofast.module.tm.dal.dataobject.tool.ToolDO;
import com.dofast.module.tm.service.tool.ToolService;
import com.dofast.module.wms.api.ERPApi.MaterialStockERPAPI;
import com.dofast.module.wms.api.Issueheader.IssueApi;
import com.dofast.module.wms.api.Issueheader.dto.IssueLineDTO;
import com.dofast.module.wms.api.Issueheader.dto.IssueheaderDTO;
import com.dofast.module.wms.api.RtIssue.RtIssueApi;
import com.dofast.module.wms.api.RtIssue.dto.RtIssueLineDTO;
import com.dofast.module.wms.api.StorageAreaApi.StorageAreaApi;
import com.dofast.module.wms.api.StorageAreaApi.dto.StorageAreaDTO;
import com.dofast.module.wms.api.StorageLocationApi.StorageLocationApi;
import com.dofast.module.wms.api.StorageLocationApi.dto.StorageLocationDTO;
import com.dofast.module.wms.api.WarehosueApi.WarehouseApi;
import com.dofast.module.wms.api.WarehosueApi.dto.WarehouseDTO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockExportReqVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockRespVO;
import com.dofast.module.wms.controller.admin.storagearea.vo.StorageAreaExportReqVO;
import com.dofast.module.wms.controller.admin.transaction.vo.TransactionUpdateReqVO;
import com.dofast.module.wms.convert.materialstock.MaterialStockConvert;
import com.dofast.module.wms.dal.dataobject.itemrecpt.ItemRecptTxBean;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.module.wms.dal.dataobject.rtissueline.RtIssueLineDO;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.service.materialstock.MaterialStockService;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagecore.StorageCoreService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.transaction.TransactionService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import liquibase.pro.packaged.D;
import liquibase.pro.packaged.G;
import liquibase.pro.packaged.W;
import liquibase.repackaged.org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.purchase.controller.admin.goods.vo.*;
import com.dofast.module.purchase.dal.dataobject.goods.GoodsDO;
import com.dofast.module.purchase.convert.goods.GoodsConvert;
import com.dofast.module.purchase.service.goods.GoodsService;

@Tag(name = "管理后台 - 采购商品明细")
@RestController
@RequestMapping("/purchase/goods")
@Validated
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private PurchaseOrderMapper orderMapper;

    @Resource
    private MaterialStockERPAPI materialStockERPAPI;

    @Resource
    private OrderService orderService;

    @Resource
    private StorageCoreService storageCoreService;

    @Resource
    private WarehouseApi warehouseApi;

    @Resource
    private StorageLocationApi storageLocationApi;

    @Resource
    private StorageAreaApi storageAreaApi;

    @Resource
    private MdItemService mdItemService;

    @Resource
    private MaterialStockService materialStockService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @Resource
    private FeedbackApi feedbackApi;

    @Resource
    private ToolService toolService;

    @Resource
    private RtIssueApi rtIssueApi;

    @Resource
    private IssueApi issueApi;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private OrderOracleService orderOracleService;

    @PostMapping("/create")
    @Operation(summary = "创建采购商品明细")
    @PreAuthorize("@ss.hasPermission('purchase:goods:create')")
    public CommonResult<Integer> createGoods(@Valid @RequestBody GoodsCreateReqVO createReqVO) {
        return success(goodsService.createGoods(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新采购商品明细")
    @PreAuthorize("@ss.hasPermission('purchase:goods:update')")
    public CommonResult<Boolean> updateGoods(@Valid @RequestBody GoodsUpdateReqVO updateReqVO) {
        if (updateReqVO.getReceiveNum() != null && updateReqVO.getQuantity() != null) {
            // receiveNum只允许超过quantity5%浮动
            if (updateReqVO.getReceiveNum().compareTo(updateReqVO.getQuantity().multiply(new BigDecimal("1.05"))) > 0) {
                return error(ErrorCodeConstants.RECEIVE_CANNOT_EXCEED);
            }

            // 收货数量 + 已拆分数量不能大于采购数量
            BigDecimal sum = updateReqVO.getReceiveNum().add(updateReqVO.getReceivedNum() == null ? BigDecimal.ZERO : updateReqVO.getReceivedNum());

            if (sum.compareTo(updateReqVO.getQuantity().multiply(new BigDecimal("1.05"))) > 0) {
                return error(ErrorCodeConstants.RECEIVE_CANNOT_EXCEED);
            }
        }

        if (updateReqVO.getReceiveTime() == null) {
            // 若入库时间为空，则默认当前时间
            updateReqVO.setReceiveTime(LocalDateTime.now());
        }

        if (updateReqVO.getBatchCode() == null) {
            // 校验采购单表是否存在母批次号
            OrderDO orderDO = orderService.getOrder(updateReqVO.getPoNo());
            if (orderDO.getParentBatchCode() == null) {
                // 若母批次号为空，生成母批次号
                // 获取当前日期
                LocalDate currentDate = LocalDate.now();
                // 定义日期格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String parentBatchCode = currentDate.format(formatter) + ThreadLocalRandom.current().nextInt(1000, 10000);
                String serial = "001";
                // 修改采购单表
                orderDO.setParentBatchCode(parentBatchCode);
                orderDO.setSerial(serial);
                orderService.updateOrder(OrderConvert.INSTANCE.convert01(orderDO));
                updateReqVO.setParentBatchCode(parentBatchCode);
                updateReqVO.setBatchCode(parentBatchCode + "-" + serial);//serial = 01
            } else {
                updateReqVO.setParentBatchCode(orderDO.getParentBatchCode());
                String serial = orderDO.getSerial();
                if (serial == null) {
                    serial = "001";
                } else {
                    int serialInt = Integer.parseInt(serial);
                    serialInt++;
                    serial = String.format("%03d", serialInt);
                }
                updateReqVO.setBatchCode(orderDO.getParentBatchCode() + "-" + serial);
                orderDO.setSerial(serial);
                orderService.updateOrder(OrderConvert.INSTANCE.convert01(orderDO));
            }
        }

        // 原单据信息
        GoodsDO originGoods = goodsService.getGoods(updateReqVO.getId());
        // 追加校验: 若当前收货数量(receiveNum)为0, 已拆分数量(receivedNum)小于采购数量(quantity), 回滚单据为未收货(status = 0)
        if (originGoods != null) {
            BigDecimal receiveNum = originGoods.getReceiveNum() == null ? BigDecimal.ZERO : originGoods.getReceiveNum();
            BigDecimal receivedNum = originGoods.getReceivedNum() == null ? BigDecimal.ZERO : originGoods.getReceivedNum();
            if (receiveNum.compareTo(BigDecimal.ZERO) == 0 && receivedNum.compareTo(originGoods.getQuantity()) < 0) {
                updateReqVO.setStatus(0);
            }
        }

        goodsService.updateGoods(updateReqVO);
        return success(true);
    }

    @PutMapping("/updateReceiveStatus")
    @Operation(summary = "更新采购商品明细收货状态")
    @PreAuthorize("@ss.hasPermission('purchase:goods:update')")
    public CommonResult<Boolean> updateReceiveStatus(@Valid @RequestBody List<GoodsUpdateReqVO> updateReqVOList) {
        if (!updateReqVOList.isEmpty()) {
            // 未入库 =》 已入库
            for (GoodsUpdateReqVO goodsUpdateReqVO : updateReqVOList) {
                Integer status = goodsUpdateReqVO.getStatus();
                if (status == 1) { // 1: 未打印 2: 已打印
                    goodsUpdateReqVO.setStatus(2);
                    goodsService.updateGoods(goodsUpdateReqVO);
                }
            }
        }
        return success(true);
    }

    @PutMapping("/batchUpdateReceiveStatus")
    @Operation(summary = "批量更新收货状态")
    @PreAuthorize("@ss.hasPermission('purchase:goods:update')")
    public CommonResult<Boolean> batchUpdateReceiveStatus(@Valid @RequestBody String poNo) {
        // 根据采购单号获取单身信息
        GoodsExportReqVO exportReqVO = new GoodsExportReqVO();
        exportReqVO.setPoNo(poNo);
        List<GoodsDO> result = goodsService.getGoodsList(exportReqVO);
        // 未入库 =》 已入库
        for (GoodsDO good : result) {
            Integer status = good.getStatus();
            if (status == 0) {
                good.setStatus(1);
                goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(good));
            }
        }
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除采购商品明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('purchase:goods:delete')")
    public CommonResult<Boolean> deleteGoods(@RequestParam("id") Integer id) {
        goodsService.deleteGoods(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得采购商品明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<GoodsRespVO> getGoods(@RequestParam("id") Integer id) {
        GoodsDO goods = goodsService.getGoods(id);

        return success(GoodsConvert.INSTANCE.convert(goods));
    }

    @GetMapping("/list")
    @Operation(summary = "获得采购商品明细列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<List<GoodsRespVO>> getGoodsList(@RequestParam("ids") Collection<Integer> ids) {
        List<GoodsDO> list = goodsService.getGoodsList(ids);
        // 过滤list , 若list中的数据中符合received_num >= quantity AND receive_num = 0
        List<GoodsDO> editGoods = new ArrayList<>();
        for (GoodsDO goodsDO : list) {
            BigDecimal receivedNum = goodsDO.getReceivedNum() == null ? BigDecimal.ZERO : goodsDO.getReceivedNum();
            BigDecimal quantity = goodsDO.getQuantity() == null ? BigDecimal.ZERO : goodsDO.getQuantity();
            BigDecimal receiveNum = goodsDO.getReceiveNum() == null ? BigDecimal.ZERO : goodsDO.getReceiveNum();
            if (receivedNum.compareTo(quantity) > -1 && receiveNum.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            editGoods.add(goodsDO);
        }
        return success(GoodsConvert.INSTANCE.convertList(editGoods));
    }

    /*@GetMapping("/page")
    @Operation(summary = "获得采购商品明细分页")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<PageResult<GoodsRespVO>> getGoodsPage(@Valid GoodsPageReqVO pageVO) {
        PageResult<GoodsDO> pageResult = goodsService.getGoodsPage(pageVO);
        List<GoodsDO> goodsList = goodsService.getGoodsList(new GoodsExportReqVO().setPoNo(pageVO.getPoNo()));

        List<GoodsDO> list = pageResult.getList();
        // 过滤list , 若list中的数据中符合received_num >= quantity AND receive_num = 0
        List<GoodsDO> editGoods = new ArrayList<>();
        for (GoodsDO goodsDO : list) {
            BigDecimal receivedNum = goodsDO.getReceivedNum() == null ? BigDecimal.ZERO : goodsDO.getReceivedNum();
            BigDecimal quantity = goodsDO.getQuantity() == null ? BigDecimal.ZERO : goodsDO.getQuantity();
            BigDecimal receiveNum = goodsDO.getReceiveNum() == null ? BigDecimal.ZERO : goodsDO.getReceiveNum();
            if (receivedNum.compareTo(quantity) > -1 && receiveNum.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            editGoods.add(goodsDO);
        }
        pageResult.setList(editGoods);

        int count = 0;
        for(GoodsDO goodsDO : goodsList) {
            BigDecimal receivedNum = goodsDO.getReceivedNum() == null ? BigDecimal.ZERO : goodsDO.getReceivedNum();
            BigDecimal quantity = goodsDO.getQuantity() == null ? BigDecimal.ZERO : goodsDO.getQuantity();
            BigDecimal receiveNum = goodsDO.getReceiveNum() == null ? BigDecimal.ZERO : goodsDO.getReceiveNum();
            if (receivedNum.compareTo(quantity) > -1 && receiveNum.compareTo(BigDecimal.ZERO) == 0) {
                count++;
            }
        }
        long finTotal = pageResult.getTotal() - count;
        pageResult.setTotal(finTotal);

        return success(GoodsConvert.INSTANCE.convertPage(pageResult));
    }*/

    @GetMapping("/page")
    @Operation(summary = "获得采购商品明细分页")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<PageResult<GoodsRespVO>> getGoodsPage(@Valid GoodsPageReqVO pageVO) {
        // 获取分页数据
        PageResult<GoodsDO> pageResult = goodsService.getGoodsPage(pageVO);

        // 使用相同的查询条件获取所有数据
        GoodsExportReqVO exportReqVO = GoodsConvert.INSTANCE.convert03(pageVO); // 转换查询条件

        List<GoodsDO> goodsList = goodsService.getGoodsList(exportReqVO);

        // 过滤当前页数据
        List<GoodsDO> filteredList = pageResult.getList().stream()
                .filter(goods -> !isFullyReceivedAndNoPending(goods))
                .collect(Collectors.toList());
        pageResult.setList(filteredList);

        // 统计所有数据中需要过滤的数量
        long totalFiltered = goodsList.stream()
                .filter(this::isFullyReceivedAndNoPending)
                .count();

        // 计算有效总数
        long finTotal = pageResult.getTotal() - totalFiltered;
        pageResult.setTotal(Math.max(finTotal, 0)); // 防止负数
        return success(GoodsConvert.INSTANCE.convertPage(pageResult));
    }

    // 辅助方法：判断是否满足过滤条件
    private boolean isFullyReceivedAndNoPending(GoodsDO goods) {
        BigDecimal receivedNum = Optional.ofNullable(goods.getReceivedNum()).orElse(BigDecimal.ZERO);
        BigDecimal quantity = Optional.ofNullable(goods.getQuantity()).orElse(BigDecimal.ZERO);
        BigDecimal receiveNum = Optional.ofNullable(goods.getReceiveNum()).orElse(BigDecimal.ZERO);

        return receivedNum.compareTo(quantity) >= 0 &&
                receiveNum.compareTo(BigDecimal.ZERO) == 0;
    }

    @GetMapping("/allList")
    @Operation(summary = "获得采购商品明细列表")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<List<GoodsRespVO>> getGoodsAllList(@Valid GoodsDO goodsDO) {
        GoodsExportReqVO exportReqVO = new GoodsExportReqVO();
        goodsDO.setStatus(2); // 2: 已打印
        BeanUtils.copyProperties(goodsDO, exportReqVO);
        List<GoodsDO> pageResult = goodsService.getGoodsList(exportReqVO);
        return success(GoodsConvert.INSTANCE.convertList(pageResult));
    }

    @GetMapping("/getGoodsSumQuantity")
    @Operation(summary = "获得采购商品明细列表")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<List<GoodsRespVO>> getGoodsSumQuantity(@Valid GoodsDO goodsDO) {
        // 根据采购单号查询原始商品列表
        List<GoodsDO> queryGoods = goodsService.getGoodsList(new GoodsExportReqVO().setPoNo(goodsDO.getPoNo()).setStatus(2));
        // 使用Map进行物料料号分组并汇总数量
        Map<String, GoodsDO> goodsMap = new LinkedHashMap<>();
        for (GoodsDO goods : queryGoods) {
            String goodsNumber = goods.getGoodsNumber();
            if (goodsMap.containsKey(goodsNumber)) {
                // 若存在相同料号，累加数量
                GoodsDO existGoods = goodsMap.get(goodsNumber);
                BigDecimal existQuantity = existGoods.getQuantity() != null ? existGoods.getQuantity() : BigDecimal.ZERO;
                BigDecimal newQuantity = goods.getQuantity() != null ? goods.getQuantity() : BigDecimal.ZERO;
                existGoods.setQuantity(existQuantity.add(newQuantity));

                BigDecimal existReceive = existGoods.getReceivedNum() != null ? existGoods.getReceivedNum() : BigDecimal.ZERO;
                BigDecimal newReceive = goods.getReceivedNum() != null ? goods.getReceivedNum() : BigDecimal.ZERO;
                existGoods.setQuantity(existReceive.add(newReceive));

            } else {
                // 出现的物料料号，创建新对象加入Map
                GoodsDO newGoods = new GoodsDO();
                BeanUtils.copyProperties(goods, newGoods);  // 复制属性
                goodsMap.put(goodsNumber, newGoods);
            }
        }
        // 转换为结果列表
        List<GoodsDO> resultGoodsList = new ArrayList<>(goodsMap.values());
        return success(GoodsConvert.INSTANCE.convertList(resultGoodsList));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出采购商品明细 Excel")
    @PreAuthorize("@ss.hasPermission('purchase:goods:export')")
    @OperateLog(type = EXPORT)
    public void exportGoodsExcel(@Valid GoodsExportReqVO exportReqVO,
                                 HttpServletResponse response) throws IOException {
        List<GoodsDO> list = goodsService.getGoodsList(exportReqVO);
        // 导出 Excel
        List<GoodsExcelVO> datas = GoodsConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "采购商品明细.xls", "数据", GoodsExcelVO.class, datas);
    }

    @PostMapping("/wareHousing")
    public String wareHousing(@RequestBody Map<String, Object> params) {
        String poNo = params.get("poNo").toString();
        Integer wareHouseId = (Integer) params.get("warehouseId");
        Integer locationId = (Integer) params.get("locationId");
        Integer areaId = (Integer) params.get("areaId");

        OrderDO orderDO = orderMapper.selectOne(OrderDO::getPoNo, poNo);
        String supplierCode = Optional.ofNullable(orderDO.getSupplierCode()).orElse("");

        GoodsExportReqVO exportReqVO = new GoodsExportReqVO();
        exportReqVO.setPoNo(poNo);
        exportReqVO.setStatus(2); // 2-已打印
        List<GoodsDO> goodsList = goodsService.getGoodsList(exportReqVO);

        // 双层分组结构
        Map<String, Map<String, Map<String, Object>>> outerGroupMap = new HashMap<>();
        // 原始记录映射
        Map<String, List<GoodsDO>> originalGroupMap = new HashMap<>();

        for (GoodsDO goodsDO : goodsList) {
            BigDecimal receiveNum = goodsDO.getReceiveNum() == null ?
                    BigDecimal.ZERO : new BigDecimal(String.valueOf(goodsDO.getReceiveNum()));
            if (receiveNum.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            String erpReceiveCode = goodsDO.getErpReceiveCode();
            String outerKey = poNo + "_" + erpReceiveCode;

            if (!outerGroupMap.containsKey(outerKey)) {
                outerGroupMap.put(outerKey, new HashMap<>());
                originalGroupMap.put(outerKey, new ArrayList<>());
            }

            originalGroupMap.get(outerKey).add(goodsDO);

            String innerKey = createWareHousingInnerKey(goodsDO);
            Map<String, Map<String, Object>> innerGroup = outerGroupMap.get(outerKey);

            if (innerGroup.containsKey(innerKey)) {
                Map<String, Object> mergedGoods = innerGroup.get(innerKey);
                BigDecimal currentNum = (BigDecimal) mergedGoods.get("receiveNum");
                mergedGoods.put("receiveNum", currentNum.add(receiveNum));
            } else {
                Map<String, Object> mergedGoods = createWareHousingGoodsMap(goodsDO, poNo);
                innerGroup.put(innerKey, mergedGoods);
            }
        }

        // 记录ERP调用结果
        List<String> successOuterKeys = new ArrayList<>();
        Map<String, String> failedGroups = new LinkedHashMap<>();
        List<String> allErpReceives = new ArrayList<>();

        int i = 0;

        // 尝试所有ERP接口调用
        for (Map.Entry<String, Map<String, Map<String, Object>>> outerEntry : outerGroupMap.entrySet()) {
            String outerKey = outerEntry.getKey();
            List<Map<String, Object>> mergedList = new ArrayList<>(outerEntry.getValue().values());
            Map<String, Object> firstGoods = mergedList.get(0);
            String erpReceiveCode = firstGoods.get("erpReceiveCode").toString();

            // 记录所有erpReceiveCode用于结果展示
            allErpReceives.add(erpReceiveCode);

            Map<String, Object> erpParams = new HashMap<>(params);
            erpParams.put("goodsList", mergedList);
            erpParams.put("sourceNo", erpReceiveCode);
            erpParams.put("warehousingCode", erpReceiveCode);
            erpParams.put("supplierCode", firstGoods.get("supplierCode"));
            erpParams.put("poNo", poNo);
            erpParams.put("pmds000", "6"); // 采购入库

            try {
                String result = materialStockERPAPI.purchaseDeliveryCreate(erpParams);
                // String result = "success";
                if (result != null && result.contains("success")) {
                    successOuterKeys.add(outerKey);
                } else {
                    failedGroups.put(erpReceiveCode, result);
                }
            } catch (Exception e) {
                failedGroups.put(erpReceiveCode, "ERP接口异常: " + e.getMessage());
            }
        }

        // 全失败处理
        if (successOuterKeys.isEmpty() && !failedGroups.isEmpty()) {
            return "所有入库单处理失败: " + formatErrors(failedGroups);
        }

        // 处理MES数据（仅成功部分）
        List<ItemRecptTxBean> transactionList = new ArrayList<>();
        for (String outerKey : successOuterKeys) {
            List<GoodsDO> groupGoods = originalGroupMap.get(outerKey);
            for (GoodsDO goodsDO : groupGoods) {
                BigDecimal receiveNum = goodsDO.getReceiveNum() == null ?
                        BigDecimal.ZERO : new BigDecimal(String.valueOf(goodsDO.getReceiveNum()));
                if (receiveNum.compareTo(BigDecimal.ZERO) == 0) continue;

                // 更新商品状态
                goodsDO.setStatus(3); // 已入库
                goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(goodsDO));

                // 生成库存事务记录
                ItemRecptTxBean bean = createTransactionBean(goodsDO, orderDO, poNo,
                        wareHouseId, locationId, areaId, supplierCode);
                transactionList.add(bean);
            }
        }

        // 执行库存事务
        if (!transactionList.isEmpty()) {
            storageCoreService.processItemRecpt(transactionList);
        }

        // 混合结果处理
        if (!failedGroups.isEmpty()) {
            int successCount = successOuterKeys.size();
            int totalCount = allErpReceives.size();
            return String.format("部分入库单处理成功 (%d/%d)，失败单据: %s",
                    successCount, totalCount, formatErrors(failedGroups));
        }

        return "success";
    }




    // 构建内层分组Key：料号_项次_项序_母批次
    private String createWareHousingInnerKey(GoodsDO goodsDO) {
        return goodsDO.getGoodsNumber() + "_" +
                goodsDO.getPurchaseBatch() + "_" +
                goodsDO.getPurchaseConsequence() + "_" +
                goodsDO.getParentBatchCode();
    }

    // 创建ERP接口使用的商品Map
    private Map<String, Object> createWareHousingGoodsMap(GoodsDO goodsDO, String poNo) {
        Map<String, Object> goodsMap = new HashMap<>();
        goodsMap.put("id", goodsDO.getId());
        goodsMap.put("poNo", poNo);
        goodsMap.put("goodsNumber", goodsDO.getGoodsNumber());
        goodsMap.put("goodsName", goodsDO.getGoodsName());
        goodsMap.put("unitOfMeasure", goodsDO.getUnitOfMeasure());
        goodsMap.put("receiveNum", goodsDO.getReceiveNum());
        goodsMap.put("batchCode", goodsDO.getParentBatchCode());
        goodsMap.put("consequence", goodsDO.getReceiveSeq());
        goodsMap.put("purchaseBatch", goodsDO.getPurchaseBatch());
        goodsMap.put("purchaseConsequence", goodsDO.getPurchaseConsequence());
        goodsMap.put("purchaseBatchConsequence", goodsDO.getPurchaseBatchConsequence());
        goodsMap.put("supplierCode", goodsDO.getVendorCode());
        goodsMap.put("erpReceiveCode", goodsDO.getErpReceiveCode());
        return goodsMap;
    }

    // 创建库存事务对象
    private ItemRecptTxBean createTransactionBean(GoodsDO goodsDO, OrderDO orderDO, String poNo,
                                                  Integer wareHouseId, Integer locationId, Integer areaId,
                                                  String supplierCode) {
        String batchCode = goodsDO.getBatchCode();
        String parentBatchCode = goodsDO.getParentBatchCode();
        String itemCode = goodsDO.getGoodsNumber();
        MdItemDO mdItemDO = Optional.ofNullable(mdItemService.getMdItemByItemCode(itemCode)).orElse(null);
        String itemName = goodsDO.getGoodsName();
        String specification = goodsDO.getGoodsSpecs();
        String unitOfMeasure = goodsDO.getUnitOfMeasure();
        BigDecimal quantity = goodsDO.getReceiveNum();

        ItemRecptTxBean bean = new ItemRecptTxBean();
        bean.setId(mdItemDO != null ? mdItemDO.getId().longValue() : 0L);
        bean.setItemCode(itemCode);
        bean.setItemName(itemName);
        bean.setSpecification(specification);
        bean.setUnitOfMeasure(unitOfMeasure);
        bean.setTransactionQuantity(quantity);
        bean.setBatchCode(batchCode);
        bean.setParentBatchCode(parentBatchCode);
        bean.setSourceDocCode(poNo);
        bean.setSourceDocType("PURCHASE");
        bean.setSourceDocId(orderDO.getId().longValue());
        bean.setSourceDocLineId(goodsDO.getId().longValue());
        bean.setOriginId(goodsDO.getId().longValue());
        bean.setVendorCode(supplierCode);

        // 仓库信息
        WarehouseDTO warehouse = warehouseApi.getWarehouse(wareHouseId.longValue());
        bean.setWarehouseId(wareHouseId.longValue());
        bean.setWarehouseCode(warehouse.getWarehouseCode());
        bean.setWarehouseName(warehouse.getWarehouseName());

        // 库区信息
        StorageLocationDTO location = storageLocationApi.getLocation(locationId.longValue());
        bean.setLocationId(locationId.longValue());
        bean.setLocationCode(location.getLocationCode());
        bean.setLocationName(location.getLocationName());

        // 库位信息
        StorageAreaDTO area = storageAreaApi.getArea(areaId.longValue());
        bean.setAreaId(areaId.longValue());
        bean.setAreaCode(area.getAreaCode());
        bean.setAreaName(area.getAreaName());

        return bean;
    }


    @PostMapping("/getStockByPurchaseId")
    @Operation(summary = "获得库存记录")
    public CommonResult<MaterialStockRespVO> getStockByPurchaseId(@RequestBody Map<String, Object> params) {
        if (MapUtils.isEmpty(params)) {
            return error(ErrorCodeConstants.PARAMS_ERROR);
        }
        // 根据采购单身Id
        Integer id = null;
        Object idObj = params.get("id");
        if (idObj instanceof Integer) {
            id = (Integer) idObj;
        } else if (idObj instanceof Long) {
            id = ((Long) idObj).intValue();
        }
        String type = StringUtils.defaultString((String) params.get("type"), "");
        String transBatchCode = StringUtils.defaultString((String) params.get("batchCode"), "");
        String itemCode = null;
        String batchCode = null;
        String method = (String) params.get("method");
        boolean toolFlag = false;
        String recpt = Optional.ofNullable((String) params.get("recpt")).orElse("");

        MaterialStockRespVO toolResponse = new MaterialStockRespVO();

        if(StringUtils.isBlank(type) && StringUtils.isBlank(transBatchCode)){
            return error(ErrorCodeConstants.PARAMS_ERROR);
        }

        if (transBatchCode != "" && transBatchCode != null) {
            batchCode = transBatchCode.trim();
        } else {
            switch (type) {
                case "purchase":
                    GoodsDO orderDO = goodsService.getGoods(id);
                    itemCode = orderDO.getGoodsNumber();
                    batchCode = orderDO.getBatchCode();
                    break;
                case "feedback":
                    FeedbackDTO feedbackDO = feedbackApi.getFeedBack(id.longValue());
                    itemCode = feedbackDO.getItemCode();
                    batchCode = feedbackDO.getBatchCode();
                    break;
                case "eject":
                    RtIssueLineDTO query = new RtIssueLineDTO();
                    query.setId(id.longValue());
                    RtIssueLineDTO rtIssueLineDTO = rtIssueApi.listRtIssueLine(query).get(0);
                    itemCode = rtIssueLineDTO.getItemCode();
                    batchCode = rtIssueLineDTO.getBatchCode();
                    break;
                case "warehouse":
                    MaterialStockDO materialStockDO = materialStockService.getMaterialStock(Long.valueOf(id));
                    if (materialStockDO == null) {
                        return error(ErrorCodeConstants.MATERIAL_NOT_WAREHOUSE);
                    }
                    if(materialStockDO.getQuantityOnhand().compareTo(BigDecimal.ZERO)>0){
                        BeanUtils.copyBeanProp(toolResponse , materialStockDO);
                    }else{
                        List<MaterialStockDO> queryMaterialStockDO = materialStockService.getMaterialStockList(new MaterialStockExportReqVO().setBatchCode(materialStockDO.getBatchCode()));
                        if (queryMaterialStockDO.isEmpty()) {
                            return error(ErrorCodeConstants.MATERIAL_NOT_WAREHOUSE);
                        }
                        BeanUtils.copyBeanProp(toolResponse , queryMaterialStockDO.get(0));
                    }
                    toolFlag = true;
                    break;
                case "tool":
                    ToolDO toolDO = toolService.getTool(id.longValue());
                    if (toolDO.getQuantityAvail() < 1) {
                        return error(ErrorCodeConstants.TOOL_NOT_ENOUGH);
                    }
                    toolResponse.setId(toolDO.getId().longValue());
                    toolResponse.setItemCode(toolDO.getToolCode());
                    toolResponse.setItemName(toolDO.getToolName());
                    toolResponse.setQuantityOnhand(BigDecimal.ONE);
                    toolResponse.setUnitOfMeasure("张");
                    StorageLocationDO locationDO = storageLocationService.getStorageLocation("AM007");
                    StorageAreaExportReqVO exportReqVO = new StorageAreaExportReqVO();
                    exportReqVO.setLocationId(locationDO.getId());
                    StorageAreaDO areaDO = storageAreaService.getStorageAreaList(exportReqVO).get(0);
                    toolResponse.setLocationId(locationDO.getId());
                    toolResponse.setWarehouseId(locationDO.getWarehouseId());
                    toolResponse.setAreaId(areaDO.getId());
                    toolFlag = true;
                    break;
            }
        }

        if (toolFlag) {
            return success(toolResponse);
        }

        // 根据单号不同获取对应库存信息
        MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
        exportReqVO.setItemCode(itemCode);
        exportReqVO.setBatchCode(batchCode);
        exportReqVO.setRecptStatus("N");
        List<MaterialStockDO> checkConfirmMaterialStock = materialStockService.getMaterialStockList(exportReqVO);

        if (!checkConfirmMaterialStock.isEmpty()) {
            // 当前物料出现调拨, 提示用户先进行确认
            return error(ErrorCodeConstants.MATERIAL_ALLOCATE);
        }

        if (!"N".equals(recpt)) {
            exportReqVO.setRecptStatus("Y");
        }

        List<MaterialStockDO> materialStock = materialStockService.getMaterialStockListContainZero(exportReqVO);
        if (materialStock.isEmpty()) {
            return error(ErrorCodeConstants.MATERIAL_NOT_WAREHOUSE);
        }

        // 过滤materialStock, 获取quantityOnhand大于零且最新的一条数据
        materialStock = materialStock.stream().filter(stock -> stock.getQuantityOnhand().compareTo(BigDecimal.ZERO) > 0).sorted(Comparator.comparing(MaterialStockDO::getCreateTime).reversed()).collect(Collectors.toList());
        if (materialStock.isEmpty()) {
            return error(com.dofast.module.wms.enums.ErrorCodeConstants.ISSUE_LINE_VIRTUAL_WH);
        }

        List<MaterialStockDO> finMaterialStock = materialStockService.getMaterialStockList(exportReqVO);

        //2025-08-02 追加校验当前扫码物料是否在虚拟线边仓
        if (finMaterialStock.get(0).getWarehouseCode().equals(Constant.VIRTUAL_WH)) {
            return error(com.dofast.module.wms.enums.ErrorCodeConstants.ISSUE_LINE_VIRTUAL_WH);
        }

        /*if (finMaterialStock.get(0).getQuantityOnhand().equals(BigDecimal.ZERO)) {
            return error(ErrorCodeConstants.MATERIAL_NOT_WAREHOUSE);
        }*/


        // 暂时禁用
        /*if(method != null && method.equals("allocated")){
            Integer warehouseId = (Integer) Optional.ofNullable(params.get("warehouseId")).orElse(0);
            Integer locationId = (Integer) Optional.ofNullable(params.get("locationId")).orElse(0);
            Integer areaId = (Integer) Optional.ofNullable(params.get("areaId")).orElse(0);
            if(warehouseId != 0 && locationId != 0 && areaId!= 0){
                BigDecimal quantityOnhand = Optional.ofNullable(materialStock.get(0).getQuantityOnhand()).orElse(BigDecimal.ZERO); // 获取当前线边仓库存信息
                // 获取物料的最大库存
                MdItemDO itemDO = mdItemService.getMdItemByItemCode(itemCode);
                BigDecimal maxStock = Optional.ofNullable(itemDO.getMaxStock()).orElse(BigDecimal.ZERO);
                // 比较调拨线边仓库存是否超过了物料的最大库存
                if (quantityOnhand.compareTo(maxStock) > 0) {
                    return error(ErrorCodeConstants.MATERIAL_MAX_STOCK);
                }
            }
        }*/
        return success(MaterialStockConvert.INSTANCE.convert(materialStock.get(0)));
       /* MaterialStockDO stockDO = materialStock.get(0);
        MaterialStockRespVO respVO = new MaterialStockRespVO();
        BeanUtils.copyBeanProp(respVO, stockDO);
        return success(respVO);*/
    }


    @PostMapping("/splitGoods")
    public CommonResult<String> splitGoods(@RequestBody Map<String, Object> params) {
        // 根据当前的入库单号获取入库单详情, 做入库操作
        String poNo = (String) params.get("poNo");
        Integer id = (Integer) params.get("id");
        String goodsNumber = (String) params.get("goodsNumber");
        String unitOfMeasure = (String) params.get("unitOfMeasure");
        List<Map<String, Object>> splitList = (List<Map<String, Object>>) params.get("splitDetails");

        GoodsDO parent = goodsService.getGoods(id);


        // 使用任务单号作为锁的键，确保同一任务单的报工操作串行化
        String lockKey = "goods:split:" + parent.getPoNo();
        String lockValue = UUID.randomUUID().toString();

        boolean locked = false;
        try {
            // 获取分布式锁
            locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(60));
            if (!locked) {
                return error(ErrorCodeConstants.PURCHASE_ORDER_LOCKED);
            }
            // 在锁的保护下执行核心业务逻辑

            GoodsExportReqVO goodsReqVO = new GoodsExportReqVO();
            goodsReqVO.setPoNo(parent.getPoNo());
            goodsReqVO.setGoodsNumber(parent.getGoodsNumber());
            List<GoodsDO> goodsList = goodsService.getGoodsList(goodsReqVO);
            /*if (goodsList.isEmpty()) {
                return error(ErrorCodeConstants.GOODS_NOT_EXISTS);
            }*/
            goodsList.sort(Comparator.comparing(GoodsDO::getCreateTime));
            GoodsDO originGoods = goodsList.get(0);
            boolean splitFlag = false;
            if(originGoods.getId().equals(parent.getId())){
                splitFlag = true;
            }

            OrderDO orderDO = orderMapper.selectOne(OrderDO::getPoNo, poNo);
            BigDecimal updateCount = new BigDecimal(0);
            MdItemDO mdItemDO = mdItemService.getMdItemByItemCode(goodsNumber);

            String transactionType_out = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_OUT;
            String transactionType_in = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_IN;

            // 开始追加采购商品单身表
            for (Map<String, Object> split : splitList) {
                BigDecimal quantity = new BigDecimal(String.valueOf(split.get("quantity"))); // 收货数量
                GoodsDO goodsDO = new GoodsDO();
                goodsDO.setPoNo(poNo);
                goodsDO.setGoodsNumber(goodsNumber);
                goodsDO.setUnitOfMeasure(parent.getUnitOfMeasure());
                goodsDO.setCompany(parent.getUnitOfMeasure());
                goodsDO.setQuantity(quantity);
                goodsDO.setReceiveNum(quantity);
                goodsDO.setGoodsName(parent.getGoodsName());
                goodsDO.setPurchaseId(parent.getPurchaseId());
                goodsDO.setGoodsSpecs(parent.getGoodsSpecs());
                goodsDO.setParentBatchCode(orderDO.getParentBatchCode());
                // goodsDO.setReceiveTime(parent.getReceiveTime());
                // setReceiveTime修改为获取当前日期 要求LocalDateTime格式
                goodsDO.setReceiveTime(LocalDateTime.now());
                goodsDO.setStatus(parent.getStatus());
                goodsDO.setVendorCode(parent.getVendorCode());
                goodsDO.setVendorName(parent.getVendorName());
                goodsDO.setConsequence(parent.getConsequence()); // 继承拆分行项次
                goodsDO.setPurchaseBatch(parent.getPurchaseBatch());   // ERP采购批次
                goodsDO.setPurchaseConsequence(parent.getPurchaseConsequence()); // ERP采购批序
                goodsDO.setPurchaseBatchConsequence(parent.getPurchaseBatchConsequence()); // ERP采购分批序
                goodsDO.setErpReceiveCode(parent.getErpReceiveCode()); // ERP收货单号
                goodsDO.setReceiveSeq(parent.getReceiveSeq()); // ERP收货项次
                String serial = orderDO.getSerial();
                if (serial == null) {
                    serial = "001";
                } else {
                    int serialInt = Integer.parseInt(serial);
                    serialInt++;
                    serial = String.format("%03d", serialInt);
                }
                goodsDO.setBatchCode(orderDO.getParentBatchCode() + "-" + serial);
                orderDO.setSerial(serial);
                orderService.updateOrder(OrderConvert.INSTANCE.convert01(orderDO));
                Integer lineId = goodsService.createGoods(GoodsConvert.INSTANCE.convert02(goodsDO));
                updateCount = updateCount.add(quantity);

                // 校验当前的单据是否已入库
                if (parent.getStatus() == 3) {
                    // 当前单据已入库
                    // 获取库存信息, 修改当前已入库数量
                    MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
                    exportReqVO.setItemCode(goodsNumber);
                    exportReqVO.setBatchCode(parent.getBatchCode());
                    List<MaterialStockDO> materialStockDO = materialStockService.getMaterialStockList(exportReqVO);
                    MaterialStockDO materialStock = materialStockDO.get(0);
                    //BigDecimal updateCountBig = new BigDecimal(updateCount);
                    //materialStock.setQuantityOnhand( materialStock.getQuantityOnhand().subtract(updateCountBig));

                    //构造原库存减少事务
                    TransactionUpdateReqVO transaction_out = new TransactionUpdateReqVO();
                    BeanUtils.copyBeanProp(transaction_out, materialStock);
                    transaction_out.setTransactionType(transactionType_out);
                    transaction_out.setTransactionFlag(-1);//库存减少
                    BigDecimal transactionQuantity = new BigDecimal(String.valueOf(quantity));
                    transaction_out.setTransactionQuantity(transactionQuantity);
                    transaction_out.setTransactionDate(LocalDateTime.now());
                    transaction_out.setSourceDocId(parent.getPurchaseId().longValue());
                    transaction_out.setSourceDocCode(poNo);
                    transaction_out.setSourceDocLineId(parent.getId().longValue());
                    transactionService.processTransaction(transaction_out);

                    //再构造一条目的库存增加的事务
                    TransactionUpdateReqVO transaction_in = new TransactionUpdateReqVO();
                    BeanUtils.copyBeanProp(transaction_in, goodsDO);
                    transaction_in.setTransactionType(transactionType_in);
                    transaction_in.setTransactionFlag(1);//库存增加
                    transaction_in.setTransactionQuantity(transactionQuantity);
                    //由于是新增的库存记录所以需要将查询出来的库存记录ID置为空
                    transaction_in.setMaterialStockId(null);
                    //使用出库事务的供应商初始化入库事务的供应商
                    transaction_in.setVendorId(transaction_out.getVendorId());
                    transaction_in.setVendorCode(transaction_out.getVendorCode());
                    transaction_in.setVendorName(transaction_out.getVendorName());
                    transaction_in.setVendorNick(transaction_out.getVendorNick());
                    transaction_in.setItemId(mdItemDO.getId().longValue());
                    transaction_in.setItemCode(mdItemDO.getItemCode());
                    transaction_in.setItemName(mdItemDO.getItemName());
                    transaction_in.setSpecification(mdItemDO.getSpecification());

                    //这里使用系统默认生成的线边库初始化对应的入库仓库、库区、库位
                    WarehouseDO warehouse = warehouseService.selectWmWarehouseByWarehouseCode(materialStock.getWarehouseCode());
                    transaction_in.setWarehouseId(warehouse.getId());
                    transaction_in.setWarehouseCode(warehouse.getWarehouseCode());
                    transaction_in.setWarehouseName(warehouse.getWarehouseName());
                    StorageLocationDO location = storageLocationService.selectWmStorageLocationByLocationCode(materialStock.getLocationCode());
                    transaction_in.setLocationId(location.getId());
                    transaction_in.setLocationCode(location.getLocationCode());
                    transaction_in.setLocationName(location.getLocationName());
                    StorageAreaDO area = storageAreaService.selectWmStorageAreaByAreaCode(materialStock.getAreaCode());
                    transaction_in.setAreaId(area.getId());
                    transaction_in.setAreaCode(area.getAreaCode());
                    transaction_in.setAreaName(area.getAreaName());
                    transaction_in.setSourceDocId(parent.getPurchaseId().longValue());
                    transaction_in.setSourceDocCode(poNo);
                    transaction_in.setSourceDocLineId(lineId.longValue());
                    //设置入库相关联的出库事务ID
                    transaction_in.setRelatedTransactionId(transaction_out.getId());
                    transactionService.processTransaction(transaction_in);

                    // 更新拆分的条码状态
                    goodsDO.setStatus(3); // 已入库
                    goodsDO.setId(lineId);
                    goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(goodsDO));
                }
            }
            // 修改原有单据的数量
            parent.setReceiveNum(parent.getReceiveNum().subtract(updateCount));
            //parent.setQuantity(parent.getQuantity().subtract(updateCount)); // 收货数量不允许修改
            // 只有原单才会记录拆分数量
            if(splitFlag){
                parent.setReceivedNum(parent.getReceivedNum() == null ? updateCount : parent.getReceivedNum().add(updateCount)); // 追加单据拆分状态
            }else{
                parent.setQuantity(parent.getQuantity().subtract(updateCount));
            }

            goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(parent));
            // 若原有单据数量为空, 删除
            if (parent.getReceiveNum().compareTo(BigDecimal.ZERO) <= 0) {
                // goodsService.deleteGoods(parent.getId());
                // 清空库存表中当前物料的数量信息
                MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
                exportReqVO.setItemCode(goodsNumber);
                exportReqVO.setBatchCode(parent.getBatchCode());
                List<MaterialStockDO> materialStockDO = materialStockService.getMaterialStockList(exportReqVO);
                if (materialStockDO.size() > 0) {
                    materialStockDO.get(0).setQuantityOnhand(BigDecimal.ZERO);
                    materialStockService.updateMaterialStock(MaterialStockConvert.INSTANCE.convert02(materialStockDO.get(0)));
                    // materialStockService.deleteMaterialStock(materialStockDO.get(0).getId());
                }
            }

        } finally {
            // 确保锁被释放
            if (locked) {
                try {
                    String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                            "return redis.call('del', KEYS[1]) " +
                            "else return 0 end";
                    Long deleteResult = (Long) redisTemplate.execute(
                            new DefaultRedisScript<>(luaScript, Long.class),
                            Arrays.asList(lockKey),
                            lockValue
                    );
                } catch (Exception e) {
                    System.out.println("释放分布式锁异常: " + e + ", Key: " + lockKey);
                }
            }
        }

        return success("操作成功");
    }

    @GetMapping("/getPurchaseBarCode")
    @Operation(summary = "获得采购商品明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<GoodsRespVO> getPurchaseBarCode(@RequestParam("id") Integer id) {
        GoodsDO goods = goodsService.getGoods(id);
        return success(GoodsConvert.INSTANCE.convert(goods));
    }


    @GetMapping("/checkConfig")
    @Operation(summary = "判定采购单是否正确配置")
    @Parameter(name = "poNo", description = "编号", required = true, example = "AMCG001-001")
    public CommonResult<List<GoodsDO>> checkConfig(@RequestParam("poNo") String poNo) {
        GoodsExportReqVO exportReqVO = new GoodsExportReqVO();
        exportReqVO.setPoNo(poNo);
        List<GoodsDO> goodsList = goodsService.getGoodsList(exportReqVO);
        if (goodsList.isEmpty()) {
            return error(ErrorCodeConstants.GOODS_NOT_EXISTS);
        }
        // 判定当前goodsList列表中的每一项是否都配置了收货数量与收货单位
        for (GoodsDO goodsDO : goodsList) {
            if (goodsDO.getReceiveNum() == null || goodsDO.getUnitOfMeasure() == null) {
                return error(ErrorCodeConstants.GOODS_NOT_CONFIG);
            }
            if (goodsDO.getStatus() == 0) {
                return error(ErrorCodeConstants.GOODS_NOT_RECEIVE);
            }
        }
        return success(goodsList);
    }

    @GetMapping("/checkOrigin")
    @Operation(summary = "判定采购单是否为来源单")
    @Parameter(name = "poNo", description = "编号", required = true, example = "AMCG001-001")
    public CommonResult<String> checkOrigin(@RequestParam("id") Integer id) {
        GoodsDO goods = goodsService.getGoods(id);
        GoodsExportReqVO exportReqVO = new GoodsExportReqVO();
        exportReqVO.setPoNo(goods.getPoNo());
        exportReqVO.setGoodsNumber(goods.getGoodsNumber());
        List<GoodsDO> goodsList = goodsService.getGoodsList(exportReqVO);
        if (goodsList.isEmpty()) {
            return error(ErrorCodeConstants.GOODS_NOT_EXISTS);
        }
        goodsList.sort(Comparator.comparing(GoodsDO::getCreateTime));
        GoodsDO originGoods = goodsList.get(0);
        if(!originGoods.getId().equals(goods.getId())){
            return error(ErrorCodeConstants.FIND_SUB_GOODS);
        }
        return success("success");
    }

    @PostMapping("/receiving")
    public CommonResult<String> receiving(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> goodsList = (List<Map<String, Object>>) params.get("list");
        // 外层分组：采购单号_供应商编码 -> 内层分组：料号_项次_项序_母批次 -> 汇总后的商品
        Map<String, Map<String, Map<String, Object>>> outerGroupMap = new HashMap<>();
        // 原始数据分组：采购单号_供应商编码 -> 原始商品列表
        Map<String, List<Map<String, Object>>> originalGroupMap = new HashMap<>();

        for (Map<String, Object> goodsDO : goodsList) {
            String poNo = goodsDO.get("poNo").toString();
            String goodsNumber =(String) goodsDO.get("goodsNumber");

            List<GoodsDO> list = goodsService.getGoodsList(new GoodsExportReqVO().setPoNo(poNo).setGoodsNumber(goodsNumber));
            if(list.isEmpty()){
                return error(ErrorCodeConstants.GOODS_NOT_EXISTS);
            }
            // list根据创建时间排序
            list.sort(Comparator.comparing(GoodsDO::getCreateTime));
            GoodsDO goods = list.get(0);
            if(goodsDO.get("id").equals(goods.getId())){
                return error(ErrorCodeConstants.FIND_ORIGIN_GOODS);
            }
        }

        for (Map<String, Object> goodsDO : goodsList) {
            BigDecimal receiveNum = goodsDO.get("receiveNum") == null ? BigDecimal.ZERO : new BigDecimal(String.valueOf(goodsDO.get("receiveNum")));
            if (receiveNum.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            String poNo = goodsDO.get("poNo").toString();
            String vendorCode = Optional.ofNullable(goodsDO.get("vendorCode").toString()).orElse("");
            String outerKey = poNo + "_" + vendorCode;

            // 构建原始商品Map（不汇总）
            Map<String, Object> originalGoodsMap = createGoodsMap(goodsDO, poNo, vendorCode);

            // 保存原始数据到分组
            if (!originalGroupMap.containsKey(outerKey)) {
                originalGroupMap.put(outerKey, new ArrayList<>());
            }
            originalGroupMap.get(outerKey).add(originalGoodsMap);

            // 构建内层分组Key：料号_项次_项序_母批次
            String innerKey = createInnerKey(goodsDO);

            // 初始化外层分组
            if (!outerGroupMap.containsKey(outerKey)) {
                outerGroupMap.put(outerKey, new HashMap<>());
            }
            Map<String, Map<String, Object>> innerGroupMap = outerGroupMap.get(outerKey);

            // 处理内层分组：存在则累加数量，不存在则新建
            if (innerGroupMap.containsKey(innerKey)) {
                Map<String, Object> mergedGoods = innerGroupMap.get(innerKey);
                Number mergedNum = (Number) mergedGoods.get("receiveNum");
                BigDecimal mergedNumBigDecimal = mergedNum == null ? BigDecimal.ZERO : new BigDecimal(mergedNum.toString());
                mergedGoods.put("receiveNum", mergedNumBigDecimal.add(receiveNum));
            } else {
                // 新建汇总商品（使用原始商品数据）
                Map<String, Object> mergedGoods = createGoodsMap(goodsDO, poNo, vendorCode);
                innerGroupMap.put(innerKey, mergedGoods);
            }
        }

        // 准备最终的分组数据（将内层Map转换为List）
        Map<String, List<Map<String, Object>>> groupedGoodsMap = new HashMap<>();
        for (Map.Entry<String, Map<String, Map<String, Object>>> outerEntry : outerGroupMap.entrySet()) {
            groupedGoodsMap.put(outerEntry.getKey(), new ArrayList<>(outerEntry.getValue().values()));
        }

        // 调用ERP接口
        for (Map.Entry<String, List<Map<String, Object>>> entry : groupedGoodsMap.entrySet()) {
            String outerKey = entry.getKey();
            List<Map<String, Object>> mergedList = entry.getValue();

            Map<String, Object> erpParams = new HashMap<>(params);
            erpParams.put("goodsList", mergedList);
            erpParams.put("sourceNo", mergedList.get(0).get("poNo"));
            erpParams.put("supplierCode", mergedList.get(0).get("supplierCode"));
            erpParams.put("poNo", mergedList.get(0).get("poNo"));
            erpParams.put("pmds000", "1"); // 采购收货

            String result = materialStockERPAPI.purchaseDeliveryCreate(erpParams);
            // String result = "error";
            if (!result.contains("success")) {
                // return result;
                return error(500 , result);
            }
            String warehousingCode = result.split(",")[1];

            // 使用原始分组数据进行更新
            List<Map<String, Object>> originalList = originalGroupMap.get(outerKey);
            List<GoodsDO> update = new ArrayList<>();
            List<GoodsDO> updateSeq = new ArrayList<>();

            for (Map<String, Object> goodsMap : originalList) {
                Integer id = (Integer) goodsMap.get("id");
                GoodsDO goodsDO = goodsService.getGoods(id);
                goodsDO.setErpReceiveCode(warehousingCode);
                goodsDO.setStatus(1); // 未收货 => 未打印
                update.add(goodsDO);
            }
            goodsService.updateBatch(update);

            for (Map<String, Object> goodsMap : originalList) {
                Integer id = (Integer) goodsMap.get("id");
                GoodsDO goodsDO = goodsService.getGoods(id);
                Map<String,Object> seqMap = orderOracleService.getReceiveSeq(goodsDO);
                Number receiveSeqBigDecimal = (Number) seqMap.get("RECEIVE_SEQ");
                goodsDO.setReceiveSeq(receiveSeqBigDecimal.intValue());
                updateSeq.add(goodsDO);
            }

            if (!updateSeq.isEmpty()) {
                goodsService.updateBatch(updateSeq);
            }
        }
        // return "success";
        return success("success");

    }

    private String createInnerKey(Map<String, Object> goodsDO) {
        return StringUtils.defaultString(goodsDO.get("goodsNumber").toString()) + "_" +
                StringUtils.defaultString(goodsDO.get("purchaseBatch").toString()) + "_" +
                StringUtils.defaultString(goodsDO.get("purchaseConsequence").toString()) + "_" +
                StringUtils.defaultString(goodsDO.get("parentBatchCode").toString());
    }

    // 构建商品Map（原始结构）
    private Map<String, Object> createGoodsMap(Map<String, Object> goodsDO, String poNo, String vendorCode) {
        Map<String, Object> goodsMap = new HashMap<>();
        goodsMap.put("id", goodsDO.get("id"));
        goodsMap.put("poNo", poNo);
        goodsMap.put("goodsNumber", goodsDO.get("goodsNumber"));
        goodsMap.put("goodsName", goodsDO.get("goodsName"));
        goodsMap.put("unitOfMeasure", goodsDO.get("unitOfMeasure"));
        goodsMap.put("receiveNum", goodsDO.get("receiveNum"));
        goodsMap.put("batchCode", goodsDO.get("parentBatchCode"));
        goodsMap.put("consequence", goodsDO.get("consequence"));
        goodsMap.put("purchaseBatch", goodsDO.get("purchaseBatch"));
        goodsMap.put("purchaseConsequence", goodsDO.get("purchaseConsequence"));
        goodsMap.put("purchaseBatchConsequence", goodsDO.get("purchaseBatchConsequence"));
        goodsMap.put("supplierCode", vendorCode);
        return goodsMap;
    }


    /**
     * 物料追溯
     *
     * @param pageVO
     * @return
     */
    @GetMapping("/traceGoodsPage")
    @Operation(summary = "获得采购商品明细分页")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<PageResult<GoodsRespVO>> getTraceGoodsPage(@Valid GoodsPageReqVO pageVO) {
        // 已知参数: 工单号, 任务编号, 批次号
        String workorderCode = pageVO.getWorkorderCode();
        String taskCode = pageVO.getTaskCode();
        String batchCode = pageVO.getBatchCode();

        if(workorderCode == null && taskCode == null && batchCode == null){
            return success();
        }

        // 查询批次信息 - 使用Set避免重复
        Set<String> batchSet = new HashSet<>();

        if (workorderCode != null && !"".equals(workorderCode)) {
            // 基于工单获取所有领料单上料详情
            List<IssueheaderDTO> issueheaderDTOS = issueApi.listIssueHeader(new IssueheaderDTO().setWorkorderCode(workorderCode));
            for (IssueheaderDTO issueheaderDTO : issueheaderDTOS) {
                // 找寻上料详情(已领料已报工)
                List<IssueLineDTO> issueLineDTOS = issueApi.listIssueLine(new IssueLineDTO().setIssueId(issueheaderDTO.getId()).setStatus("Y").setFeedbackStatus("Y"));
                for (IssueLineDTO issueLineDTO : issueLineDTOS) {
                    batchSet.add(issueLineDTO.getBatchCode());
                }
            }
        }

        if (taskCode != null && !"".equals(taskCode)) {
            // 基于任务单获取领料详情
            List<IssueheaderDTO> issueheaderDTOS = issueApi.listIssueHeader(new IssueheaderDTO().setTaskCode(taskCode));
            if (!issueheaderDTOS.isEmpty()) {
                // 一个任务单对应一个领料单头
                IssueheaderDTO issueheaderDTO = issueheaderDTOS.get(0);
                List<IssueLineDTO> issueLineDTOS = issueApi.listIssueLine(new IssueLineDTO().setIssueId(issueheaderDTO.getId()).setStatus("Y").setFeedbackStatus("Y"));
                for (IssueLineDTO issueLineDTO : issueLineDTOS) {
                    batchSet.add(issueLineDTO.getBatchCode());
                }
            }
        }

        Set<String> finalBatchSet = new HashSet<>();
        if (batchCode != null) {
            batchSet.add(batchCode);
        }

        if (!batchSet.isEmpty()) {
            Set<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>(batchSet);
            while (!queue.isEmpty()) {
                String current = queue.poll();
                if (visited.contains(current)) continue;
                visited.add(current);
                if (!current.startsWith("TASK")) {
                    finalBatchSet.add(current);
                    continue;
                }
                List<String> origins = getOriginBatchCode(current);
                for (String origin : origins) {
                    if (origin != null && !visited.contains(origin)) {
                        queue.add(origin);
                    }
                }
            }
        }
        List<String> batchList = new ArrayList<>(finalBatchSet);
        if(batchList.isEmpty()){
            return success();
        }
        GoodsPageReqVO queryVO = new GoodsPageReqVO();
        queryVO.setPageNo(pageVO.getPageNo()).setPageSize(pageVO.getPageSize());
        queryVO.setBatchCodeList(batchList);
        PageResult<GoodsDO> pageResult = goodsService.getGoodsPage(queryVO);
        return success(GoodsConvert.INSTANCE.convertPage(pageResult));
    }

    public List<String> getOriginBatchCode(String batchCode) {
        List<String> batchCodeList = new ArrayList<>();
        if (batchCode != null) {
            // 判定批次号是否以TASK开头
            if (batchCode.startsWith("TASK")) {
                FeedbackDTO feedbackDTO = Optional.ofNullable(feedbackApi.getFeedBackByBatchCode(batchCode)).orElse(null);
                if (feedbackDTO != null) {
                    // 找寻对应领料单行, 获取采购项
                    List<IssueLineDTO> issueLineDTOS = issueApi.listIssueLine(new IssueLineDTO().setFeedbackCode(feedbackDTO.getFeedbackCode()));
                    if (!issueLineDTOS.isEmpty()) {
                        for (IssueLineDTO issueLineDTO : issueLineDTOS) {
                            batchCodeList.add(issueLineDTO.getBatchCode());
                        }
                    }
                }
            } else {
                // 如果批次号不以TASK开头，停止搜索
                return batchCodeList;
            }
        }
        return batchCodeList;
    }

    @GetMapping("/initIqcQuantity")
    @Operation(summary = "来料检初始化某单据某物料总数")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<BigDecimal> initIqcQuantity(@Valid GoodsDO goodsDO) {
        // 检查采购单号和物料编号是否为空
        if (goodsDO.getPoNo() == null || goodsDO.getGoodsNumber() == null) {
            return error(ErrorCodeConstants.GOODS_NOT_EXISTS);
        }
        // 根据采购单号查询原始商品列表
        List<GoodsDO> queryGoods = goodsService.getGoodsList(new GoodsExportReqVO().setPoNo(goodsDO.getPoNo()).setGoodsNumber(goodsDO.getGoodsNumber()).setStatus(2));
        if (queryGoods == null || queryGoods.isEmpty()) {
            return error(ErrorCodeConstants.GOODS_NOT_EXISTS);
        }
        BigDecimal totalQuantity = BigDecimal.ZERO;
        for (GoodsDO goods : queryGoods) {
            BigDecimal receiveNum = Optional.ofNullable(goods.getReceiveNum()).orElse(BigDecimal.ZERO);
            totalQuantity = totalQuantity.add(receiveNum);
        }
        return success(totalQuantity);
    }

    // 格式化错误信息
    private String formatErrors(Map<String, String> errorMap) {
        return errorMap.entrySet().stream()
                .map(entry -> "收货单号[" + entry.getKey() + "]: ")
                .collect(Collectors.joining("; "));
    }

    /*@PostMapping("/mergeGoods")
    @Operation(summary = "合并采购商品明细")
    @PreAuthorize("@ss.hasPermission('purchase:goods:update')")
    public CommonResult<String> mergeGoods(@RequestBody Map<String, Object> params) {
        List<Integer> ids = (List<Integer>) params.get("ids");
        if (ids == null || ids.size() < 2) {
            return error(ErrorCodeConstants.MERGE_AT_LEAST_TWO);
        }

        // 获取所有要合并的商品明细
        List<GoodsDO> goodsList = goodsService.getGoodsList(ids);
        if (goodsList.size() != ids.size()) {
            return error(ErrorCodeConstants.GOODS_NOT_EXISTS);
        }

        // 检查状态并分组
        Map<Integer, List<GoodsDO>> statusGroups = goodsList.stream()
                .collect(Collectors.groupingBy(GoodsDO::getStatus));

        // 检查是否包含已入库的商品
        if (statusGroups.containsKey(3)) {
            return error(ErrorCodeConstants.MERGE_ALREADY_WAREHOUSED);
        }

        // 检查所有单据状态是否相同
        if (statusGroups.size() > 1) {
            return error(ErrorCodeConstants.MERGE_SAME_STATUS);
        }

        // 检查采购单号和物料编号是否一致
        String poNo = goodsList.get(0).getPoNo();
        String goodsNumber = goodsList.get(0).getGoodsNumber();
        for (GoodsDO goods : goodsList) {
            if (!poNo.equals(goods.getPoNo()) || !goodsNumber.equals(goods.getGoodsNumber())) {
                return error(ErrorCodeConstants.MERGE_SAME_PO_AND_ITEM);
            }
        }

        // 检查未打印和已打印状态的ERP收货单号是否一致
        Integer status = goodsList.get(0).getStatus();
        if (status == 1 || status == 2) {
            String erpReceiveCode = goodsList.get(0).getErpReceiveCode();
            for (GoodsDO goods : goodsList) {
                if (!erpReceiveCode.equals(goods.getErpReceiveCode())) {
                    return error(ErrorCodeConstants.MERGE_SAME_ERP_RECEIVE_CODE);
                }
            }
        }

        // 按收货数量降序排序，选择收货数量最大的记录作为主记录
        goodsList.sort((g1, g2) -> {
            BigDecimal num1 = g1.getReceiveNum() != null ? g1.getReceiveNum() : BigDecimal.ZERO;
            BigDecimal num2 = g2.getReceiveNum() != null ? g2.getReceiveNum() : BigDecimal.ZERO;
            return num2.compareTo(num1);
        });

        GoodsDO mainGoods = goodsList.get(0);
        BigDecimal totalReceiveNum = mainGoods.getReceiveNum() != null ? mainGoods.getReceiveNum() : BigDecimal.ZERO;
        BigDecimal totalReceivedNum = mainGoods.getReceivedNum() != null ? mainGoods.getReceivedNum() : BigDecimal.ZERO;
        BigDecimal totalQuantity = mainGoods.getQuantity() != null ? mainGoods.getQuantity() : BigDecimal.ZERO;

        // 收集需要删除的ID（没有已拆分数量的单据）
        List<Integer> idsToDelete = new ArrayList<>();

        // 计算被合并总数
        BigDecimal sumReceiveNum = BigDecimal.ZERO;

        // 检查是否所有单据都没有已拆分数量
        boolean allNoReceivedNum = true;
        for (GoodsDO goods : goodsList) {
            if (goods.getReceivedNum() != null) {
                allNoReceivedNum = false;
                break;
            }
        }

        // 汇总收货数量、已拆分数量和采购数量
        for (int i = 1; i < goodsList.size(); i++) {
            GoodsDO goods = goodsList.get(i);
            totalReceiveNum = totalReceiveNum.add(goods.getReceiveNum() != null ? goods.getReceiveNum() : BigDecimal.ZERO);
            sumReceiveNum = sumReceiveNum.add(goods.getReceiveNum() != null ? goods.getReceiveNum() : BigDecimal.ZERO);

            // 如果所有单据都没有已拆分数量，则汇总采购数量
            if (allNoReceivedNum) {
                totalQuantity = totalQuantity.add(goods.getQuantity() != null ? goods.getQuantity() : BigDecimal.ZERO);
            }

            // 检查是否有已拆分数量字段
            if (goods.getReceivedNum() != null) {
                // 有已拆分数量，不删除，只更新状态和数量
                totalReceivedNum = totalReceivedNum.add(goods.getReceiveNum());
                goods.setReceiveNum(BigDecimal.ZERO);
                goods.setStatus(mainGoods.getStatus());
                goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(goods));
            } else {
                // 没有已拆分数量，添加到删除列表
                idsToDelete.add(goods.getId());
            }
        }

        // 更新主记录
        mainGoods.setReceiveNum(totalReceiveNum);
        if (mainGoods.getReceivedNum() != null) {
            mainGoods.setReceivedNum(mainGoods.getReceivedNum().subtract(sumReceiveNum));
        }

        // 如果所有单据都没有已拆分数量，则更新采购数量
        if (allNoReceivedNum) {
            mainGoods.setQuantity(totalQuantity);
        }

        goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(mainGoods));

        // 删除没有已拆分数量的单据
        if (!idsToDelete.isEmpty()) {
            for (Integer id : idsToDelete) {
                goodsService.deleteGoods(id);
            }
        }

        return success("合并成功");
    }*/

    @PostMapping("/mergeGoods")
    @Operation(summary = "合并采购商品明细")
    @PreAuthorize("@ss.hasPermission('purchase:goods:update')")
    public CommonResult<String> mergeGoods(@RequestBody Map<String, Object> params) {
        List<Integer> ids = (List<Integer>) params.get("ids");
        if (ids == null || ids.size() < 2) {
            return error(ErrorCodeConstants.MERGE_AT_LEAST_TWO);
        }

        // 获取所有要合并的商品明细
        List<GoodsDO> goodsList = goodsService.getGoodsList(ids);
        if (goodsList.size() != ids.size()) {
            return error(ErrorCodeConstants.GOODS_NOT_EXISTS);
        }

        // 检查状态并分组
        Map<Integer, List<GoodsDO>> statusGroups = goodsList.stream()
                .collect(Collectors.groupingBy(GoodsDO::getStatus));

        // 检查是否包含已入库的商品
        if (statusGroups.containsKey(3)) {
            return error(ErrorCodeConstants.MERGE_ALREADY_WAREHOUSED);
        }

        // 检查所有单据状态是否相同
        if (statusGroups.size() > 1) {
            return error(ErrorCodeConstants.MERGE_SAME_STATUS);
        }

        // 检查采购单号和物料编号是否一致
        String poNo = goodsList.get(0).getPoNo();
        String goodsNumber = goodsList.get(0).getGoodsNumber();
        for (GoodsDO goods : goodsList) {
            if (!poNo.equals(goods.getPoNo()) || !goodsNumber.equals(goods.getGoodsNumber())) {
                return error(ErrorCodeConstants.MERGE_SAME_PO_AND_ITEM);
            }
        }

        // 检查未打印和已打印状态的ERP收货单号是否一致
        Integer status = goodsList.get(0).getStatus();
        if (status == 1 || status == 2) {
            String erpReceiveCode = goodsList.get(0).getErpReceiveCode();
            for (GoodsDO goods : goodsList) {
                if (!erpReceiveCode.equals(goods.getErpReceiveCode())) {
                    return error(ErrorCodeConstants.MERGE_SAME_ERP_RECEIVE_CODE);
                }
            }
        }

        // 按收货数量降序排序，选择收货数量最大的记录作为主记录
        goodsList.sort((g1, g2) -> {
            BigDecimal num1 = g1.getReceiveNum() != null ? g1.getReceiveNum() : BigDecimal.ZERO;
            BigDecimal num2 = g2.getReceiveNum() != null ? g2.getReceiveNum() : BigDecimal.ZERO;
            return num2.compareTo(num1);
        });

        GoodsDO mainGoods = goodsList.get(0);
        BigDecimal totalReceiveNum = mainGoods.getReceiveNum() != null ? mainGoods.getReceiveNum() : BigDecimal.ZERO;
        BigDecimal totalReceivedNum = mainGoods.getReceivedNum() != null ? mainGoods.getReceivedNum() : BigDecimal.ZERO;
        BigDecimal totalQuantity = mainGoods.getQuantity() != null ? mainGoods.getQuantity() : BigDecimal.ZERO;

        List<Integer> idsToDelete = new ArrayList<>();

        // 计算被合并总数
        BigDecimal sumReceiveNum = BigDecimal.ZERO;

        // 检查是否所有单据都没有已拆分数量
        boolean allNoReceivedNum = true;
        for (GoodsDO goods : goodsList) {
            if (goods.getReceivedNum() != null) {
                allNoReceivedNum = false;
                break;
            }
        }

        // 汇总收货数量、已拆分数量和采购数量
        for (int i = 1; i < goodsList.size(); i++) {
            GoodsDO goods = goodsList.get(i);
            totalReceiveNum = totalReceiveNum.add(goods.getReceiveNum() != null ? goods.getReceiveNum() : BigDecimal.ZERO);
            sumReceiveNum = sumReceiveNum.add(goods.getReceiveNum() != null ? goods.getReceiveNum() : BigDecimal.ZERO);

            // 如果所有单据都没有已拆分数量，则汇总采购数量
            if (allNoReceivedNum) {
                totalQuantity = totalQuantity.add(goods.getQuantity() != null ? goods.getQuantity() : BigDecimal.ZERO);
            }

            // 检查是否有已拆分数量字段
            if (goods.getReceivedNum() != null) {
                // 有已拆分数量，不删除，只更新状态和数量
                totalReceivedNum = totalReceivedNum.add(goods.getReceiveNum());
                goods.setReceiveNum(BigDecimal.ZERO);
                goods.setStatus(mainGoods.getStatus());
                goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(goods));
            } else {
                // 没有已拆分数量，添加到删除列表
                idsToDelete.add(goods.getId());
            }
        }

        // 根据状态规则更新主记录状态
        Integer finalStatus = mainGoods.getStatus();
        if (finalStatus == 1 || finalStatus == 2) {
            finalStatus = 1;
        }

        mainGoods.setReceiveNum(totalReceiveNum);
        if (mainGoods.getReceivedNum() != null) {
            mainGoods.setReceivedNum(mainGoods.getReceivedNum().subtract(sumReceiveNum));
        }

        // 若合并单据都没有已拆分数量，则更新采购数量
        if (allNoReceivedNum) {
            mainGoods.setQuantity(totalQuantity);
        }

        // 更新状态
        mainGoods.setStatus(finalStatus);

        goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(mainGoods));

        // 删除没有已拆分数量的单据
        if (!idsToDelete.isEmpty()) {
            for (Integer id : idsToDelete) {
                goodsService.deleteGoods(id);
            }
        }

        return success("合并成功");
    }

}
