package com.dofast.module.qms.dal.mysql.template;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.template.TemplateDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.template.vo.*;

/**
 * 检测模板 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TemplateMapper extends BaseMapperX<TemplateDO> {
    TemplateDO selectQcTemplateByProductAndQcType(TemplateBaseVO baseVO);
    TemplateDO findTemplateByProductIdAndQcType(TemplateBaseVO baseVO);

    default TemplateDO checkTemplateCodeUnique(TemplateBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<TemplateDO>().eq(TemplateDO::getTemplateCode,baseVO.getTemplateCode()));
    }

    default PageResult<TemplateDO> selectPage(TemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemplateDO>()
                .eqIfPresent(TemplateDO::getTemplateCode, reqVO.getTemplateCode())
                .likeIfPresent(TemplateDO::getTemplateName, reqVO.getTemplateName())
                .eqIfPresent(TemplateDO::getQcTypes, reqVO.getQcTypes())
                .eqIfPresent(TemplateDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(TemplateDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TemplateDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TemplateDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(TemplateDO::getProcessName, reqVO.getProcessName())
                .betweenIfPresent(TemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateDO::getId));
    }

    default List<TemplateDO> selectList(TemplateExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TemplateDO>()
                .eqIfPresent(TemplateDO::getTemplateCode, reqVO.getTemplateCode())
                .eqIfPresent(TemplateDO::getTemplateName, reqVO.getTemplateName())
                .eqIfPresent(TemplateDO::getQcTypes, reqVO.getQcTypes())
                .eqIfPresent(TemplateDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(TemplateDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TemplateDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TemplateDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(TemplateDO::getProcessName, reqVO.getProcessName())
                .betweenIfPresent(TemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateDO::getId));
    }

}
