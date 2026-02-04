package com.dofast.module.mes.dal.mysql.mdworkstationtool;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdworkstationtool.MdWorkstationToolDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdworkstationtool.vo.*;

/**
 * 工装夹具资源 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdWorkstationToolMapper extends BaseMapperX<MdWorkstationToolDO> {
    default int deleteByWorkstationId(Long workstationId){
        return delete(new LambdaQueryWrapperX<MdWorkstationToolDO>().eq(MdWorkstationToolDO::getWorkstationId,workstationId));
    }
    default MdWorkstationToolDO checkToolTypeExists(MdWorkstationToolBaseVO mdWorkstationTool){
        return selectOne(new LambdaQueryWrapperX<MdWorkstationToolDO>().eq(MdWorkstationToolDO::getToolTypeId,mdWorkstationTool.getToolTypeId())
        .eq(MdWorkstationToolDO::getWorkstationId,mdWorkstationTool.getWorkstationId()));
    }
    default PageResult<MdWorkstationToolDO> selectPage(MdWorkstationToolPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdWorkstationToolDO>()
                .eqIfPresent(MdWorkstationToolDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(MdWorkstationToolDO::getToolTypeId, reqVO.getToolTypeId())
                .eqIfPresent(MdWorkstationToolDO::getToolTypeCode, reqVO.getToolTypeCode())
                .likeIfPresent(MdWorkstationToolDO::getToolTypeName, reqVO.getToolTypeName())
                .eqIfPresent(MdWorkstationToolDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdWorkstationToolDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkstationToolDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkstationToolDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkstationToolDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkstationToolDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkstationToolDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkstationToolDO::getId));
    }

    default List<MdWorkstationToolDO> selectList(MdWorkstationToolExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdWorkstationToolDO>()
                .eqIfPresent(MdWorkstationToolDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(MdWorkstationToolDO::getToolTypeId, reqVO.getToolTypeId())
                .eqIfPresent(MdWorkstationToolDO::getToolTypeCode, reqVO.getToolTypeCode())
                .eqIfPresent(MdWorkstationToolDO::getToolTypeName, reqVO.getToolTypeName())
                .eqIfPresent(MdWorkstationToolDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdWorkstationToolDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkstationToolDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkstationToolDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkstationToolDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkstationToolDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkstationToolDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkstationToolDO::getId));
    }

}
