package com.dofast.module.wms.dal.mysql.storagelocation;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.storagelocation.vo.*;

/**
 * 库区 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface StorageLocationMapper extends BaseMapperX<StorageLocationDO> {
    default StorageLocationDO selectWmStorageLocationByLocationCode(String locationCode) {
        return selectOne(new LambdaQueryWrapperX<StorageLocationDO>().eq(StorageLocationDO::getLocationCode,locationCode));
    }

    default StorageLocationDO selectWmStorageLocationByLocationCodeAndProcessCode(String locationCode, String processCode) {
        return selectOne(StorageLocationDO::getLocationCode,locationCode, StorageLocationDO::getProcessCode , processCode);
    }

    /**
     * 根据仓库删除对应的库区
     * @param warehouseId
     * @return
     */
     int deleteByWarehouseId(Long warehouseId);
    default StorageLocationDO checkLocationCodeUnique(StorageLocationBaseVO wmStorageLocation){
        return selectOne(new LambdaQueryWrapperX<StorageLocationDO>().eq(StorageLocationDO::getLocationCode,wmStorageLocation.getLocationCode()));
    }
    default StorageLocationDO checkLocationNameUnique(StorageLocationBaseVO wmStorageLocation){
        return selectOne(new LambdaQueryWrapperX<StorageLocationDO>().eq(StorageLocationDO::getLocationName,wmStorageLocation.getLocationName()));
    }
    default PageResult<StorageLocationDO> selectPage(StorageLocationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StorageLocationDO>()
                .eqIfPresent(StorageLocationDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(StorageLocationDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(StorageLocationDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(StorageLocationDO::getArea, reqVO.getArea())
                .eqIfPresent(StorageLocationDO::getAreaFlag, reqVO.getAreaFlag())
                .eqIfPresent(StorageLocationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(StorageLocationDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(StorageLocationDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(StorageLocationDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(StorageLocationDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(StorageLocationDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(StorageLocationDO::getProcessCode, reqVO.getProcessCode())
                .orderByDesc(StorageLocationDO::getId));
    }

    default List<StorageLocationDO> selectList(StorageLocationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StorageLocationDO>()
                .eqIfPresent(StorageLocationDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(StorageLocationDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(StorageLocationDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(StorageLocationDO::getArea, reqVO.getArea())
                .eqIfPresent(StorageLocationDO::getAreaFlag, reqVO.getAreaFlag())
                .eqIfPresent(StorageLocationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(StorageLocationDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(StorageLocationDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(StorageLocationDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(StorageLocationDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(StorageLocationDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(StorageLocationDO::getProcessCode, reqVO.getProcessCode())
                .orderByDesc(StorageLocationDO::getId));
    }

}
