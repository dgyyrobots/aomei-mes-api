package com.dofast.module.purchase.controller.admin.retreatOrder;

import com.dofast.module.purchase.controller.admin.retreatGoods.vo.RetreatGoodsExportReqVO;
import com.dofast.module.purchase.convert.retreatOrder.RetreatOrderConvert;
import com.dofast.module.purchase.dal.dataobject.goods.GoodsDO;
import com.dofast.module.purchase.dal.dataobject.order.OrderDO;
import com.dofast.module.purchase.dal.dataobject.retreatGoods.RetreatGoodsDO;
import com.dofast.module.purchase.dal.dataobject.retreatOrder.RetreatOrderDO;
import com.dofast.module.purchase.dal.mysql.order.PurchaseOrderMapper;
import com.dofast.module.purchase.service.goods.GoodsService;
import com.dofast.module.purchase.service.order.OrderService;
import com.dofast.module.purchase.service.retreatGoods.RetreatGoodsService;
import com.dofast.module.wms.api.ERPApi.MaterialStockERPAPI;
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
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.purchase.controller.admin.retreatOrder.vo.*;

import com.dofast.module.purchase.service.retreatOrder.RetreatOrderService;

@Tag(name = "管理后台 - ERP仓退单")
@RestController
@RequestMapping("/purchase/retreatOrder")
@Validated
public class RetreatOrderController {

    @Resource
    private RetreatOrderService retreatOrderService;

    @Resource
    private RetreatGoodsService retreatGoodsService;

    @Resource
    private MaterialStockERPAPI materialStockERPAPI;

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderService orderService;


    @PostMapping("/create")
    @Operation(summary = "创建ERP仓退单")
    @PreAuthorize("@ss.hasPermission('purchase:order:create')")
    public CommonResult<Long> createOrder(@Valid @RequestBody RetreatOrderCreateReqVO createReqVO) {
        return success(retreatOrderService.createOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新ERP仓退单")
    @PreAuthorize("@ss.hasPermission('purchase:order:update')")
    public CommonResult<Boolean> updateOrder(@Valid @RequestBody RetreatOrderUpdateReqVO updateReqVO) {
        retreatOrderService.updateOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除ERP仓退单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('purchase:order:delete')")
    public CommonResult<Boolean> deleteOrder(@RequestParam("id") Long id) {
        retreatOrderService.deleteOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得ERP仓退单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('purchase:order:query')")
    public CommonResult<RetreatOrderRespVO> getOrder(@RequestParam("id") Long id) {
        RetreatOrderDO order = retreatOrderService.getOrder(id);
        return success(RetreatOrderConvert.INSTANCE.convert(order));
    }

    @GetMapping("/list")
    @Operation(summary = "获得ERP仓退单列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('purchase:order:query')")
    public CommonResult<List<RetreatOrderRespVO>> getOrderList(@RequestParam("ids") Collection<Long> ids) {
        List<RetreatOrderDO> list = retreatOrderService.getOrderList(ids);
        return success(RetreatOrderConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得ERP仓退单分页")
    @PreAuthorize("@ss.hasPermission('purchase:order:query')")
    public CommonResult<PageResult<RetreatOrderRespVO>> getOrderPage(@Valid RetreatOrderPageReqVO pageVO) {
        PageResult<RetreatOrderDO> pageResult = retreatOrderService.getOrderPage(pageVO);
        return success(RetreatOrderConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出ERP仓退单 Excel")
    @PreAuthorize("@ss.hasPermission('purchase:order:export')")
    @OperateLog(type = EXPORT)
    public void exportOrderExcel(@Valid RetreatOrderExportReqVO exportReqVO,
                                 HttpServletResponse response) throws IOException {
        List<RetreatOrderDO> list = retreatOrderService.getOrderList(exportReqVO);
        // 导出 Excel
        List<RetreatOrderExcelVO> datas = RetreatOrderConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "ERP仓退单.xls", "数据", RetreatOrderExcelVO.class, datas);
    }

    @PostMapping("/retreat")
    public String retreat(@RequestBody RetreatOrderDO retreatOrderDO) {
        // 基于退料单头Id获取退料单身信息
        RetreatGoodsExportReqVO exportReqVO = new RetreatGoodsExportReqVO();
        exportReqVO.setRetreatId(Math.toIntExact(retreatOrderDO.getId()));
        List<RetreatGoodsDO> goodsList = retreatGoodsService.getGoodsList(exportReqVO);

        List<Map<String, Object>> goodsMapList = new ArrayList<>();
        for (RetreatGoodsDO goodsDO : goodsList) {
            BigDecimal receiveNum = goodsDO.getReceiveNum() == null ? BigDecimal.ZERO : new BigDecimal(String.valueOf(goodsDO.getReceiveNum()));
            if (receiveNum.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            String vendorCode = retreatOrderDO.getVendorCode(); // ERP收货时批次绑定在单头, 故取单头批次

            OrderDO orderDO = orderService.getOrder(goodsDO.getPoNo());
            // 将当前的商品信息添加到对应的List中
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("id", goodsDO.getId());
            goodsMap.put("poNo", goodsDO.getPoNo());
            goodsMap.put("purchaseBatch", goodsDO.getPurchaseBatch());
            goodsMap.put("purchaseConsequence", goodsDO.getPurchaseConsequence());
            goodsMap.put("purchaseBatchConsequence", goodsDO.getPurchaseBatchConsequence());
            goodsMap.put("warehousingCode", goodsDO.getErpReceiveCode());
            goodsMap.put("warehousingSeq", goodsDO.getReceiveSeq());

            goodsMap.put("goodsNumber", goodsDO.getGoodsNumber());
            goodsMap.put("goodsName", goodsDO.getGoodsName());
            goodsMap.put("unitOfMeasure", goodsDO.getUnitOfMeasure());
            goodsMap.put("receiveNum", goodsDO.getReceiveNum());

            // 2025-06-08 追加母批次
            // goodsMap.put("batchCode", goodsDO.getBatchCode());
            goodsMap.put("batchCode", orderDO.getParentBatchCode());

            goodsMap.put("consequence", goodsDO.getConsequence());
            goodsMap.put("supplierCode", vendorCode);
            goodsMap.put("locationCode", goodsDO.getLocationCode());
            goodsMap.put("areaCode", goodsDO.getAreaCode());
            goodsMap.put("reasonCode", goodsDO.getReasonCode());

            goodsMapList.add(goodsMap);
        }
        System.out.println(goodsMapList.toString());
        // 调用ERP接口
        Map<String, Object> erpParams = new HashMap<>();
        erpParams.put("goodsList", goodsMapList);
        erpParams.put("sourceNo", goodsMapList.get(0).get("warehousingCode")); // 仓退单的来源单号为收货单号
        erpParams.put("supplierCode", goodsMapList.get(0).get("supplierCode"));
        erpParams.put("poNo", goodsMapList.get(0).get("poNo"));
        erpParams.put("retreatCode", retreatOrderDO.getRetreatCode());
        erpParams.put("pmds000", "7"); // 仓库退货
        String result = materialStockERPAPI.purchaseDeliveryCreate(erpParams);
        if (!result.contains("success")) {
            return result;
        }
        return "success";
    }


}
