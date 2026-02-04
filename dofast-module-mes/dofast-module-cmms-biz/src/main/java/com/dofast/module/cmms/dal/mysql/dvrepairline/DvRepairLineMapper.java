package com.dofast.module.cmms.dal.mysql.dvrepairline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cmms.dal.dataobject.dvrepairline.DvRepairLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cmms.controller.admin.dvrepairline.vo.*;

/**
 * 设备维修单行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DvRepairLineMapper extends BaseMapperX<DvRepairLineDO> {

    default PageResult<DvRepairLineDO> selectPage(DvRepairLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DvRepairLineDO>()
                .eqIfPresent(DvRepairLineDO::getRepairId, reqVO.getRepairId())
                .eqIfPresent(DvRepairLineDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(DvRepairLineDO::getSubjectCode, reqVO.getSubjectCode())
                .likeIfPresent(DvRepairLineDO::getSubjectName, reqVO.getSubjectName())
                .eqIfPresent(DvRepairLineDO::getSubjectType, reqVO.getSubjectType())
                .eqIfPresent(DvRepairLineDO::getSubjectContent, reqVO.getSubjectContent())
                .eqIfPresent(DvRepairLineDO::getSubjectStandard, reqVO.getSubjectStandard())
                .eqIfPresent(DvRepairLineDO::getMalfunction, reqVO.getMalfunction())
                .eqIfPresent(DvRepairLineDO::getMalfunctionUrl, reqVO.getMalfunctionUrl())
                .eqIfPresent(DvRepairLineDO::getRepairDes, reqVO.getRepairDes())
                .eqIfPresent(DvRepairLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvRepairLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvRepairLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvRepairLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvRepairLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvRepairLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvRepairLineDO::getId));
    }

    default List<DvRepairLineDO> selectList(DvRepairLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DvRepairLineDO>()
                .eqIfPresent(DvRepairLineDO::getRepairId, reqVO.getRepairId())
                .eqIfPresent(DvRepairLineDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(DvRepairLineDO::getSubjectCode, reqVO.getSubjectCode())
                .eqIfPresent(DvRepairLineDO::getSubjectName, reqVO.getSubjectName())
                .eqIfPresent(DvRepairLineDO::getSubjectType, reqVO.getSubjectType())
                .eqIfPresent(DvRepairLineDO::getSubjectContent, reqVO.getSubjectContent())
                .eqIfPresent(DvRepairLineDO::getSubjectStandard, reqVO.getSubjectStandard())
                .eqIfPresent(DvRepairLineDO::getMalfunction, reqVO.getMalfunction())
                .eqIfPresent(DvRepairLineDO::getMalfunctionUrl, reqVO.getMalfunctionUrl())
                .eqIfPresent(DvRepairLineDO::getRepairDes, reqVO.getRepairDes())
                .eqIfPresent(DvRepairLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvRepairLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvRepairLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvRepairLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvRepairLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvRepairLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvRepairLineDO::getId));
    }

}
