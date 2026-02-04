package com.dofast.module.pro.dal.mysql.feedbackwarehousinglog;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.feedbackwarehousinglog.FeedbackWarehousingLogDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo.*;

/**
 * 报工入库日志 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackWarehousingLogMapper extends BaseMapperX<FeedbackWarehousingLogDO> {

    default PageResult<FeedbackWarehousingLogDO> selectPage(FeedbackWarehousingLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackWarehousingLogDO>()
                .eqIfPresent(FeedbackWarehousingLogDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(FeedbackWarehousingLogDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(FeedbackWarehousingLogDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(FeedbackWarehousingLogDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(FeedbackWarehousingLogDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(FeedbackWarehousingLogDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(FeedbackWarehousingLogDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(FeedbackWarehousingLogDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getItemId, reqVO.getItemId())
                .eqIfPresent(FeedbackWarehousingLogDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(FeedbackWarehousingLogDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FeedbackWarehousingLogDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(FeedbackWarehousingLogDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(FeedbackWarehousingLogDO::getQuantityFeedback, reqVO.getQuantityFeedback())
                .eqIfPresent(FeedbackWarehousingLogDO::getQuantityQualified, reqVO.getQuantityQualified())
                .eqIfPresent(FeedbackWarehousingLogDO::getQuantityUnquanlified, reqVO.getQuantityUnquanlified())
                .likeIfPresent(FeedbackWarehousingLogDO::getUserName, reqVO.getUserName())
                .likeIfPresent(FeedbackWarehousingLogDO::getNickName, reqVO.getNickName())
                .eqIfPresent(FeedbackWarehousingLogDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getErpBatchCode, reqVO.getErpBatchCode())
                .likeIfPresent(FeedbackWarehousingLogDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(FeedbackWarehousingLogDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(FeedbackWarehousingLogDO::getErpFeedback, reqVO.getErpFeedback())
                .eqIfPresent(FeedbackWarehousingLogDO::getErpFeedbackStatus, reqVO.getErpFeedbackStatus())
                .eqIfPresent(FeedbackWarehousingLogDO::getErpWarehousingStatus, reqVO.getErpWarehousingStatus())
                .eqIfPresent(FeedbackWarehousingLogDO::getConversionQuantity, reqVO.getConversionQuantity())
                .eqIfPresent(FeedbackWarehousingLogDO::getConversionUnit, reqVO.getConversionUnit())
                .eqIfPresent(FeedbackWarehousingLogDO::getConversionQuantityUnquanlified, reqVO.getConversionQuantityUnquanlified())
                .eqIfPresent(FeedbackWarehousingLogDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FeedbackWarehousingLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FeedbackWarehousingLogDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(FeedbackWarehousingLogDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(FeedbackWarehousingLogDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(FeedbackWarehousingLogDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(FeedbackWarehousingLogDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackWarehousingLogDO::getCreator, reqVO.getCreator())
                .orderByDesc(FeedbackWarehousingLogDO::getId));
    }

    default List<FeedbackWarehousingLogDO> selectList(FeedbackWarehousingLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FeedbackWarehousingLogDO>()
                .eqIfPresent(FeedbackWarehousingLogDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkstationCode, reqVO.getWorkstationCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(FeedbackWarehousingLogDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(FeedbackWarehousingLogDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(FeedbackWarehousingLogDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(FeedbackWarehousingLogDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getItemId, reqVO.getItemId())
                .eqIfPresent(FeedbackWarehousingLogDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FeedbackWarehousingLogDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(FeedbackWarehousingLogDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(FeedbackWarehousingLogDO::getQuantityFeedback, reqVO.getQuantityFeedback())
                .eqIfPresent(FeedbackWarehousingLogDO::getQuantityQualified, reqVO.getQuantityQualified())
                .eqIfPresent(FeedbackWarehousingLogDO::getQuantityUnquanlified, reqVO.getQuantityUnquanlified())
                .eqIfPresent(FeedbackWarehousingLogDO::getUserName, reqVO.getUserName())
                .eqIfPresent(FeedbackWarehousingLogDO::getNickName, reqVO.getNickName())
                .eqIfPresent(FeedbackWarehousingLogDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(FeedbackWarehousingLogDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(FeedbackWarehousingLogDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(FeedbackWarehousingLogDO::getErpFeedback, reqVO.getErpFeedback())
                .eqIfPresent(FeedbackWarehousingLogDO::getErpFeedbackStatus, reqVO.getErpFeedbackStatus())
                .eqIfPresent(FeedbackWarehousingLogDO::getErpWarehousingStatus, reqVO.getErpWarehousingStatus())
                .eqIfPresent(FeedbackWarehousingLogDO::getConversionQuantity, reqVO.getConversionQuantity())
                .eqIfPresent(FeedbackWarehousingLogDO::getConversionUnit, reqVO.getConversionUnit())
                .eqIfPresent(FeedbackWarehousingLogDO::getConversionQuantityUnquanlified, reqVO.getConversionQuantityUnquanlified())
                .eqIfPresent(FeedbackWarehousingLogDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FeedbackWarehousingLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FeedbackWarehousingLogDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(FeedbackWarehousingLogDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(FeedbackWarehousingLogDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(FeedbackWarehousingLogDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(FeedbackWarehousingLogDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackWarehousingLogDO::getCreator, reqVO.getCreator())
                .orderByDesc(FeedbackWarehousingLogDO::getId));
    }

}
