package com.dofast.module.qms.dal.mysql.oqc;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.iqc.IqcDO;
import com.dofast.module.qms.dal.dataobject.oqc.OqcDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.oqc.vo.*;

/**
 * 出货检验单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OqcMapper extends BaseMapperX<OqcDO> {
    int updateCrMajMinQuaAndRate(Long oqcId);
    default OqcDO checkOqcCodeUnique(OqcBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<OqcDO>().eq(OqcDO::getOqcCode,baseVO.getOqcCode()));
    }
    default PageResult<OqcDO> selectPage(OqcPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OqcDO>()
                .eqIfPresent(OqcDO::getOqcCode, reqVO.getOqcCode())
                .eqIfPresent(OqcDO::getSourceCode,reqVO.getSourceCode())
                .likeIfPresent(OqcDO::getOqcName, reqVO.getOqcName())
                .eqIfPresent(OqcDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(OqcDO::getClientId, reqVO.getClientId())
                .eqIfPresent(OqcDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(OqcDO::getClientName, reqVO.getClientName())
                .eqIfPresent(OqcDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(OqcDO::getItemId, reqVO.getItemId())
                .eqIfPresent(OqcDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(OqcDO::getItemName, reqVO.getItemName())
                .eqIfPresent(OqcDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(OqcDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(OqcDO::getQuantityMinCheck, reqVO.getQuantityMinCheck())
                .eqIfPresent(OqcDO::getQuantityMaxUnqualified, reqVO.getQuantityMaxUnqualified())
                .eqIfPresent(OqcDO::getQuantityOut, reqVO.getQuantityOut())
                .eqIfPresent(OqcDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(OqcDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(OqcDO::getQuantityQuanlified, reqVO.getQuantityQuanlified())
                .eqIfPresent(OqcDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(OqcDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(OqcDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(OqcDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(OqcDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(OqcDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(OqcDO::getCheckResult, reqVO.getCheckResult())
                .betweenIfPresent(OqcDO::getOutDate, reqVO.getOutDate())
                .betweenIfPresent(OqcDO::getInspectDate, reqVO.getInspectDate())
                .eqIfPresent(OqcDO::getInspector, reqVO.getInspector())
                .eqIfPresent(OqcDO::getStatus, reqVO.getStatus())
                .eqIfPresent(OqcDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OqcDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(OqcDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(OqcDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(OqcDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(OqcDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OqcDO::getAdjuncts, reqVO.getAdjuncts())
                .orderByDesc(OqcDO::getId));
    }

    default List<OqcDO> selectList(OqcExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OqcDO>()
                .eqIfPresent(OqcDO::getOqcCode, reqVO.getOqcCode())
                .eqIfPresent(OqcDO::getSourceCode,reqVO.getSourceCode())
                .eqIfPresent(OqcDO::getOqcName, reqVO.getOqcName())
                .eqIfPresent(OqcDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(OqcDO::getClientId, reqVO.getClientId())
                .eqIfPresent(OqcDO::getClientCode, reqVO.getClientCode())
                .eqIfPresent(OqcDO::getClientName, reqVO.getClientName())
                .eqIfPresent(OqcDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(OqcDO::getItemId, reqVO.getItemId())
                .eqIfPresent(OqcDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(OqcDO::getItemName, reqVO.getItemName())
                .eqIfPresent(OqcDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(OqcDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(OqcDO::getQuantityMinCheck, reqVO.getQuantityMinCheck())
                .eqIfPresent(OqcDO::getQuantityMaxUnqualified, reqVO.getQuantityMaxUnqualified())
                .eqIfPresent(OqcDO::getQuantityOut, reqVO.getQuantityOut())
                .eqIfPresent(OqcDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(OqcDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(OqcDO::getQuantityQuanlified, reqVO.getQuantityQuanlified())
                .eqIfPresent(OqcDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(OqcDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(OqcDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(OqcDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(OqcDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(OqcDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(OqcDO::getCheckResult, reqVO.getCheckResult())
                .betweenIfPresent(OqcDO::getOutDate, reqVO.getOutDate())
                .betweenIfPresent(OqcDO::getInspectDate, reqVO.getInspectDate())
                .eqIfPresent(OqcDO::getInspector, reqVO.getInspector())
                .eqIfPresent(OqcDO::getStatus, reqVO.getStatus())
                .eqIfPresent(OqcDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OqcDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(OqcDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(OqcDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(OqcDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(OqcDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OqcDO::getAdjuncts, reqVO.getAdjuncts())
                .orderByDesc(OqcDO::getId));
    }

}
