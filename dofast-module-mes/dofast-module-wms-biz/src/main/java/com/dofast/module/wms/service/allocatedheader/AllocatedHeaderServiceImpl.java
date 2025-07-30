package com.dofast.module.wms.service.allocatedheader;

import com.dofast.module.mes.service.mditem.MdItemService;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedTxBean;
import com.dofast.module.wms.dal.dataobject.allocatedline.AllocatedLineDO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueTxBean;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.dal.mysql.allocatedline.AllocatedLineMapper;
import com.dofast.module.wms.service.allocatedline.AllocatedLineService;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import org.springframework.stereotype.Service;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedTxBean;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;

import com.dofast.module.wms.controller.admin.allocatedheader.vo.*;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedHeaderDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.allocatedheader.AllocatedHeaderConvert;
import com.dofast.module.wms.dal.mysql.allocatedheader.AllocatedHeaderMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 调拨单头 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class AllocatedHeaderServiceImpl implements AllocatedHeaderService {

    @Resource
    private AllocatedHeaderMapper allocatedHeaderMapper;

    @Resource
    private AllocatedLineMapper allocateLineMapper;

    @Resource
    private MdItemService mdItemService;

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService locationService;

    @Resource
    private StorageAreaService areaService;


    @Override
    public Long createAllocatedHeader(AllocatedHeaderCreateReqVO createReqVO) {
        // 插入
        WarehouseDO queryWarehouse = warehouseService.getWarehouse(createReqVO.getWarehouseId());
        StorageLocationDO queryLocationDO = locationService.getStorageLocation(createReqVO.getLocationId());
        StorageAreaDO queryAreaDO = areaService.getStorageArea(createReqVO.getAreaId());
        createReqVO.setWarehouseCode(Optional.ofNullable(queryWarehouse.getWarehouseCode()).orElse(""));
        createReqVO.setWarehouseName(Optional.ofNullable(queryWarehouse.getWarehouseName()).orElse(""));
        createReqVO.setLocationCode(Optional.ofNullable(queryLocationDO.getLocationCode()).orElse(""));
        createReqVO.setLocationName(Optional.ofNullable(queryLocationDO.getLocationName()).orElse(""));
        createReqVO.setAreaCode(Optional.ofNullable(queryAreaDO.getAreaCode()).orElse(""));
        createReqVO.setAreaName(Optional.ofNullable(queryAreaDO.getAreaName()).orElse(""));
        AllocatedHeaderDO allocatedHeader = AllocatedHeaderConvert.INSTANCE.convert(createReqVO);
        allocatedHeaderMapper.insert(allocatedHeader);
        AllocatedHeaderDO allocated = allocatedHeaderMapper.selectOne(AllocatedHeaderDO::getAllocatedCode, allocatedHeader.getAllocatedCode());
        List<Map<String, Object>> bomList = createReqVO.getBomList();
        if (bomList != null && bomList.size() > 0) {
            // 追加单身表信息
            List<AllocatedLineDO> bomListDO = new ArrayList<>();
            for (Map<String, Object> map : bomList) {
                AllocatedLineDO bom = new AllocatedLineDO();
                bom.setAllocatedId(allocated.getId());
                // 仓库信息
                Integer warehouseId = (Integer) Optional.ofNullable(map.get("warehouseId")).orElse(0);
                bom.setWarehouseId((Long.valueOf(warehouseId)));
                if (warehouseId != 0) {
                    // 获取仓库信息
                    WarehouseDO warehouseDO = warehouseService.getWarehouse(warehouseId.longValue());
                    bom.setWarehouseName(warehouseDO.getWarehouseName());
                    bom.setWarehouseCode(warehouseDO.getWarehouseCode());
                }

                Integer locationId = (Integer) Optional.ofNullable(map.get("locationId")).orElse(0);
                bom.setLocationId(Long.valueOf(locationId));
                if (locationId != 0) {
                    // 获取库位信息
                    StorageLocationDO locationDO = locationService.getStorageLocation(locationId.longValue());
                    bom.setLocationName(locationDO.getLocationName());
                    bom.setLocationCode(locationDO.getLocationCode());
                }
                Integer areaId = (Integer) Optional.ofNullable(map.get("areaId")).orElse(0);
                bom.setAreaId(Long.valueOf(areaId));
                if (areaId != 0) {
                    // 获取库区信息
                    StorageAreaDO areaDO = areaService.getStorageArea(areaId.longValue());
                    bom.setAreaName(areaDO.getAreaName());
                    bom.setAreaCode(areaDO.getAreaCode());
                }
                // 库存信息
                Long itemId = Optional.ofNullable(mdItemService.getMdItem((String) map.get("itemCode")).getId()).orElse(0L);
                bom.setItemId(itemId);
                bom.setItemName((String) map.get("itemName"));
                bom.setItemCode((String) map.get("itemCode"));
                // 基于当前物料料号获取id
                bom.setSpecification((String) map.get("specification"));
                Object quantityAllocatedObj = map.get("quantityAllocated");
                if (quantityAllocatedObj instanceof Integer) {
                    bom.setQuantityAllocated(((Integer) quantityAllocatedObj).doubleValue());
                } else if (quantityAllocatedObj instanceof Double) {
                    bom.setQuantityAllocated((Double) quantityAllocatedObj);
                } else {
                    // 处理其他类型或默认值
                    bom.setQuantityAllocated(0.0); // 或者抛出异常
                }
                bom.setBatchCode((String) map.get("batchCode")); // 批次号
                bom.setUnitOfMeasure((String) map.get("unitOfMeasure")); // 计量单位
               /* Double quantityAllocated = (Double) Optional.ofNullable(map.get("quantityAllocated")).orElse(0.0);
                bom.setQuantityAllocated(quantityAllocated);*/
                bomListDO.add(bom);
            }
            allocateLineMapper.insertBatch(bomListDO);
        }

        // 返回
        return allocatedHeader.getId();
    }

    @Override
    public void updateAllocatedHeader(AllocatedHeaderUpdateReqVO updateReqVO) {
        // 校验存在
        validateAllocatedHeaderExists(updateReqVO.getId());
        // 更新
        AllocatedHeaderDO updateObj = AllocatedHeaderConvert.INSTANCE.convert(updateReqVO);
        allocatedHeaderMapper.updateById(updateObj);
    }

    @Override
    public void deleteAllocatedHeader(Long id) {
        // 校验存在
        validateAllocatedHeaderExists(id);
        // 删除
        allocatedHeaderMapper.deleteById(id);
    }

    private void validateAllocatedHeaderExists(Long id) {
        if (allocatedHeaderMapper.selectById(id) == null) {
            throw exception(ALLOCATED_HEADER_NOT_EXISTS);
        }
    }

    @Override
    public AllocatedHeaderDO getAllocatedHeader(Long id) {
        return allocatedHeaderMapper.selectById(id);
    }

    @Override
    public List<AllocatedHeaderDO> getAllocatedHeaderList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return allocatedHeaderMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<AllocatedHeaderDO> getAllocatedHeaderPage(AllocatedHeaderPageReqVO pageReqVO) {
        return allocatedHeaderMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AllocatedHeaderDO> getAllocatedHeaderList(AllocatedHeaderExportReqVO exportReqVO) {
        return allocatedHeaderMapper.selectList(exportReqVO);
    }

    /**
     * 调拨单绑定工单后, 获取工单的BOM信息并校验库存是否足够
     *
     * @param workOrderNo
     * @return
     */
    @Override
    public List<Map<String, Object>> initWorkOrderBom(String workOrderNo) {
        return allocatedHeaderMapper.initWorkOrderBom(workOrderNo);
    }


    /**
     * 获取调拨单单身信息
     *
     * @param allocatedId
     * @return
     */
    public List<AllocatedTxBean> getTxBeans(Long allocatedId) {
        return allocatedHeaderMapper.getTxBeans(allocatedId);
    }

    public List<AllocatedHeaderDO> getAllocatedHeaderListByBatchCodeList(List<String> batchCodeList){
        return allocatedHeaderMapper.getAllocatedHeaderListByBatchCodeList(batchCodeList);
    }



}
