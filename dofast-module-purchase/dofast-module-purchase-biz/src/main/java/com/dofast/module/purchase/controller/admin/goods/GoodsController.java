package com.dofast.module.purchase.controller.admin.goods;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.dofast.framework.common.util.bean.BeanUtils;
import com.dofast.framework.common.util.http.HttpUtils;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.service.mditem.MdItemService;
import com.dofast.module.pro.api.FeedbackApi.FeedbackApi;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.purchase.convert.order.OrderConvert;
import com.dofast.module.purchase.dal.dataobject.order.OrderDO;
import com.dofast.module.purchase.dal.mysql.goods.GoodsMapper;
import com.dofast.module.purchase.dal.mysql.order.PurchaseOrderMapper;
import com.dofast.module.purchase.enums.ErrorCodeConstants;
import com.dofast.module.purchase.service.order.OrderService;
import com.dofast.module.tm.dal.dataobject.tool.ToolDO;
import com.dofast.module.tm.service.tool.ToolService;
import com.dofast.module.wms.api.MaterialStockApi.MaterialStockERPAPI;
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
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.service.materialstock.MaterialStockService;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagecore.StorageCoreService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.transaction.TransactionService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

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
        goodsService.updateGoods(updateReqVO);
        return success(true);
    }

    @PutMapping("/updateReceiveStatus")
    @Operation(summary = "更新采购商品明细")
    @PreAuthorize("@ss.hasPermission('purchase:goods:update')")
    public CommonResult<Boolean> updateReceiveStatus(@Valid @RequestBody List<GoodsUpdateReqVO> updateReqVOList) {
        System.out.println(updateReqVOList);
        if (!updateReqVOList.isEmpty()) {
            // 未入库 =》 已入库
            for (GoodsUpdateReqVO goodsUpdateReqVO : updateReqVOList) {
                Integer status = goodsUpdateReqVO.getStatus();
                if (status == 0) {
                    goodsUpdateReqVO.setStatus(1);
                    goodsService.updateGoods(goodsUpdateReqVO);
                }
            }
        }
        return success(true);
    }

    @PutMapping("/batchUpdateReceiveStatus")
    @Operation(summary = "更新采购商品明细")
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
        return success(GoodsConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得采购商品明细分页")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<PageResult<GoodsRespVO>> getGoodsPage(@Valid GoodsPageReqVO pageVO) {
        PageResult<GoodsDO> pageResult = goodsService.getGoodsPage(pageVO);
        return success(GoodsConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/allList")
    @Operation(summary = "获得采购商品明细列表")
    @PreAuthorize("@ss.hasPermission('purchase:goods:query')")
    public CommonResult<List<GoodsRespVO>> getGoodsAllList(@Valid GoodsDO goodsDO) {
        GoodsExportReqVO exportReqVO = new GoodsExportReqVO();
        goodsDO.setStatus(1);
        BeanUtils.copyProperties(goodsDO, exportReqVO);
        List<GoodsDO> pageResult = goodsService.getGoodsList(exportReqVO);
        return success(GoodsConvert.INSTANCE.convertList(pageResult));
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

    // 入库功能. 测试能否调用ERP接口
    @PostMapping("/wareHousing")
    public String wareHousing(@RequestBody Map<String, Object> params) {
        // 根据当前的入库单号获取入库单详情, 做入库操作
        String poNo = params.get("poNo").toString();
        Integer wareHouseId = (Integer) params.get("warehouseId");
        Integer locationId = (Integer) params.get("locationId");
        Integer areaId = (Integer) params.get("areaId");

        // 初始化订单详情
        OrderDO orderDO = orderMapper.selectOne(OrderDO::getPoNo, poNo);
        //Integer supplierId = Optional.ofNullable(orderDO.getSupplierId()).orElse(0);
        String supplierCode = Optional.ofNullable(orderDO.getSupplierCode()).orElse("");

        GoodsExportReqVO exportReqVO = new GoodsExportReqVO();
        exportReqVO.setPoNo(poNo);
        List<GoodsDO> goodsList = goodsService.getGoodsList(exportReqVO);
        List<Map<String, Object>> goodsMapList = new ArrayList<>();
        for (GoodsDO goodsDO : goodsList) {
            BigDecimal receiveNum = goodsDO.getReceiveNum() == null ? BigDecimal.ZERO : new BigDecimal(String.valueOf(goodsDO.getReceiveNum()));
            if (receiveNum.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("id", goodsDO.getId());
            goodsMap.put("poNo", goodsDO.getPoNo());
            goodsMap.put("goodsNumber", goodsDO.getGoodsNumber());
            goodsMap.put("goodsName", goodsDO.getGoodsName());
            goodsMap.put("unitOfMeasure", goodsDO.getUnitOfMeasure());
            goodsMap.put("receiveNum", goodsDO.getReceiveNum());
            goodsMap.put("batchCode", goodsDO.getBatchCode());
            goodsMapList.add(goodsMap);
        }
        System.out.println(goodsMapList);
        params.put("goodsList", goodsMapList);
        params.put("sourceNo", poNo);
        params.put("supplierCode", supplierCode);
        params.put("pmds000", "1"); // 采购收货
        // 调用ERP接口, 先收货
       /* String result = materialStockERPAPI.purchaseDeliveryCreate(params);
        if (!result.equals("success")) {
            return result;
        }*/
        params.put("pmds001", "6"); // 采购入库
        // 调用ERP接口, 再入库
       /* String wareHouseingResult = materialStockERPAPI.purchaseDeliveryCreate(params);
        if (!wareHouseingResult.equals("success")) {
            return wareHouseingResult;
        }*/

        List<ItemRecptTxBean> transactionList = new ArrayList<>();
        // 将数据库的数据追加到库存现有量中
        for (GoodsDO goodsDO : goodsList) {

            BigDecimal receiveNum = goodsDO.getReceiveNum() == null ? BigDecimal.ZERO : new BigDecimal(String.valueOf(goodsDO.getReceiveNum()));
            if (receiveNum.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            String batchCode = goodsDO.getBatchCode(); // 子批次号
            String itemCode = goodsDO.getGoodsNumber();
            MdItemDO mdItemDO = Optional.ofNullable(mdItemService.getMdItemByItemCode(itemCode)).orElse(null); // 根据商品编码获取商品信息
            String itemName = goodsDO.getGoodsName();
            String specification = goodsDO.getGoodsSpecs();
            String unitOfMeasure = goodsDO.getUnitOfMeasure(); // 收货单位
            BigDecimal quantity = goodsDO.getReceiveNum(); // 收货数量
            // 构建事务对象
            ItemRecptTxBean bean = new ItemRecptTxBean();
            bean.setId(goodsDO.getId().longValue()); // 物料Id
            bean.setItemCode(itemCode);
            bean.setItemName(itemName);
            bean.setSpecification(specification);
            bean.setUnitOfMeasure(unitOfMeasure);
            BigDecimal transactionQuantity = new BigDecimal(String.valueOf(goodsDO.getReceiveNum()));
            bean.setTransactionQuantity(transactionQuantity); // 事务数量
            bean.setBatchCode(batchCode); // 子批次号
            bean.setSourceDocCode(poNo);// 来源单据
            bean.setSourceDocType("PURCHASE"); // 来源单据类型
            bean.setSourceDocId(orderDO.getId().longValue()); // 来源单据行号
            bean.setSourceDocLineId(goodsDO.getId().longValue()); // 来源单据单身行号
            System.out.println("aaa:" + supplierCode);
            /*if (supplierId != 0) {
                bean.setVendorId(supplierId.longValue()); // 供应商ID
            }*/
            // TODO: 2025-1-6 获取供应商信息
            bean.setVendorCode(supplierCode); // 供应商编码
            // 追加库区库位
            //仓库
            WarehouseDTO warehouse = warehouseApi.getWarehouse(wareHouseId.longValue());
            bean.setWarehouseId(wareHouseId.longValue());
            bean.setWarehouseCode(warehouse.getWarehouseCode());
            bean.setWarehouseName(warehouse.getWarehouseName());
            //库区
            StorageLocationDTO location = storageLocationApi.getLocation(locationId.longValue());
            bean.setLocationId(locationId.longValue());
            bean.setLocationCode(location.getLocationCode());
            bean.setLocationName(location.getLocationName());
            //库位
            StorageAreaDTO area = storageAreaApi.getArea(areaId.longValue());
            bean.setAreaId(areaId.longValue());
            bean.setAreaCode(area.getAreaCode());
            bean.setAreaName(area.getAreaName());
            transactionList.add(bean);
            // 更新当前条码状态为已入库
            goodsDO.setStatus(2); // 已入库
            goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(goodsDO));
        }
        //调用库存核心
        storageCoreService.processItemRecpt(transactionList);

        return "success";
    }

    @PostMapping("/getStockByPurchaseId")
    @Operation(summary = "获得库存记录")
    public CommonResult<MaterialStockRespVO> getStockByPurchaseId(@RequestBody Map<String, Object> params) {
        // 根据采购单身Id
        Integer id = (Integer) params.get("id");
        String type = (String) params.get("type");
        String itemCode = "";
        String batchCode = "";
        String method = (String) params.get("method");
        boolean toolFlag = false;
        String recpt = Optional.ofNullable((String) params.get("recpt")).orElse("");

        MaterialStockRespVO toolResponse = new MaterialStockRespVO();

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
                break;
            case "tool":
                ToolDO toolDO = toolService.getTool(id.longValue());
                if(toolDO.getQuantityAvail() < 1){
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

        if (toolFlag) {
            return success(toolResponse);
        }

        // 根据单号不同获取对应库存信息
        MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
        exportReqVO.setItemCode(itemCode);
        exportReqVO.setBatchCode(batchCode);
        if (recpt != "N" && recpt != "") {
            exportReqVO.setRecptStatus("Y");
        }
        List<MaterialStockDO> materialStock = materialStockService.getMaterialStockList(exportReqVO);
        if (materialStock.isEmpty()) {
            return error(ErrorCodeConstants.MATERIAL_NOT_WAREHOUSE);
        }

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
    }


    @PostMapping("/splitGoods")
    public String splitGoods(@RequestBody Map<String, Object> params) {
        System.out.println(params);
        // 根据当前的入库单号获取入库单详情, 做入库操作
        String poNo = (String) params.get("poNo");
        Integer id = (Integer) params.get("id");
        String goodsNumber = (String) params.get("goodsNumber");
        String unitOfMeasure = (String) params.get("unitOfMeasure");
        List<Map<String, Object>> splitList = (List<Map<String, Object>>) params.get("splitDetails");
        GoodsDO parent = goodsService.getGoods(id);
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
            goodsDO.setReceiveTime(parent.getReceiveTime());
            goodsDO.setStatus(0);
            goodsDO.setConsequence(parent.getConsequence()); // 继承拆分行项次
            String serial = orderDO.getSerial();
            if (serial == null) {
                serial = "001";
            } else {
                int serialInt = Integer.parseInt(serial);
                serialInt++;
                serial = String.format("%03d", serialInt);
            }
            goodsDO.setBatchCode(orderDO.getParentBatchCode() + "-" + orderDO.getSerial());
            orderDO.setSerial(serial);
            orderService.updateOrder(OrderConvert.INSTANCE.convert01(orderDO));
            Integer lineId = goodsService.createGoods(GoodsConvert.INSTANCE.convert02(goodsDO));
            updateCount = updateCount.add(quantity);

            // 校验当前的单据是否已入库
            if (parent.getStatus() == 2) {
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
                goodsDO.setStatus(2); // 已入库
                goodsDO.setId(lineId);
                goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(goodsDO));
            }
        }
        // 修改原有单据的数量
        //parent.setReceiveNum(parent.getReceiveNum() - updateCount);
        //parent.setQuantity(parent.getQuantity() - updateCount);
        parent.setReceiveNum(parent.getReceiveNum().subtract(updateCount));
        parent.setQuantity(parent.getQuantity().subtract(updateCount));

        goodsService.updateGoods(GoodsConvert.INSTANCE.convert01(parent));
        // 若原有单据数量为空, 删除
        if (parent.getReceiveNum().compareTo(BigDecimal.ZERO) <= 0) {
            goodsService.deleteGoods(parent.getId());
            // 清空库存表中当前物料的数量信息
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setItemCode(goodsNumber);
            exportReqVO.setBatchCode(parent.getBatchCode());
            List<MaterialStockDO> materialStockDO = materialStockService.getMaterialStockList(exportReqVO);
            if (materialStockDO.size() > 0) {
                materialStockService.deleteMaterialStock(materialStockDO.get(0).getId());
            }
        }
        return "操作成功";
    }

    @GetMapping("/getPurchaseBarCode")
    @Operation(summary = "获得采购商品明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<GoodsRespVO> getPurchaseBarCode(@RequestParam("id") Integer id) {
        GoodsDO goods = goodsService.getGoods(id);
        return success(GoodsConvert.INSTANCE.convert(goods));
    }


    @GetMapping("/checkConfig")
    @Operation(summary = "获得采购商品明细")
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
        }
        return success(goodsList);
    }


}
