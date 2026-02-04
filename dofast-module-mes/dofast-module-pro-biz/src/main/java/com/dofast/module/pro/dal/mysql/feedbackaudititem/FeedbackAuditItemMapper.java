package com.dofast.module.pro.dal.mysql.feedbackaudititem;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.feedbackaudit.FeedbackAuditDO;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.feedbackaudititem.vo.*;

/**
 * 报工审批汇总 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackAuditItemMapper extends BaseMapperX<FeedbackAuditItemDO> {

    default PageResult<FeedbackAuditItemDO> selectPage(FeedbackAuditItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackAuditItemDO>()
                .eqIfPresent(FeedbackAuditItemDO::getAuditId, reqVO.getAuditId())
                .eqIfPresent(FeedbackAuditItemDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(FeedbackAuditItemDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(FeedbackAuditItemDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(FeedbackAuditItemDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(FeedbackAuditItemDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(FeedbackAuditItemDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(FeedbackAuditItemDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(FeedbackAuditItemDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(FeedbackAuditItemDO::getItemId, reqVO.getItemId())
                .eqIfPresent(FeedbackAuditItemDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(FeedbackAuditItemDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FeedbackAuditItemDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(FeedbackAuditItemDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(FeedbackAuditItemDO::getSumQuantityFeedback, reqVO.getSumQuantityFeedback())
                .eqIfPresent(FeedbackAuditItemDO::getSumQuantityQualified, reqVO.getSumQuantityQualified())
                .eqIfPresent(FeedbackAuditItemDO::getSumQuantityUnquanlified, reqVO.getSumQuantityUnquanlified())
                .eqIfPresent(FeedbackAuditItemDO::getSumQuantityExcess, reqVO.getSumQuantityExcess())

                .likeIfPresent(FeedbackAuditItemDO::getUserName, reqVO.getUserName())
                .likeIfPresent(FeedbackAuditItemDO::getNickName, reqVO.getNickName())
                .eqIfPresent(FeedbackAuditItemDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(FeedbackAuditItemDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackAuditItemDO::getAllPrincipal, reqVO.getAllPrincipal())
                .eqIfPresent(FeedbackAuditItemDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(FeedbackAuditItemDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(FeedbackAuditItemDO::getMachineryName, reqVO.getMachineryName())

                .orderByDesc(FeedbackAuditItemDO::getId));
    }

    default List<FeedbackAuditItemDO> selectList(FeedbackAuditItemExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FeedbackAuditItemDO>()
                .eqIfPresent(FeedbackAuditItemDO::getAuditId, reqVO.getAuditId())
                .eqIfPresent(FeedbackAuditItemDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(FeedbackAuditItemDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(FeedbackAuditItemDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(FeedbackAuditItemDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(FeedbackAuditItemDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(FeedbackAuditItemDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(FeedbackAuditItemDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(FeedbackAuditItemDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(FeedbackAuditItemDO::getItemId, reqVO.getItemId())
                .eqIfPresent(FeedbackAuditItemDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(FeedbackAuditItemDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FeedbackAuditItemDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(FeedbackAuditItemDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(FeedbackAuditItemDO::getSumQuantityFeedback, reqVO.getSumQuantityFeedback())
                .eqIfPresent(FeedbackAuditItemDO::getSumQuantityQualified, reqVO.getSumQuantityQualified())
                .eqIfPresent(FeedbackAuditItemDO::getSumQuantityUnquanlified, reqVO.getSumQuantityUnquanlified())
                .eqIfPresent(FeedbackAuditItemDO::getSumQuantityExcess, reqVO.getSumQuantityExcess())

                .eqIfPresent(FeedbackAuditItemDO::getUserName, reqVO.getUserName())
                .eqIfPresent(FeedbackAuditItemDO::getNickName, reqVO.getNickName())
                .eqIfPresent(FeedbackAuditItemDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(FeedbackAuditItemDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackAuditItemDO::getAllPrincipal, reqVO.getAllPrincipal())
                .eqIfPresent(FeedbackAuditItemDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(FeedbackAuditItemDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(FeedbackAuditItemDO::getMachineryName, reqVO.getMachineryName())
                .orderByDesc(FeedbackAuditItemDO::getId));
    }

}
