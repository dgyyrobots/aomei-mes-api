package com.dofast.module.mes.dal.mysql.registrationline;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.registrationline.RegistrationLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.registrationline.vo.*;

/**
 * 计时登记记录 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface RegistrationLineMapper extends BaseMapperX<RegistrationLineDO> {

    default PageResult<RegistrationLineDO> selectPage(RegistrationLinePageReqVO reqVO) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return selectPage(reqVO, new LambdaQueryWrapperX<RegistrationLineDO>()
                .eqIfPresent(RegistrationLineDO::getRegistrationCode, reqVO.getRegistrationCode())
                .eqIfPresent(RegistrationLineDO::getRegistrationId, reqVO.getRegistrationId())
                .eqIfPresent(RegistrationLineDO::getRegistrationType, reqVO.getRegistrationType())
                .betweenIfPresent(RegistrationLineDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(RegistrationLineDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(RegistrationLineDO::getRelatedMachineryCode, reqVO.getRelatedMachineryCode())
                .likeIfPresent(RegistrationLineDO::getRelatedMachineryName, reqVO.getRelatedMachineryName())
                .eqIfPresent(RegistrationLineDO::getRelatedErpMachineryCode, reqVO.getRelatedErpMachineryCode())
                .eqIfPresent(RegistrationLineDO::getWorkshopId, reqVO.getWorkshopId())
                .likeIfPresent(RegistrationLineDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(RegistrationLineDO::getWorkshopCode, reqVO.getWorkshopCode())
                .eqIfPresent(RegistrationLineDO::getRelatedWorkorder, reqVO.getRelatedWorkorder())
                .eqIfPresent(RegistrationLineDO::getRelatedTaskCode, reqVO.getRelatedTaskCode())
                .eqIfPresent(RegistrationLineDO::getRelatedTaskId, reqVO.getRelatedTaskId())
                .eqIfPresent(RegistrationLineDO::getDurationTime, reqVO.getDurationTime())
                .eqIfPresent(RegistrationLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RegistrationLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RegistrationLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RegistrationLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RegistrationLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RegistrationLineDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(RegistrationLineDO::getCreateTime, startOfDay, endOfDay)
                .orderByDesc(RegistrationLineDO::getId));
    }

    default List<RegistrationLineDO> selectList(RegistrationLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RegistrationLineDO>()
                .eqIfPresent(RegistrationLineDO::getRegistrationCode, reqVO.getRegistrationCode())
                .eqIfPresent(RegistrationLineDO::getRegistrationId, reqVO.getRegistrationId())
                .eqIfPresent(RegistrationLineDO::getRegistrationType, reqVO.getRegistrationType())
                .betweenIfPresent(RegistrationLineDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(RegistrationLineDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(RegistrationLineDO::getRelatedMachineryCode, reqVO.getRelatedMachineryCode())
                .likeIfPresent(RegistrationLineDO::getRelatedMachineryName, reqVO.getRelatedMachineryName())
                .eqIfPresent(RegistrationLineDO::getRelatedErpMachineryCode, reqVO.getRelatedErpMachineryCode())
                .eqIfPresent(RegistrationLineDO::getWorkshopId, reqVO.getWorkshopId())
                .likeIfPresent(RegistrationLineDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(RegistrationLineDO::getWorkshopCode, reqVO.getWorkshopCode())
                .eqIfPresent(RegistrationLineDO::getRelatedWorkorder, reqVO.getRelatedWorkorder())
                .eqIfPresent(RegistrationLineDO::getRelatedTaskCode, reqVO.getRelatedTaskCode())
                .eqIfPresent(RegistrationLineDO::getRelatedTaskId, reqVO.getRelatedTaskId())
                .eqIfPresent(RegistrationLineDO::getDurationTime, reqVO.getDurationTime())
                .eqIfPresent(RegistrationLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RegistrationLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RegistrationLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RegistrationLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RegistrationLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RegistrationLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegistrationLineDO::getId));
    }

}
