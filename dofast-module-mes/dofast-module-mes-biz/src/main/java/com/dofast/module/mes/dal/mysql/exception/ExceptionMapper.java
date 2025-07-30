package com.dofast.module.mes.dal.mysql.exception;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.exception.ExceptionDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.exception.vo.*;

/**
 * 异常登记 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ExceptionMapper extends BaseMapperX<ExceptionDO> {

    default PageResult<ExceptionDO> selectPage(ExceptionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ExceptionDO>()
                .eqIfPresent(ExceptionDO::getExceptionCode, reqVO.getExceptionCode())
                .eqIfPresent(ExceptionDO::getExceptionType, reqVO.getExceptionType())
                .eqIfPresent(ExceptionDO::getSubclassExceptionCode, reqVO.getSubclassExceptionCode())
                .eqIfPresent(ExceptionDO::getSubclassExceptionName, reqVO.getSubclassExceptionName())
                .eqIfPresent(ExceptionDO::getExceptionLevelCode, reqVO.getExceptionLevelCode())
                .eqIfPresent(ExceptionDO::getExceptionLevelName, reqVO.getExceptionLevelName())
                .eqIfPresent(ExceptionDO::getExceptionLevelColor, reqVO.getExceptionLevelColor())
                .eqIfPresent(ExceptionDO::getTitle, reqVO.getTitle())
                .eqIfPresent(ExceptionDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(ExceptionDO::getRegisterTime, reqVO.getRegisterTime())
                .eqIfPresent(ExceptionDO::getRegisterUser, reqVO.getRegisterUser())
                .eqIfPresent(ExceptionDO::getWorkshopId, reqVO.getWorkshopId())
                .eqIfPresent(ExceptionDO::getWorkshopCode, reqVO.getWorkshopCode())
                .eqIfPresent(ExceptionDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(ExceptionDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(ExceptionDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(ExceptionDO::getProcessName, reqVO.getProcessName())

                .eqIfPresent(ExceptionDO::getRelatedMachineryCode, reqVO.getRelatedMachineryCode())
                .eqIfPresent(ExceptionDO::getRelatedMachineryName, reqVO.getRelatedMachineryName())
                .eqIfPresent(ExceptionDO::getRelatedErpMachineryCode, reqVO.getRelatedErpMachineryCode())
                .eqIfPresent(ExceptionDO::getRelatedMaterial, reqVO.getRelatedMaterial())
                .eqIfPresent(ExceptionDO::getRelatedWorkorder, reqVO.getRelatedWorkorder())
                .eqIfPresent(ExceptionDO::getRelatedTaskCode, reqVO.getRelatedTaskCode())
                .eqIfPresent(ExceptionDO::getRelatedTaskId, reqVO.getRelatedTaskId())
                .eqIfPresent(ExceptionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ExceptionDO::getEstimatedImpact, reqVO.getEstimatedImpact())
                .eqIfPresent(ExceptionDO::getSolution, reqVO.getSolution())
                .betweenIfPresent(ExceptionDO::getCloseTime, reqVO.getCloseTime())
                .eqIfPresent(ExceptionDO::getAttachments, reqVO.getAttachments())
                .eqIfPresent(ExceptionDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ExceptionDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ExceptionDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ExceptionDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ExceptionDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ExceptionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ExceptionDO::getId));
    }

    default List<ExceptionDO> selectList(ExceptionExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ExceptionDO>()
                .eqIfPresent(ExceptionDO::getExceptionCode, reqVO.getExceptionCode())
                .eqIfPresent(ExceptionDO::getExceptionType, reqVO.getExceptionType())
                .eqIfPresent(ExceptionDO::getSubclassExceptionCode, reqVO.getSubclassExceptionCode())
                .eqIfPresent(ExceptionDO::getSubclassExceptionName, reqVO.getSubclassExceptionName())
                .eqIfPresent(ExceptionDO::getExceptionLevelCode, reqVO.getExceptionLevelCode())
                .eqIfPresent(ExceptionDO::getExceptionLevelName, reqVO.getExceptionLevelName())
                .eqIfPresent(ExceptionDO::getExceptionLevelColor, reqVO.getExceptionLevelColor())
                .eqIfPresent(ExceptionDO::getTitle, reqVO.getTitle())
                .eqIfPresent(ExceptionDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(ExceptionDO::getRegisterTime, reqVO.getRegisterTime())
                .eqIfPresent(ExceptionDO::getRegisterUser, reqVO.getRegisterUser())
                .eqIfPresent(ExceptionDO::getWorkshopId, reqVO.getWorkshopId())
                .eqIfPresent(ExceptionDO::getWorkshopCode, reqVO.getWorkshopCode())
                .eqIfPresent(ExceptionDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(ExceptionDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(ExceptionDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(ExceptionDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(ExceptionDO::getRelatedMachineryCode, reqVO.getRelatedMachineryCode())
                .eqIfPresent(ExceptionDO::getRelatedMachineryName, reqVO.getRelatedMachineryName())
                .eqIfPresent(ExceptionDO::getRelatedErpMachineryCode, reqVO.getRelatedErpMachineryCode())
                .eqIfPresent(ExceptionDO::getRelatedMaterial, reqVO.getRelatedMaterial())
                .eqIfPresent(ExceptionDO::getRelatedWorkorder, reqVO.getRelatedWorkorder())
                .eqIfPresent(ExceptionDO::getRelatedTaskCode, reqVO.getRelatedTaskCode())
                .eqIfPresent(ExceptionDO::getRelatedTaskId, reqVO.getRelatedTaskId())
                .eqIfPresent(ExceptionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ExceptionDO::getEstimatedImpact, reqVO.getEstimatedImpact())
                .eqIfPresent(ExceptionDO::getSolution, reqVO.getSolution())
                .betweenIfPresent(ExceptionDO::getCloseTime, reqVO.getCloseTime())
                .eqIfPresent(ExceptionDO::getAttachments, reqVO.getAttachments())
                .eqIfPresent(ExceptionDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ExceptionDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ExceptionDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ExceptionDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ExceptionDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ExceptionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ExceptionDO::getId));
    }

}
