package com.dofast.module.wms.dal.mysql.storagearea;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.storagearea.vo.*;

/**
 * 库位 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface StorageAreaMapper extends BaseMapperX<StorageAreaDO> {
    default   StorageAreaDO selectWmStorageAreaByAreaCode(String areaCode) {
        return selectOne(new LambdaQueryWrapperX<StorageAreaDO>().eq(StorageAreaDO::getAreaCode,areaCode));
    }
    /**
     * 根据仓库删除对应的库区
     * @param warehouseId
     * @return
     */
    public int deleteByWarehouseId(Long warehouseId);
    int deleteByLocationId(Long id);
    default PageResult<StorageAreaDO> selectPage(StorageAreaPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StorageAreaDO>()
                .eqIfPresent(StorageAreaDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(StorageAreaDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(StorageAreaDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(StorageAreaDO::getArea, reqVO.getArea())
                .eqIfPresent(StorageAreaDO::getMaxLoa, reqVO.getMaxLoa())
                .eqIfPresent(StorageAreaDO::getPositionX, reqVO.getPositionX())
                .eqIfPresent(StorageAreaDO::getPositionY, reqVO.getPositionY())
                .eqIfPresent(StorageAreaDO::getPositionZ, reqVO.getPositionZ())
                .eqIfPresent(StorageAreaDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(StorageAreaDO::getRemark, reqVO.getRemark())
                .eqIfPresent(StorageAreaDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(StorageAreaDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(StorageAreaDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(StorageAreaDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(StorageAreaDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StorageAreaDO::getId));
    }

    default List<StorageAreaDO> selectList(StorageAreaExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StorageAreaDO>()
                .eqIfPresent(StorageAreaDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(StorageAreaDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(StorageAreaDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(StorageAreaDO::getArea, reqVO.getArea())
                .eqIfPresent(StorageAreaDO::getMaxLoa, reqVO.getMaxLoa())
                .eqIfPresent(StorageAreaDO::getPositionX, reqVO.getPositionX())
                .eqIfPresent(StorageAreaDO::getPositionY, reqVO.getPositionY())
                .eqIfPresent(StorageAreaDO::getPositionZ, reqVO.getPositionZ())
                .eqIfPresent(StorageAreaDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(StorageAreaDO::getRemark, reqVO.getRemark())
                .eqIfPresent(StorageAreaDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(StorageAreaDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(StorageAreaDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(StorageAreaDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(StorageAreaDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StorageAreaDO::getId));
    }

}
