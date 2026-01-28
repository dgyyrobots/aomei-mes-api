package com.dofast.module.cmms.dal.mysql.dvrepair;

import java.time.LocalDateTime;
import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cmms.dal.dataobject.dvrepair.DvRepairDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cmms.controller.admin.dvrepair.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 设备维修单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DvRepairMapper extends BaseMapperX<DvRepairDO> {
     default DvRepairDO checkCodeUnique(DvRepairBaseVO baseVO){
         return selectOne(new LambdaQueryWrapperX<DvRepairDO>().eq(DvRepairDO::getRepairCode,baseVO.getRepairCode()));
     }

    default PageResult<DvRepairDO> selectPage(DvRepairPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DvRepairDO>()
                .eqIfPresent(DvRepairDO::getRepairCode, reqVO.getRepairCode())
                .likeIfPresent(DvRepairDO::getRepairName, reqVO.getRepairName())
                .eqIfPresent(DvRepairDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(DvRepairDO::getMachineryCode, reqVO.getMachineryCode())
                .likeIfPresent(DvRepairDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(DvRepairDO::getMachineryBrand, reqVO.getMachineryBrand())
                .eqIfPresent(DvRepairDO::getMachinerySpec, reqVO.getMachinerySpec())
                .eqIfPresent(DvRepairDO::getMachineryTypeId, reqVO.getMachineryTypeId())
                .betweenIfPresent(DvRepairDO::getRequireDate, reqVO.getRequireDate())
                .betweenIfPresent(DvRepairDO::getFinishDate, reqVO.getFinishDate())
                .betweenIfPresent(DvRepairDO::getConfirmDate, reqVO.getConfirmDate())
                .eqIfPresent(DvRepairDO::getRepairResult, reqVO.getRepairResult())
                .eqIfPresent(DvRepairDO::getAcceptedBy, reqVO.getAcceptedBy())
                .eqIfPresent(DvRepairDO::getConfirmBy, reqVO.getConfirmBy())
                .eqIfPresent(DvRepairDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DvRepairDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvRepairDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvRepairDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvRepairDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvRepairDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(DvRepairDO::getRepairType, reqVO.getRepairType())

                .betweenIfPresent(DvRepairDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvRepairDO::getId));
    }

    default List<DvRepairDO> selectList(DvRepairExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DvRepairDO>()
                .eqIfPresent(DvRepairDO::getRepairCode, reqVO.getRepairCode())
                .likeIfPresent(DvRepairDO::getRepairName, reqVO.getRepairName())
                .eqIfPresent(DvRepairDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(DvRepairDO::getMachineryCode, reqVO.getMachineryCode())
                .likeIfPresent(DvRepairDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(DvRepairDO::getMachineryBrand, reqVO.getMachineryBrand())
                .eqIfPresent(DvRepairDO::getMachinerySpec, reqVO.getMachinerySpec())
                .eqIfPresent(DvRepairDO::getMachineryTypeId, reqVO.getMachineryTypeId())
                .betweenIfPresent(DvRepairDO::getRequireDate, reqVO.getRequireDate())
                .betweenIfPresent(DvRepairDO::getFinishDate, reqVO.getFinishDate())
                .betweenIfPresent(DvRepairDO::getConfirmDate, reqVO.getConfirmDate())
                .eqIfPresent(DvRepairDO::getRepairResult, reqVO.getRepairResult())
                .eqIfPresent(DvRepairDO::getAcceptedBy, reqVO.getAcceptedBy())
                .eqIfPresent(DvRepairDO::getConfirmBy, reqVO.getConfirmBy())
                .eqIfPresent(DvRepairDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DvRepairDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvRepairDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvRepairDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvRepairDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvRepairDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(DvRepairDO::getRepairType, reqVO.getRepairType())
                .betweenIfPresent(DvRepairDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvRepairDO::getId));
    }

    int countInspectionRepairInCycle(@Param("planId") Long planId, @Param("machineryId") Long machineryId, @Param("repairType") String repairType, @Param("cycleStart") LocalDateTime cycleStart, @Param("cycleEnd") LocalDateTime cycleEnd);

}
