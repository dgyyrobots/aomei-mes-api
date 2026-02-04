package com.dofast.module.mes.dal.mysql.registration;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.exception.ExceptionDO;
import com.dofast.module.mes.dal.dataobject.registration.RegistrationDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.registration.vo.*;

/**
 * 计时登记 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface RegistrationMapper extends BaseMapperX<RegistrationDO> {

    default PageResult<RegistrationDO> selectPage(RegistrationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegistrationDO>()
                .eqIfPresent(RegistrationDO::getRegistrationCode, reqVO.getRegistrationCode())
                .likeIfPresent(RegistrationDO::getRegistrationName, reqVO.getRegistrationName())
                .eqIfPresent(RegistrationDO::getRegistrationType, reqVO.getRegistrationType())
                .betweenIfPresent(RegistrationDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(RegistrationDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(RegistrationDO::getWorkshopId, reqVO.getWorkshopId())
                .likeIfPresent(RegistrationDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(RegistrationDO::getWorkshopCode, reqVO.getWorkshopCode())
                .eqIfPresent(RegistrationDO::getRelatedMachineryCode, reqVO.getRelatedMachineryCode())
                .likeIfPresent(RegistrationDO::getRelatedMachineryName, reqVO.getRelatedMachineryName())
                .eqIfPresent(RegistrationDO::getRelatedErpMachineryCode, reqVO.getRelatedErpMachineryCode())
                .eqIfPresent(RegistrationDO::getRelatedWorkorder, reqVO.getRelatedWorkorder())
                .eqIfPresent(RegistrationDO::getRelatedTaskCode, reqVO.getRelatedTaskCode())
                .eqIfPresent(RegistrationDO::getRelatedTaskId, reqVO.getRelatedTaskId())
                .eqIfPresent(RegistrationDO::getRelatedMaterialCode, reqVO.getRelatedMaterialCode())
                .likeIfPresent(RegistrationDO::getRelatedMaterialName, reqVO.getRelatedMaterialName())
                .eqIfPresent(RegistrationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RegistrationDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RegistrationDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RegistrationDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RegistrationDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RegistrationDO::getDurationTime, reqVO.getDurationTime())
                .eqIfPresent(RegistrationDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegistrationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegistrationDO::getId));
    }

    default List<RegistrationDO> selectList(RegistrationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RegistrationDO>()
                .eqIfPresent(RegistrationDO::getRegistrationCode, reqVO.getRegistrationCode())
                .eqIfPresent(RegistrationDO::getRegistrationName, reqVO.getRegistrationName())
                .eqIfPresent(RegistrationDO::getRegistrationType, reqVO.getRegistrationType())
                .betweenIfPresent(RegistrationDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(RegistrationDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(RegistrationDO::getWorkshopId, reqVO.getWorkshopId())
                .eqIfPresent(RegistrationDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(RegistrationDO::getWorkshopCode, reqVO.getWorkshopCode())
                .eqIfPresent(RegistrationDO::getRelatedMachineryCode, reqVO.getRelatedMachineryCode())
                .eqIfPresent(RegistrationDO::getRelatedMachineryName, reqVO.getRelatedMachineryName())
                .eqIfPresent(RegistrationDO::getRelatedErpMachineryCode, reqVO.getRelatedErpMachineryCode())
                .eqIfPresent(RegistrationDO::getRelatedWorkorder, reqVO.getRelatedWorkorder())
                .eqIfPresent(RegistrationDO::getRelatedTaskCode, reqVO.getRelatedTaskCode())
                .eqIfPresent(RegistrationDO::getRelatedTaskId, reqVO.getRelatedTaskId())
                .eqIfPresent(RegistrationDO::getRelatedMaterialCode, reqVO.getRelatedMaterialCode())
                .eqIfPresent(RegistrationDO::getRelatedMaterialName, reqVO.getRelatedMaterialName())
                .eqIfPresent(RegistrationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RegistrationDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RegistrationDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RegistrationDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RegistrationDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RegistrationDO::getDurationTime, reqVO.getDurationTime())
                .eqIfPresent(RegistrationDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegistrationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegistrationDO::getId));
    }

}
