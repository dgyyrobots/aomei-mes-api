package com.dofast.module.wms.dal.mysql.issueheader;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.issueheader.vo.*;

/**
 * 生产领料单头 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface IssueHeaderMapper extends BaseMapperX<IssueHeaderDO> {
    public List<IssueTxBean> getTxBeans(Long issueId);
    default IssueHeaderDO checkIssueCodeUnique(IssueHeaderBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<IssueHeaderDO>().eq(IssueHeaderDO::getIssueCode,baseVO.getIssueCode()));
    }
    default PageResult<IssueHeaderDO> selectPage(IssueHeaderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IssueHeaderDO>()
                .eqIfPresent(IssueHeaderDO::getIssueCode, reqVO.getIssueCode())
                .likeIfPresent(IssueHeaderDO::getIssueName, reqVO.getIssueName())
                .eqIfPresent(IssueHeaderDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(IssueHeaderDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(IssueHeaderDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(IssueHeaderDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(IssueHeaderDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(IssueHeaderDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(IssueHeaderDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(IssueHeaderDO::getClientId, reqVO.getClientId())
                .eqIfPresent(IssueHeaderDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(IssueHeaderDO::getClientName, reqVO.getClientName())
                .eqIfPresent(IssueHeaderDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(IssueHeaderDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(IssueHeaderDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(IssueHeaderDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(IssueHeaderDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(IssueHeaderDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(IssueHeaderDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(IssueHeaderDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(IssueHeaderDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(IssueHeaderDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(IssueHeaderDO::getIssueDate, reqVO.getIssueDate())
                .eqIfPresent(IssueHeaderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IssueHeaderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IssueHeaderDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IssueHeaderDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IssueHeaderDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IssueHeaderDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(IssueHeaderDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(IssueHeaderDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(IssueHeaderDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(IssueHeaderDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(IssueHeaderDO::getProcessName, reqVO.getProcessName())
                .betweenIfPresent(IssueHeaderDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(IssueHeaderDO::getIssueCode, reqVO.getIssueCodeList())
                .orderByDesc(IssueHeaderDO::getId));
    }

    default List<IssueHeaderDO> selectList(IssueHeaderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<IssueHeaderDO>()
                .eqIfPresent(IssueHeaderDO::getIssueCode, reqVO.getIssueCode())
                .likeIfPresent(IssueHeaderDO::getIssueName, reqVO.getIssueName())
                .eqIfPresent(IssueHeaderDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(IssueHeaderDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(IssueHeaderDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(IssueHeaderDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(IssueHeaderDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(IssueHeaderDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(IssueHeaderDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(IssueHeaderDO::getClientId, reqVO.getClientId())
                .eqIfPresent(IssueHeaderDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(IssueHeaderDO::getClientName, reqVO.getClientName())
                .eqIfPresent(IssueHeaderDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(IssueHeaderDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(IssueHeaderDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(IssueHeaderDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(IssueHeaderDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(IssueHeaderDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(IssueHeaderDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(IssueHeaderDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(IssueHeaderDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(IssueHeaderDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(IssueHeaderDO::getIssueDate, reqVO.getIssueDate())
                .eqIfPresent(IssueHeaderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IssueHeaderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IssueHeaderDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IssueHeaderDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IssueHeaderDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IssueHeaderDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(IssueHeaderDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(IssueHeaderDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(IssueHeaderDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(IssueHeaderDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(IssueHeaderDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(IssueHeaderDO::getProcessName, reqVO.getProcessName())
                .orderByDesc(IssueHeaderDO::getId));
    }

    List<Map<String, Object>> initBomByWorkOrder(String workOrderNo);

}
