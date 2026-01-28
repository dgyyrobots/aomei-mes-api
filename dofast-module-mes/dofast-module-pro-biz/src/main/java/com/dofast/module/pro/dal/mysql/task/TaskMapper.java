package com.dofast.module.pro.dal.mysql.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cal.dal.dataobject.team.TeamDO;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.task.vo.*;
import org.apache.ibatis.annotations.Select;

/**
 * 生产任务 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TaskMapper extends BaseMapperX<TaskDO> {

    default PageResult<TaskDO> selectPage(TaskPageReqVO reqVO, Collection<Long> workorderIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(TaskDO::getTaskName, reqVO.getTaskName())
                .in(TaskDO::getWorkorderId, workorderIds)
                .eqIfPresent(TaskDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(TaskDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(TaskDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(TaskDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(TaskDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(TaskDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(TaskDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TaskDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(TaskDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(TaskDO::getQuantityQuanlify, reqVO.getQuantityQuanlify())
                .eqIfPresent(TaskDO::getQuantityUnquanlify, reqVO.getQuantityUnquanlify())
                .eqIfPresent(TaskDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(TaskDO::getClientId, reqVO.getClientId())
                .eqIfPresent(TaskDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(TaskDO::getClientName, reqVO.getClientName())
                .eqIfPresent(TaskDO::getClientNick, reqVO.getClientNick())
                .betweenIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(TaskDO::getDuration, reqVO.getDuration())
                .betweenIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getColorCode, reqVO.getColorCode())
                .betweenIfPresent(TaskDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TaskDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TaskDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(TaskDO::getSerial, reqVO.getSerial())
                .eqIfPresent(TaskDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(TaskDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TaskDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TaskDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(TaskDO::getMachineryCodes, reqVO.getMachineryCodes())
                .eqIfPresent(TaskDO::getFeedbackSerial, reqVO.getFeedbackSerial())
                .eqIfPresent(TaskDO::getProcessSequence, reqVO.getProcessSequence())
                .eqIfPresent(TaskDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .orderByDesc(TaskDO::getId));
    }


    default PageResult<TaskDO> selectPage(TaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(TaskDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(TaskDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(TaskDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(TaskDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(TaskDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(TaskDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(TaskDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(TaskDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TaskDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(TaskDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(TaskDO::getQuantityQuanlify, reqVO.getQuantityQuanlify())
                .eqIfPresent(TaskDO::getQuantityUnquanlify, reqVO.getQuantityUnquanlify())
                .eqIfPresent(TaskDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(TaskDO::getClientId, reqVO.getClientId())
                .eqIfPresent(TaskDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(TaskDO::getClientName, reqVO.getClientName())
                .eqIfPresent(TaskDO::getClientNick, reqVO.getClientNick())
                .betweenIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(TaskDO::getDuration, reqVO.getDuration())
                .betweenIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getColorCode, reqVO.getColorCode())
                .betweenIfPresent(TaskDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TaskDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TaskDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(TaskDO::getSerial, reqVO.getSerial())
                .eqIfPresent(TaskDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(TaskDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TaskDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TaskDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(TaskDO::getMachineryCodes, reqVO.getMachineryCodes())
                .eqIfPresent(TaskDO::getFeedbackSerial, reqVO.getFeedbackSerial())
                .eqIfPresent(TaskDO::getProcessSequence, reqVO.getProcessSequence())
                .eqIfPresent(TaskDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .orderByDesc(TaskDO::getId));
    }

    default PageResult<TaskDO> selectPageByIds(TaskPageReqVO reqVO, Collection<Long> collect) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(TaskDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(TaskDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(TaskDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(TaskDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(TaskDO::getWorkstationName, reqVO.getWorkstationName())
//                .eqIfPresent(TaskDO::getProcessId, reqVO.getProcessId())
                .in(TaskDO::getWorkstationId, collect)
                .eqIfPresent(TaskDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(TaskDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TaskDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(TaskDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(TaskDO::getQuantityQuanlify, reqVO.getQuantityQuanlify())
                .eqIfPresent(TaskDO::getQuantityUnquanlify, reqVO.getQuantityUnquanlify())
                .eqIfPresent(TaskDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(TaskDO::getClientId, reqVO.getClientId())
                .eqIfPresent(TaskDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(TaskDO::getClientName, reqVO.getClientName())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getClientNick, reqVO.getClientNick())
                .betweenIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(TaskDO::getDuration, reqVO.getDuration())
                .betweenIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getColorCode, reqVO.getColorCode())
                .betweenIfPresent(TaskDO::getRequestDate, reqVO.getRequestDate())
//                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TaskDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskDO::getAttr4, reqVO.getAttr4())
                .inIfPresent(TaskDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(TaskDO::getSerial, reqVO.getSerial())
                .eqIfPresent(TaskDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(TaskDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TaskDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TaskDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(TaskDO::getMachineryCodes, reqVO.getMachineryCodes())
                .eqIfPresent(TaskDO::getFeedbackSerial, reqVO.getFeedbackSerial())
                .eqIfPresent(TaskDO::getProcessSequence, reqVO.getProcessSequence())
                .eqIfPresent(TaskDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                /*.and(reqVO.getStatus()!=null,v->{
                    if ("1".equals(reqVO.getStatus())){
                        v.in(TaskDO::getStatus,"FINISHED");
                    }else {
                        v.notIn(TaskDO::getStatus,"FINISHED");
                    }
                })
                .and(reqVO.getIsOver()!=null && !StrUtils.isEmpty(reqVO.getIsOver()),v->{
                    if ("1".equals(reqVO.getIsOver())){
                        v.eq(TaskDO::getStatus,"FINISHED");
                    }else {
                        v.notIn(TaskDO::getStatus,"FINISHED");
                    }
                })*/
                .orderByDesc(TaskDO::getRequestDate)
                .orderByAsc(TaskDO::getSpecification));
                /*.apply("0=0 ORDER BY CASE `status` WHEN 'STARTED' THEN 0\n" +
                        "              WHEN 'PAUSE' THEN 1\n" +
                        "\t\t\t\t\t\t\twhen 'NORMAL' THEN 2\n" +
                        "\t\t\t\t\t\t\twhen 'FINISHED' THEN 3\n" +
                        "\t\t\t\t\t\t\tEND ASC,id DESC"));*/
    }

    default List<TaskDO> selectList(TaskListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(TaskDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(TaskDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(TaskDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(TaskDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(TaskDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(TaskDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(TaskDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(TaskDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TaskDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(TaskDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(TaskDO::getQuantityQuanlify, reqVO.getQuantityQuanlify())
                .eqIfPresent(TaskDO::getQuantityUnquanlify, reqVO.getQuantityUnquanlify())
                .eqIfPresent(TaskDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(TaskDO::getClientId, reqVO.getClientId())
                .eqIfPresent(TaskDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(TaskDO::getClientName, reqVO.getClientName())
                .eqIfPresent(TaskDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(TaskDO::getDuration, reqVO.getDuration())
                .eqIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getColorCode, reqVO.getColorCode())
                .eqIfPresent(TaskDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TaskDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TaskDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(TaskDO::getSerial, reqVO.getSerial())
                .eqIfPresent(TaskDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(TaskDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TaskDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TaskDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(TaskDO::getMachineryCodes, reqVO.getMachineryCodes())
                .eqIfPresent(TaskDO::getFeedbackSerial, reqVO.getFeedbackSerial())
                .eqIfPresent(TaskDO::getProcessSequence, reqVO.getProcessSequence())
                .eqIfPresent(TaskDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .orderByDesc(TaskDO::getId));
    }


    default List<TaskDO> selectList(TaskExportReqVO reqVO, Collection<Long> workstationIds) {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(TaskDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(TaskDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(TaskDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(TaskDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(TaskDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(TaskDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(TaskDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(TaskDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TaskDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(TaskDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(TaskDO::getQuantityQuanlify, reqVO.getQuantityQuanlify())
                .eqIfPresent(TaskDO::getQuantityUnquanlify, reqVO.getQuantityUnquanlify())
                .eqIfPresent(TaskDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(TaskDO::getClientId, reqVO.getClientId())
                .eqIfPresent(TaskDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(TaskDO::getClientName, reqVO.getClientName())
                .eqIfPresent(TaskDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(TaskDO::getDuration, reqVO.getDuration())
                .eqIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getColorCode, reqVO.getColorCode())
                .eqIfPresent(TaskDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TaskDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(TaskDO::getWorkstationId, workstationIds)
                .eqIfPresent(TaskDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(TaskDO::getSerial, reqVO.getSerial())
                .eqIfPresent(TaskDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(TaskDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TaskDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TaskDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(TaskDO::getMachineryCodes, reqVO.getMachineryCodes())
                .eqIfPresent(TaskDO::getFeedbackSerial, reqVO.getFeedbackSerial())
                .eqIfPresent(TaskDO::getProcessSequence, reqVO.getProcessSequence())
                .eqIfPresent(TaskDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .orderByDesc(TaskDO::getId));
    }

    default List<TaskDO> selectList(TaskExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(TaskDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(TaskDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(TaskDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(TaskDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(TaskDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(TaskDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(TaskDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(TaskDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TaskDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(TaskDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(TaskDO::getQuantityQuanlify, reqVO.getQuantityQuanlify())
                .eqIfPresent(TaskDO::getQuantityUnquanlify, reqVO.getQuantityUnquanlify())
                .eqIfPresent(TaskDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(TaskDO::getClientId, reqVO.getClientId())
                .eqIfPresent(TaskDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(TaskDO::getClientName, reqVO.getClientName())
                .eqIfPresent(TaskDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(TaskDO::getDuration, reqVO.getDuration())
                .eqIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getColorCode, reqVO.getColorCode())
                .eqIfPresent(TaskDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TaskDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TaskDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(TaskDO::getSerial, reqVO.getSerial())
                .eqIfPresent(TaskDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(TaskDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TaskDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TaskDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(TaskDO::getMachineryCodes, reqVO.getMachineryCodes())
                .eqIfPresent(TaskDO::getFeedbackSerial, reqVO.getFeedbackSerial())
                .eqIfPresent(TaskDO::getProcessSequence, reqVO.getProcessSequence())
                .eqIfPresent(TaskDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .orderByDesc(TaskDO::getId));
    }

    default TaskDO getTask(Long id, Long processId) {
        return selectOne(new LambdaQueryWrapperX<TaskDO>()
                .eqIfPresent(TaskDO::getId, id)
                .eqIfPresent(TaskDO::getProcessId, processId));
    }

    /*default PageResult<TaskDO> selectByProcessId(TaskPageReqVO pageReqVO,List<Long> collect){
        return selectPage(pageReqVO,new LambdaQueryWrapperX<TaskDO>()
                .in(TaskDO::getProcessId,collect));
    }*/

    default List<TaskDO> getTaskByTeamCode(String teamCode) {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .eqIfPresent(TaskDO::getAttr1, teamCode)
                .orderByDesc(TaskDO::getId));
    }

    default PageResult<TaskDO> getTaskByTeamCode(TaskPageReqVO reqVO, String teamCode) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(TaskDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(TaskDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(TaskDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(TaskDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(TaskDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(TaskDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(TaskDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(TaskDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TaskDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(TaskDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(TaskDO::getQuantityQuanlify, reqVO.getQuantityQuanlify())
                .eqIfPresent(TaskDO::getQuantityUnquanlify, reqVO.getQuantityUnquanlify())
                .eqIfPresent(TaskDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(TaskDO::getClientId, reqVO.getClientId())
                .eqIfPresent(TaskDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(TaskDO::getClientName, reqVO.getClientName())
                .eqIfPresent(TaskDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(TaskDO::getDuration, reqVO.getDuration())
                .eqIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getColorCode, reqVO.getColorCode())
                .eqIfPresent(TaskDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskDO::getAttr1, teamCode)
                .eqIfPresent(TaskDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TaskDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(TaskDO::getSerial, reqVO.getSerial())
                .eqIfPresent(TaskDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(TaskDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TaskDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TaskDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(TaskDO::getMachineryCodes, reqVO.getMachineryCodes())
                .eqIfPresent(TaskDO::getFeedbackSerial, reqVO.getFeedbackSerial())
                .eqIfPresent(TaskDO::getProcessSequence, reqVO.getProcessSequence())
                .eqIfPresent(TaskDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .orderByDesc(TaskDO::getId));
    }

    default PageResult<TaskDO> getTaskByTeamList(TaskPageReqVO reqVO, List<String> teamCodeList) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskDO>()
                .likeIfPresent(TaskDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(TaskDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(TaskDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(TaskDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(TaskDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(TaskDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(TaskDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(TaskDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(TaskDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TaskDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(TaskDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(TaskDO::getQuantityQuanlify, reqVO.getQuantityQuanlify())
                .eqIfPresent(TaskDO::getQuantityUnquanlify, reqVO.getQuantityUnquanlify())
                .eqIfPresent(TaskDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(TaskDO::getClientId, reqVO.getClientId())
                .eqIfPresent(TaskDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(TaskDO::getClientName, reqVO.getClientName())
                .eqIfPresent(TaskDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(TaskDO::getDuration, reqVO.getDuration())
                .eqIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(TaskDO::getColorCode, reqVO.getColorCode())
                .eqIfPresent(TaskDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getRemark, reqVO.getRemark())
                .inIfPresent(TaskDO::getAttr1, teamCodeList)
                .eqIfPresent(TaskDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskDO::getIsPrint, reqVO.getIsPrint())
                .eqIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TaskDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(TaskDO::getSerial, reqVO.getSerial())
                .eqIfPresent(TaskDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(TaskDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TaskDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TaskDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(TaskDO::getMachineryCodes, reqVO.getMachineryCodes())
                .eqIfPresent(TaskDO::getFeedbackSerial, reqVO.getFeedbackSerial())
                .eqIfPresent(TaskDO::getProcessSequence, reqVO.getProcessSequence())
                .eqIfPresent(TaskDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .orderByDesc(TaskDO::getId));
    }



    default Map<String, Integer> getCountMonthTaskLastYear() {
        QueryWrapper<TaskDO> wrapper = new QueryWrapper<>();
        wrapper.select("DATE_FORMAT(create_time, '%Y-%m') AS month", "SUM(quantity_quanlify) AS total")
                .groupBy("month")
                .ge("create_time", LocalDate.now().minusYears(1).withDayOfYear(1)) // 上一年第一天
                .lt("create_time", LocalDate.now().withDayOfYear(1)); // 今年第一天

        List<Map<String, Object>> result = selectMaps(wrapper);

        Map<String, Integer> resultMap = new HashMap<>();
        if (result == null || result.isEmpty()) return resultMap;
        for (Map<String, Object> map : result) {
            String month = (String) map.get("month");
            Double total = (Double) map.get("total");
            resultMap.put(month, total != null ? total.intValue() : 0);
        }
        return resultMap;
    }

    default Map<String, Integer> getCountMonthTaskThisYear() {
        QueryWrapper<TaskDO> wrapper = new QueryWrapper<>();
        wrapper.select("DATE_FORMAT(create_time, '%Y-%m') AS month", "SUM(quantity_quanlify) AS total")
                .groupBy("month")
                // 时间范围：今年第一天 ~ 明年第一天（确保覆盖全年）
                .ge("create_time", LocalDate.now().withDayOfYear(1))          // 今年第一天
                .lt("create_time", LocalDate.now().plusYears(1).withDayOfYear(1)); // 明年第一天

        List<Map<String, Object>> result = selectMaps(wrapper);

        Map<String, Integer> resultMap = new HashMap<>();
        if (result == null || result.isEmpty()) {
            return resultMap;
        }
        for (Map<String, Object> map : result) {
            String month = (String) map.get("month");
            Double total = (Double) map.get("total");
            resultMap.put(month, total != null ? total.intValue() : 0);
        }
        return resultMap;
    }

    @Select("SELECT * FROM pro_task WHERE id = #{id} and deleted = 0 FOR UPDATE")
    TaskDO selectForUpdate(Long id);

}
