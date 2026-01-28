package com.dofast.module.pro.dal.mysql.feedbackauditdetail;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.feedbackauditdetail.FeedbackAuditDetailDO;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.feedbackauditdetail.vo.*;

/**
 * 报工审批明细 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackAuditDetailMapper extends BaseMapperX<FeedbackAuditDetailDO> {

    default PageResult<FeedbackAuditDetailDO> selectPage(FeedbackAuditDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackAuditDetailDO>()
                .eqIfPresent(FeedbackAuditDetailDO::getAuditId, reqVO.getAuditId())
                .eqIfPresent(FeedbackAuditDetailDO::getAuditItemId, reqVO.getAuditItemId())
                .eqIfPresent(FeedbackAuditDetailDO::getFeedbackId, reqVO.getFeedbackId())
                .eqIfPresent(FeedbackAuditDetailDO::getFeedbackType, reqVO.getFeedbackType())
                .eqIfPresent(FeedbackAuditDetailDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(FeedbackAuditDetailDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(FeedbackAuditDetailDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(FeedbackAuditDetailDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(FeedbackAuditDetailDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(FeedbackAuditDetailDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(FeedbackAuditDetailDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(FeedbackAuditDetailDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(FeedbackAuditDetailDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(FeedbackAuditDetailDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(FeedbackAuditDetailDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(FeedbackAuditDetailDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(FeedbackAuditDetailDO::getItemId, reqVO.getItemId())
                .eqIfPresent(FeedbackAuditDetailDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(FeedbackAuditDetailDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FeedbackAuditDetailDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(FeedbackAuditDetailDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantityFeedback, reqVO.getQuantityFeedback())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantityQualified, reqVO.getQuantityQualified())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantityUnquanlified, reqVO.getQuantityUnquanlified())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantityExcess, reqVO.getQuantityExcess())

                .likeIfPresent(FeedbackAuditDetailDO::getUserName, reqVO.getUserName())
                .likeIfPresent(FeedbackAuditDetailDO::getNickName, reqVO.getNickName())
                .eqIfPresent(FeedbackAuditDetailDO::getFeedbackChannel, reqVO.getFeedbackChannel())
                .betweenIfPresent(FeedbackAuditDetailDO::getFeedbackTime, reqVO.getFeedbackTime())
                .eqIfPresent(FeedbackAuditDetailDO::getRecordUser, reqVO.getRecordUser())
                .eqIfPresent(FeedbackAuditDetailDO::getRecordNick, reqVO.getRecordNick())
                .eqIfPresent(FeedbackAuditDetailDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(FeedbackAuditDetailDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(FeedbackAuditDetailDO::getTeamCode, reqVO.getTeamCode())
                .likeIfPresent(FeedbackAuditDetailDO::getPrincipalName, reqVO.getPrincipalName())
                .eqIfPresent(FeedbackAuditDetailDO::getPrincipalId, reqVO.getPrincipalId())
                .eqIfPresent(FeedbackAuditDetailDO::getShiftInfo, reqVO.getShiftInfo())
                .eqIfPresent(FeedbackAuditDetailDO::getOriginCode, reqVO.getOriginCode())
                .likeIfPresent(FeedbackAuditDetailDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(FeedbackAuditDetailDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(FeedbackAuditDetailDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(FeedbackAuditDetailDO::getErpFeedbackStatus, reqVO.getErpFeedbackStatus())
                .eqIfPresent(FeedbackAuditDetailDO::getErpWarehousingStatus, reqVO.getErpWarehousingStatus())
                .eqIfPresent(FeedbackAuditDetailDO::getMergeStatus, reqVO.getMergeStatus())
                .eqIfPresent(FeedbackAuditDetailDO::getConversionQuantity, reqVO.getConversionQuantity())
                .eqIfPresent(FeedbackAuditDetailDO::getConversionUnit, reqVO.getConversionUnit())
                .eqIfPresent(FeedbackAuditDetailDO::getConversionQuantityUnquanlified, reqVO.getConversionQuantityUnquanlified())
                .betweenIfPresent(FeedbackAuditDetailDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FeedbackAuditDetailDO::getId));
    }

    default List<FeedbackAuditDetailDO> selectList(FeedbackAuditDetailExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FeedbackAuditDetailDO>()
                .eqIfPresent(FeedbackAuditDetailDO::getAuditId, reqVO.getAuditId())
                .eqIfPresent(FeedbackAuditDetailDO::getAuditItemId, reqVO.getAuditItemId())
                .eqIfPresent(FeedbackAuditDetailDO::getFeedbackId, reqVO.getFeedbackId())
                .eqIfPresent(FeedbackAuditDetailDO::getFeedbackType, reqVO.getFeedbackType())
                .eqIfPresent(FeedbackAuditDetailDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(FeedbackAuditDetailDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(FeedbackAuditDetailDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(FeedbackAuditDetailDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(FeedbackAuditDetailDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(FeedbackAuditDetailDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(FeedbackAuditDetailDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(FeedbackAuditDetailDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(FeedbackAuditDetailDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(FeedbackAuditDetailDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(FeedbackAuditDetailDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(FeedbackAuditDetailDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(FeedbackAuditDetailDO::getItemId, reqVO.getItemId())
                .eqIfPresent(FeedbackAuditDetailDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(FeedbackAuditDetailDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FeedbackAuditDetailDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(FeedbackAuditDetailDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantityFeedback, reqVO.getQuantityFeedback())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantityQualified, reqVO.getQuantityQualified())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantityUnquanlified, reqVO.getQuantityUnquanlified())
                .eqIfPresent(FeedbackAuditDetailDO::getQuantityExcess, reqVO.getQuantityExcess())

                .likeIfPresent(FeedbackAuditDetailDO::getUserName, reqVO.getUserName())
                .likeIfPresent(FeedbackAuditDetailDO::getNickName, reqVO.getNickName())
                .eqIfPresent(FeedbackAuditDetailDO::getFeedbackChannel, reqVO.getFeedbackChannel())
                .betweenIfPresent(FeedbackAuditDetailDO::getFeedbackTime, reqVO.getFeedbackTime())
                .eqIfPresent(FeedbackAuditDetailDO::getRecordUser, reqVO.getRecordUser())
                .eqIfPresent(FeedbackAuditDetailDO::getRecordNick, reqVO.getRecordNick())
                .eqIfPresent(FeedbackAuditDetailDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(FeedbackAuditDetailDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(FeedbackAuditDetailDO::getTeamCode, reqVO.getTeamCode())
                .likeIfPresent(FeedbackAuditDetailDO::getPrincipalName, reqVO.getPrincipalName())
                .eqIfPresent(FeedbackAuditDetailDO::getPrincipalId, reqVO.getPrincipalId())
                .eqIfPresent(FeedbackAuditDetailDO::getShiftInfo, reqVO.getShiftInfo())
                .eqIfPresent(FeedbackAuditDetailDO::getOriginCode, reqVO.getOriginCode())
                .likeIfPresent(FeedbackAuditDetailDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(FeedbackAuditDetailDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(FeedbackAuditDetailDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(FeedbackAuditDetailDO::getErpFeedbackStatus, reqVO.getErpFeedbackStatus())
                .eqIfPresent(FeedbackAuditDetailDO::getErpWarehousingStatus, reqVO.getErpWarehousingStatus())
                .eqIfPresent(FeedbackAuditDetailDO::getMergeStatus, reqVO.getMergeStatus())
                .eqIfPresent(FeedbackAuditDetailDO::getConversionQuantity, reqVO.getConversionQuantity())
                .eqIfPresent(FeedbackAuditDetailDO::getConversionUnit, reqVO.getConversionUnit())
                .eqIfPresent(FeedbackAuditDetailDO::getConversionQuantityUnquanlified, reqVO.getConversionQuantityUnquanlified())
                .betweenIfPresent(FeedbackAuditDetailDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FeedbackAuditDetailDO::getId));
    }

}
