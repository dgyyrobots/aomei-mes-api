package com.dofast.module.purchase.controller.admin.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.mes.api.ItemApi.MdItemApi;
import com.dofast.module.mes.api.ItemApi.dto.MdItemDTO;
import com.dofast.module.mes.api.MdVendorApi.MdVendorApi;
import com.dofast.module.mes.api.MdVendorApi.dto.MdVendorDTO;
import com.dofast.module.purchase.controller.admin.retreatGoods.vo.RetreatGoodsExportReqVO;
import com.dofast.module.purchase.dal.dataobject.goods.GoodsDO;
import com.dofast.module.purchase.dal.dataobject.order.OrderDO;
import com.dofast.module.purchase.dal.dataobject.retreatGoods.RetreatGoodsDO;
import com.dofast.module.purchase.dal.dataobject.retreatOrder.RetreatOrderDO;
import com.dofast.module.purchase.service.retreatGoods.RetreatGoodsService;
import com.dofast.module.purchase.service.retreatOrder.RetreatOrderOracleService;
import com.dofast.module.purchase.service.retreatOrder.RetreatOrderService;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.wms.controller.admin.storagearea.vo.StorageAreaExportReqVO;
import com.dofast.module.wms.dal.dataobject.rtvendor.RtVendorDO;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.service.rtvendor.RtVendorService;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// 仓退单定时器
@Component
public class retreatOrderJob implements JobHandler {

    @Resource
    private RetreatOrderOracleService retreatOrderOracleService;

    @Resource
    private RetreatOrderService retreatOrderService;

    @Resource
    private RetreatGoodsService retreatGoodsService;

    @Resource
    private MdVendorApi mdVendorApi;

    @Resource
    private MdItemApi mdItemApi;

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public String execute(String param) throws Exception {
        // 追加仓退单单身
        List<Map<String, Object>> retreatOrderList = retreatOrderOracleService.initRetreatOrder();

        List<Map<String, Object>> retreatGoodsList = retreatOrderOracleService.initRetreatGoods();

        if (retreatOrderList.isEmpty()) {
            return "未获取到已审核的仓退单信息";
        }

        // 仓退单信息
        List<RetreatOrderDO> addOrder = new ArrayList<>();
        List<RetreatOrderDO> editOrder = new ArrayList<>();
        // 仓退单详情
        List<RetreatGoodsDO> addGoods = new ArrayList<>();
        List<RetreatGoodsDO> editGoods = new ArrayList<>();

        for (int i = 0; i < retreatOrderList.size(); i++) {
            Map<String, Object> retreatOrder = retreatOrderList.get(i);
            // 校验当前的入库单号是否存在
            String retreatCode = Optional.ofNullable((String) retreatOrder.get("RETREAT_CODE")).orElse(""); // 仓退单号
            String supplierCode = Optional.ofNullable((String) retreatOrder.get("VENDOR_CODE")).orElse(""); // 供应商编码
            String user = Optional.ofNullable((String) retreatOrder.get("RETREAT_USER")).orElse(""); // 仓退单申请人
            AdminUserRespDTO userDTO = adminUserApi.getUser(user);

            // 根据当前的供应商编码获取详情
            MdVendorDTO vendor =  mdVendorApi.getVendorByVendorCode(supplierCode);
            RetreatOrderDO retreatOrderDO = retreatOrderService.getOrder(retreatCode);
            if (retreatOrderDO == null) {
                // 追加仓退单信息
                RetreatOrderDO order = new RetreatOrderDO();
                order.setRetreatCode(retreatCode);
                order.setRetreatName(retreatCode);
                order.setRetreatType("N"); // N: 未传递ERP接口
                order.setVendorCode(supplierCode);
                if(vendor!= null){
                    order.setVendorName(vendor.getVendorName());
                }
                if(userDTO!=null) {
                    order.setRetreatUser(userDTO.getUsername());
                    order.setRetreatNick(userDTO.getNickname());
                }
                addOrder.add(order);
                // 追加仓退单详情信息
            }else{
                retreatOrderDO.setVendorCode(supplierCode);
                if(vendor!= null){
                    retreatOrderDO.setVendorName(vendor.getVendorName());
                }
                if(userDTO!=null){
                    retreatOrderDO.setRetreatUser(userDTO.getUsername());
                    retreatOrderDO.setRetreatNick(userDTO.getNickname());
                }
                editOrder.add(retreatOrderDO);
            }
        }

        if(!addOrder.isEmpty()){
            retreatOrderService.insertBatch(addOrder);
        }
        if(!editOrder.isEmpty()){
            retreatOrderService.updateBatch(editOrder);
        }

        if(retreatGoodsList.isEmpty()){
            return "未获取到仓退单详情信息";
        }

        for (int i = 0; i < retreatGoodsList.size(); i++) {
            Map<String, Object> retreatGoods = retreatGoodsList.get(i);
            String retreatCode = Optional.ofNullable((String) retreatGoods.get("RETREAT_CODE")).orElse(""); // 仓退单号
            String goodsNumber = Optional.ofNullable((String) retreatGoods.get("GOODS_NUMBER")).orElse(""); // 商品编码
            String goodsName = "";
            String goodsSpec = "";
            MdItemDTO item = mdItemApi.getMdItemByCode(goodsNumber);
            if(item!=null){
                goodsName = item.getItemName();
                goodsSpec = item.getSpecification();
            }
            String unitOfMeasure = Optional.ofNullable((String) retreatGoods.get("UNIT_OF_MEASURE")).orElse(""); // 商品单位
            BigDecimal receiveNum = retreatGoods.get("RECEIVE_NUM").equals(null)? new BigDecimal(0) : (BigDecimal) retreatGoods.get("RECEIVE_NUM");
            BigDecimal consequence = retreatGoods.get("CONSEQUENCE").equals(null)? new BigDecimal(0) : (BigDecimal) retreatGoods.get("CONSEQUENCE");// 商品项次

            // 追加采购, 收货信息
            String poNo = Optional.ofNullable((String) retreatGoods.get("PO_NO")).orElse(""); // 采购单号
            BigDecimal purchaseBatch = Optional.ofNullable((BigDecimal) retreatGoods.get("PURCHASE_BATCH")).orElse(null); // 采购项次
            BigDecimal purchaseConsequence = Optional.ofNullable((BigDecimal) retreatGoods.get("PURCHASE_CONSEQUENCE")).orElse(null); // 采购项序
            BigDecimal purchaseBatchConsequence = Optional.ofNullable((BigDecimal) retreatGoods.get("PURCHASE_BATCH_CONSEQUENCE")).orElse(null); // 采购分批序

            String warehousingCode = Optional.ofNullable((String) retreatGoods.get("WAREHOUSING_CODE")).orElse(""); // 收货单号
            BigDecimal warehousingSequence = Optional.ofNullable((BigDecimal) retreatGoods.get("WAREHOUSING_SEQUENCE")).orElse(null); // 收货项序
            String reasonCode = Optional.ofNullable((String) retreatGoods.get("REASON_CODE")).orElse(""); // 仓退理由码
            String batchCode = Optional.ofNullable((String) retreatGoods.get("BATCH_CODE")).orElse(""); // 批次号



            Long warehouseId = null;
            String warehouseCode = "";
            String warehouseName = "";
            String locationCode = Optional.ofNullable((String) retreatGoods.get("LOCATION_CODE")).orElse(""); // 库区编码
            String locationName = "";
            Long locationId = 0L;
            StorageLocationDO storageLocation = storageLocationService.getStorageLocation(locationCode);
            if(storageLocation!=null){
                locationName = storageLocation.getLocationName();
                locationId = storageLocation.getId();
                WarehouseDO warehouse = warehouseService.getWarehouse(storageLocation.getWarehouseId());
                if(warehouse!=null){
                    warehouseId = warehouse.getId();
                    warehouseCode = warehouse.getWarehouseCode();
                    warehouseName = warehouse.getWarehouseName();
                }
            }
            String areaCode = Optional.ofNullable((String) retreatGoods.get("AREA_CODE")).orElse(""); // 库位编码
            String areaName = "";
            Long areaId = 0L;;
            StorageAreaExportReqVO epr = new StorageAreaExportReqVO();
            epr.setAreaCode(areaCode);
            epr.setLocationId(locationId);
            List<StorageAreaDO> storageAreaList = storageAreaService.getStorageAreaList(epr);
            if(!storageAreaList.isEmpty()){
                areaName = storageAreaList.get(0).getAreaName();
                areaId = storageAreaList.get(0).getId();
            }

            // 校验当前单身是否存在 仓退单-料号-项次视为唯一行
            RetreatOrderDO order =  retreatOrderService.getOrder(retreatCode);
            if(order==null){
                continue;
            }

            RetreatGoodsExportReqVO exportReqVO = new RetreatGoodsExportReqVO();
            exportReqVO.setRetreatId(Math.toIntExact(order.getId()));
            exportReqVO.setGoodsNumber(goodsNumber);
            exportReqVO.setConsequence(String.valueOf(consequence));
            List<RetreatGoodsDO> retreatGoodsDOList =  retreatGoodsService.getGoodsList(exportReqVO);
            if(retreatGoodsDOList.isEmpty()){
               // 开始追加数据
               RetreatGoodsDO retreatGoodsDO = new RetreatGoodsDO();
               retreatGoodsDO.setRetreatId(Math.toIntExact(order.getId()));
               retreatGoodsDO.setGoodsNumber(goodsNumber);
               retreatGoodsDO.setGoodsName(goodsName);
               retreatGoodsDO.setGoodsSpecs(goodsSpec);
               retreatGoodsDO.setUnitOfMeasure(unitOfMeasure);
               retreatGoodsDO.setReceiveNum(receiveNum);
               retreatGoodsDO.setConsequence(String.valueOf(consequence));

               retreatGoodsDO.setPoNo(poNo);
               retreatGoodsDO.setPurchaseBatch(purchaseBatch.intValue());
               retreatGoodsDO.setPurchaseConsequence(purchaseConsequence.intValue());
               retreatGoodsDO.setPurchaseBatchConsequence(purchaseBatchConsequence.intValue());
               retreatGoodsDO.setErpReceiveCode(warehousingCode);
               retreatGoodsDO.setReceiveSeq(warehousingSequence.intValue());
               retreatGoodsDO.setReasonCode(reasonCode);

               retreatGoodsDO.setBatchCode(batchCode);
               retreatGoodsDO.setLocationCode(locationCode);
               retreatGoodsDO.setLocationName(locationName);
               retreatGoodsDO.setLocationId(locationId);
               retreatGoodsDO.setAreaCode(areaCode);
               retreatGoodsDO.setAreaName(areaName);
               retreatGoodsDO.setAreaId(areaId);
               addGoods.add(retreatGoodsDO);
            }else{
                // 开始编辑数据
                RetreatGoodsDO retreatGoodsDO = retreatGoodsDOList.get(0);
                retreatGoodsDO.setGoodsName(goodsName);
                retreatGoodsDO.setGoodsSpecs(goodsSpec);
                retreatGoodsDO.setUnitOfMeasure(unitOfMeasure);
                retreatGoodsDO.setReceiveNum(receiveNum);
                retreatGoodsDO.setPoNo(poNo);
                retreatGoodsDO.setPurchaseBatch(purchaseBatch.intValue());
                retreatGoodsDO.setPurchaseConsequence(purchaseConsequence.intValue());
                retreatGoodsDO.setPurchaseBatchConsequence(purchaseBatchConsequence.intValue());
                retreatGoodsDO.setErpReceiveCode(warehousingCode);
                retreatGoodsDO.setReceiveSeq(warehousingSequence.intValue());
                retreatGoodsDO.setReasonCode(reasonCode);
                retreatGoodsDO.setBatchCode(batchCode);
                retreatGoodsDO.setLocationCode(locationCode);
                retreatGoodsDO.setLocationName(locationName);
                retreatGoodsDO.setLocationId(locationId);
                retreatGoodsDO.setAreaCode(areaCode);
                retreatGoodsDO.setAreaName(areaName);
                retreatGoodsDO.setAreaId(areaId);
                editGoods.add(retreatGoodsDO);
            }
        }

        if(!addGoods.isEmpty()){
            retreatGoodsService.insertBatch(addGoods);
        }
        if(!editGoods.isEmpty()){
            retreatGoodsService.updateBatch(editGoods);
        }
        return "success";
    }
}
