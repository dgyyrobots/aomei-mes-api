package com.dofast.module.mes.dal.mysql.mdworkstationmachine;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdworkstationmachine.MdWorkstationMachineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdworkstationmachine.vo.*;

/**
 * 设备资源 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdWorkstationMachineMapper extends BaseMapperX<MdWorkstationMachineDO> {
    default MdWorkstationMachineDO checkMachineryExists(MdWorkstationMachineBaseVO mdWorkstationMachine){
        return selectOne(new LambdaQueryWrapperX<MdWorkstationMachineDO>().eq(MdWorkstationMachineDO::getMachineryCode,mdWorkstationMachine.getMachineryCode()));
    }

    default int deleteByWorkstationId(Long workstationId){
        return delete(new LambdaQueryWrapperX<MdWorkstationMachineDO>().eq(MdWorkstationMachineDO::getWorkstationId,workstationId));
    }
    default PageResult<MdWorkstationMachineDO> selectPage(MdWorkstationMachinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdWorkstationMachineDO>()
                .eqIfPresent(MdWorkstationMachineDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(MdWorkstationMachineDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(MdWorkstationMachineDO::getMachineryCode, reqVO.getMachineryCode())
                .likeIfPresent(MdWorkstationMachineDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(MdWorkstationMachineDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdWorkstationMachineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkstationMachineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkstationMachineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkstationMachineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkstationMachineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkstationMachineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkstationMachineDO::getId));
    }

    default List<MdWorkstationMachineDO> selectList(MdWorkstationMachineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdWorkstationMachineDO>()
                .eqIfPresent(MdWorkstationMachineDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(MdWorkstationMachineDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(MdWorkstationMachineDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(MdWorkstationMachineDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(MdWorkstationMachineDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdWorkstationMachineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkstationMachineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkstationMachineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkstationMachineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkstationMachineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkstationMachineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkstationMachineDO::getId));
    }

}
