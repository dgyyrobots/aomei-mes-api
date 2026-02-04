package com.dofast.module.mes.dal.mysql.mdunitmeasure;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdunitmeasure.MdUnitMeasureDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdunitmeasure.vo.*;

/**
 * 单位 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdUnitMeasureMapper extends BaseMapperX<MdUnitMeasureDO> {

    default PageResult<MdUnitMeasureDO> selectPage(MdUnitMeasurePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdUnitMeasureDO>()
                .eqIfPresent(MdUnitMeasureDO::getMeasureCode, reqVO.getMeasureCode())
                .likeIfPresent(MdUnitMeasureDO::getMeasureName, reqVO.getMeasureName())
                .eqIfPresent(MdUnitMeasureDO::getPrimaryFlag, reqVO.getPrimaryFlag())
                .eqIfPresent(MdUnitMeasureDO::getPrimaryId, reqVO.getPrimaryId())
                .eqIfPresent(MdUnitMeasureDO::getChangeRate, reqVO.getChangeRate())
                .eqIfPresent(MdUnitMeasureDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdUnitMeasureDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdUnitMeasureDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdUnitMeasureDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdUnitMeasureDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdUnitMeasureDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdUnitMeasureDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdUnitMeasureDO::getId));
    }
    default List<MdUnitMeasureDO> selectList(MdUnitMeasureListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdUnitMeasureDO>()
                .eqIfPresent(MdUnitMeasureDO::getMeasureCode, reqVO.getMeasureCode())
                .eqIfPresent(MdUnitMeasureDO::getMeasureName, reqVO.getMeasureName())
                .eqIfPresent(MdUnitMeasureDO::getPrimaryFlag, reqVO.getPrimaryFlag())
                .eqIfPresent(MdUnitMeasureDO::getPrimaryId, reqVO.getPrimaryId())
                .eqIfPresent(MdUnitMeasureDO::getChangeRate, reqVO.getChangeRate())
                .eqIfPresent(MdUnitMeasureDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdUnitMeasureDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdUnitMeasureDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdUnitMeasureDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdUnitMeasureDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdUnitMeasureDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(MdUnitMeasureDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdUnitMeasureDO::getId));
    }
    default List<MdUnitMeasureDO> selectList(MdUnitMeasureExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdUnitMeasureDO>()
                .eqIfPresent(MdUnitMeasureDO::getMeasureCode, reqVO.getMeasureCode())
                .likeIfPresent(MdUnitMeasureDO::getMeasureName, reqVO.getMeasureName())
                .eqIfPresent(MdUnitMeasureDO::getPrimaryFlag, reqVO.getPrimaryFlag())
                .eqIfPresent(MdUnitMeasureDO::getPrimaryId, reqVO.getPrimaryId())
                .eqIfPresent(MdUnitMeasureDO::getChangeRate, reqVO.getChangeRate())
                .eqIfPresent(MdUnitMeasureDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdUnitMeasureDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdUnitMeasureDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdUnitMeasureDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdUnitMeasureDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdUnitMeasureDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdUnitMeasureDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdUnitMeasureDO::getId));
    }

}
