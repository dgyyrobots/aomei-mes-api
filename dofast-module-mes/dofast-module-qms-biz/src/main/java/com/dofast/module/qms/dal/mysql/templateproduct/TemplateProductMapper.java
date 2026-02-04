package com.dofast.module.qms.dal.mysql.templateproduct;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.template.TemplateDO;
import com.dofast.module.qms.dal.dataobject.templateproduct.TemplateProductDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.templateproduct.vo.*;

/**
 * 检测模板-产品 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TemplateProductMapper extends BaseMapperX<TemplateProductDO> {
    default TemplateProductDO checkProductUnique(TemplateProductBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<TemplateProductDO>()
                .eq(TemplateProductDO::getItemId,baseVO.getItemId())
                .eq(TemplateProductDO::getProcessCode,baseVO.getProcessCode()));
    }

    default int deleteByTemplateId(Long templateId){
        return delete(new LambdaQueryWrapperX<TemplateProductDO>().eq(TemplateProductDO::getTemplateId,templateId));
    }
    default PageResult<TemplateProductDO> selectPage(TemplateProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemplateProductDO>()
                .eqIfPresent(TemplateProductDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateProductDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TemplateProductDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TemplateProductDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TemplateProductDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TemplateProductDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TemplateProductDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(TemplateProductDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(TemplateProductDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(TemplateProductDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(TemplateProductDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(TemplateProductDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateProductDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateProductDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateProductDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TemplateProductDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(TemplateProductDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TemplateProductDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(TemplateProductDO::getProcessName, reqVO.getProcessName())
                .orderByDesc(TemplateProductDO::getId));
    }
    default List<TemplateProductDO> selectList(TemplateProductListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TemplateProductDO>()
                .eqIfPresent(TemplateProductDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateProductDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TemplateProductDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(TemplateProductDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TemplateProductDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TemplateProductDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TemplateProductDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(TemplateProductDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(TemplateProductDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(TemplateProductDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(TemplateProductDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(TemplateProductDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateProductDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateProductDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateProductDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TemplateProductDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TemplateProductDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(TemplateProductDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(TemplateProductDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateProductDO::getId));
    }
    default List<TemplateProductDO> selectList(TemplateProductExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TemplateProductDO>()
                .eqIfPresent(TemplateProductDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateProductDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TemplateProductDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(TemplateProductDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TemplateProductDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TemplateProductDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TemplateProductDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(TemplateProductDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(TemplateProductDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(TemplateProductDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(TemplateProductDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(TemplateProductDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateProductDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateProductDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateProductDO::getAttr3, reqVO.getAttr3())

                .eqIfPresent(TemplateProductDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TemplateProductDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(TemplateProductDO::getProcessName, reqVO.getProcessName())
                .betweenIfPresent(TemplateProductDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateProductDO::getId));
    }

    default List<TemplateProductDO> selectListNotContainProcessCode(TemplateProductListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TemplateProductDO>()
                .eqIfPresent(TemplateProductDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateProductDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TemplateProductDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(TemplateProductDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TemplateProductDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TemplateProductDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TemplateProductDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(TemplateProductDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(TemplateProductDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(TemplateProductDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(TemplateProductDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(TemplateProductDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateProductDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateProductDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateProductDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TemplateProductDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TemplateProductDO::getCreateTime, reqVO.getCreateTime())
                .isNull(TemplateProductDO::getProcessCode)
                .isNull(TemplateProductDO::getProcessName)
                .orderByDesc(TemplateProductDO::getId));
    }



}
