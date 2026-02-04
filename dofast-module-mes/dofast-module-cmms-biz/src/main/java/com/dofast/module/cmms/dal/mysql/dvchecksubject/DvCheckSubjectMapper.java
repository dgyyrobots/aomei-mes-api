package com.dofast.module.cmms.dal.mysql.dvchecksubject;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cmms.dal.dataobject.dvchecksubject.DvCheckSubjectDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cmms.controller.admin.dvchecksubject.vo.*;

/**
 * 点检项目 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DvCheckSubjectMapper extends BaseMapperX<DvCheckSubjectDO> {
    default DvCheckSubjectDO checkSubjectUnique(DvCheckSubjectBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<DvCheckSubjectDO>()
                .eq(DvCheckSubjectDO::getPlanId,baseVO.getPlanId())
                .eq(DvCheckSubjectDO::getSubjectId,baseVO.getSubjectId()));
    }
    default PageResult<DvCheckSubjectDO> selectPage(DvCheckSubjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DvCheckSubjectDO>()
                .eqIfPresent(DvCheckSubjectDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(DvCheckSubjectDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(DvCheckSubjectDO::getSubjectCode, reqVO.getSubjectCode())
                .likeIfPresent(DvCheckSubjectDO::getSubjectName, reqVO.getSubjectName())
                .eqIfPresent(DvCheckSubjectDO::getSubjectType, reqVO.getSubjectType())
                .eqIfPresent(DvCheckSubjectDO::getSubjectContent, reqVO.getSubjectContent())
                .eqIfPresent(DvCheckSubjectDO::getSubjectStandard, reqVO.getSubjectStandard())
                .eqIfPresent(DvCheckSubjectDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckSubjectDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckSubjectDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckSubjectDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckSubjectDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckSubjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvCheckSubjectDO::getId));
    }

    default List<DvCheckSubjectDO> selectList(DvCheckSubjectExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DvCheckSubjectDO>()
                .eqIfPresent(DvCheckSubjectDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(DvCheckSubjectDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(DvCheckSubjectDO::getSubjectCode, reqVO.getSubjectCode())
                .eqIfPresent(DvCheckSubjectDO::getSubjectName, reqVO.getSubjectName())
                .eqIfPresent(DvCheckSubjectDO::getSubjectType, reqVO.getSubjectType())
                .eqIfPresent(DvCheckSubjectDO::getSubjectContent, reqVO.getSubjectContent())
                .eqIfPresent(DvCheckSubjectDO::getSubjectStandard, reqVO.getSubjectStandard())
                .eqIfPresent(DvCheckSubjectDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckSubjectDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckSubjectDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckSubjectDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckSubjectDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckSubjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvCheckSubjectDO::getId));
    }

}
