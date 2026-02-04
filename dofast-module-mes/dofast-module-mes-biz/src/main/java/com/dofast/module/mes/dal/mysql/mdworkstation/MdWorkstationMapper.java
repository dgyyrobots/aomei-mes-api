package com.dofast.module.mes.dal.mysql.mdworkstation;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdworkstation.MdWorkstationDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdworkstation.vo.*;

/**
 * 工作站 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdWorkstationMapper extends BaseMapperX<MdWorkstationDO> {
    default MdWorkstationDO checkWorkStationCodeUnique(MdWorkstationBaseVO mdWorkstation){
        return selectOne(new LambdaQueryWrapperX<MdWorkstationDO>().eq(MdWorkstationDO::getWorkshopCode,mdWorkstation.getWorkstationCode()));
    }
    default MdWorkstationDO checkWorkStationNameUnique(MdWorkstationBaseVO mdWorkstation){
        return selectOne(new LambdaQueryWrapperX<MdWorkstationDO>().eq(MdWorkstationDO::getWorkstationName,mdWorkstation.getWorkstationName()));
    }
    default PageResult<MdWorkstationDO> selectPage(MdWorkstationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdWorkstationDO>()
                .likeIfPresent(MdWorkstationDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(MdWorkstationDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(MdWorkstationDO::getWorkstationAddress, reqVO.getWorkstationAddress())
                .eqIfPresent(MdWorkstationDO::getWorkshopId, reqVO.getWorkshopId())
                .eqIfPresent(MdWorkstationDO::getWorkshopCode, reqVO.getWorkshopCode())
                .likeIfPresent(MdWorkstationDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(MdWorkstationDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(MdWorkstationDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(MdWorkstationDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(MdWorkstationDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(MdWorkstationDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(MdWorkstationDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(MdWorkstationDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(MdWorkstationDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(MdWorkstationDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(MdWorkstationDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(MdWorkstationDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(MdWorkstationDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(MdWorkstationDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdWorkstationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkstationDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkstationDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkstationDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkstationDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkstationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkstationDO::getId));
    }

    default List<MdWorkstationDO> selectList(MdWorkstationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdWorkstationDO>()
                .inIfPresent(MdWorkstationDO::getId, reqVO.getIds())
                .eqIfPresent(MdWorkstationDO::getWorkstationCode, reqVO.getWorkstationCode())
                .eqIfPresent(MdWorkstationDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(MdWorkstationDO::getWorkstationAddress, reqVO.getWorkstationAddress())
                .eqIfPresent(MdWorkstationDO::getWorkshopId, reqVO.getWorkshopId())
                .eqIfPresent(MdWorkstationDO::getWorkshopCode, reqVO.getWorkshopCode())
                .eqIfPresent(MdWorkstationDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(MdWorkstationDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(MdWorkstationDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(MdWorkstationDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(MdWorkstationDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(MdWorkstationDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(MdWorkstationDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(MdWorkstationDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(MdWorkstationDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(MdWorkstationDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(MdWorkstationDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(MdWorkstationDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(MdWorkstationDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(MdWorkstationDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdWorkstationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkstationDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkstationDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkstationDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkstationDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkstationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkstationDO::getId));
    }
    default List<MdWorkstationDO> select(Collection<Long> workstationIds){
        return selectBatchIds(workstationIds);
    }

}
