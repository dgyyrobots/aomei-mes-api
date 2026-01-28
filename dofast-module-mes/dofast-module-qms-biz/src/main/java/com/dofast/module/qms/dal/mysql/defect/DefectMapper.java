package com.dofast.module.qms.dal.mysql.defect;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.defect.DefectDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.defect.vo.*;

/**
 * 常见缺陷 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DefectMapper extends BaseMapperX<DefectDO> {

    default PageResult<DefectDO> selectPage(DefectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DefectDO>()
                .eqIfPresent(DefectDO::getDefectCode, reqVO.getDefectCode())
                .likeIfPresent(DefectDO::getDefectName, reqVO.getDefectName())
                .eqIfPresent(DefectDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(DefectDO::getDefectLevel, reqVO.getDefectLevel())
                .eqIfPresent(DefectDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DefectDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DefectDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DefectDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DefectDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DefectDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DefectDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(DefectDO::getProcessName, reqVO.getProcessName())
                .orderByDesc(DefectDO::getId));
    }

    default List<DefectDO> selectList(DefectExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DefectDO>()
                .eqIfPresent(DefectDO::getDefectCode, reqVO.getDefectCode())
                .likeIfPresent(DefectDO::getDefectName, reqVO.getDefectName())
                .eqIfPresent(DefectDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(DefectDO::getDefectLevel, reqVO.getDefectLevel())
                .eqIfPresent(DefectDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DefectDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DefectDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DefectDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DefectDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DefectDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DefectDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(DefectDO::getProcessName, reqVO.getProcessName())
                .orderByDesc(DefectDO::getId));
    }

}
