package com.dofast.module.wms.dal.mysql.allocatedheader;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedHeaderDO;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedTxBean;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.allocatedheader.vo.*;

/**
 * 调拨单头 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface AllocatedHeaderMapper extends BaseMapperX<AllocatedHeaderDO> {

    default PageResult<AllocatedHeaderDO> selectPage(AllocatedHeaderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AllocatedHeaderDO>()
                .eqIfPresent(AllocatedHeaderDO::getAllocatedCode, reqVO.getAllocatedCode())
                .likeIfPresent(AllocatedHeaderDO::getAllocatedName, reqVO.getAllocatedName())
                .eqIfPresent(AllocatedHeaderDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(AllocatedHeaderDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(AllocatedHeaderDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(AllocatedHeaderDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(AllocatedHeaderDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(AllocatedHeaderDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(AllocatedHeaderDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(AllocatedHeaderDO::getClientId, reqVO.getClientId())
                .eqIfPresent(AllocatedHeaderDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(AllocatedHeaderDO::getClientName, reqVO.getClientName())
                .eqIfPresent(AllocatedHeaderDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(AllocatedHeaderDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(AllocatedHeaderDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(AllocatedHeaderDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(AllocatedHeaderDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(AllocatedHeaderDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(AllocatedHeaderDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(AllocatedHeaderDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(AllocatedHeaderDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(AllocatedHeaderDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(AllocatedHeaderDO::getAllocatedDate, reqVO.getAllocatedDate())
                .eqIfPresent(AllocatedHeaderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(AllocatedHeaderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(AllocatedHeaderDO::getTaskName, reqVO.getTaskName())
                .betweenIfPresent(AllocatedHeaderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AllocatedHeaderDO::getId));
    }

    default List<AllocatedHeaderDO> selectList(AllocatedHeaderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<AllocatedHeaderDO>()
                .eqIfPresent(AllocatedHeaderDO::getAllocatedCode, reqVO.getAllocatedCode())
                .likeIfPresent(AllocatedHeaderDO::getAllocatedName, reqVO.getAllocatedName())
                .eqIfPresent(AllocatedHeaderDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(AllocatedHeaderDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(AllocatedHeaderDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(AllocatedHeaderDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(AllocatedHeaderDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(AllocatedHeaderDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(AllocatedHeaderDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(AllocatedHeaderDO::getClientId, reqVO.getClientId())
                .eqIfPresent(AllocatedHeaderDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(AllocatedHeaderDO::getClientName, reqVO.getClientName())
                .eqIfPresent(AllocatedHeaderDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(AllocatedHeaderDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(AllocatedHeaderDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(AllocatedHeaderDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(AllocatedHeaderDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(AllocatedHeaderDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(AllocatedHeaderDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(AllocatedHeaderDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(AllocatedHeaderDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(AllocatedHeaderDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(AllocatedHeaderDO::getAllocatedDate, reqVO.getAllocatedDate())
                .eqIfPresent(AllocatedHeaderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(AllocatedHeaderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(AllocatedHeaderDO::getTaskName, reqVO.getTaskName())
                .betweenIfPresent(AllocatedHeaderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AllocatedHeaderDO::getId));
    }

    public List<Map<String, Object>> initWorkOrderBom(String workOrderNo);


    public List<AllocatedTxBean> getTxBeans(Long allocatedId);

}
