package com.dofast.module.qms.dal.mysql.iqc;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.ipqc.IpqcDO;
import com.dofast.module.qms.dal.dataobject.iqc.IqcDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.iqc.vo.*;

/**
 * 来料检验单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface IqcMapper extends BaseMapperX<IqcDO> {
    int updateCrMajMinQuaAndRate(Long iqcId);
    default IqcDO checkIqcCodeUnique(IqcBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<IqcDO>().eq(IqcDO::getIqcCode,baseVO.getIqcCode()));
    }
    default PageResult<IqcDO> selectPage(IqcPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IqcDO>()
                .eqIfPresent(IqcDO::getIqcCode, reqVO.getIqcCode())
                .likeIfPresent(IqcDO::getIqcName, reqVO.getIqcName())
                .eqIfPresent(IqcDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(IqcDO::getVendorId, reqVO.getVendorId())
                .eqIfPresent(IqcDO::getVendorCode, reqVO.getVendorCode())
                .likeIfPresent(IqcDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(IqcDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(IqcDO::getVendorBatch, reqVO.getVendorBatch())
                .eqIfPresent(IqcDO::getItemId, reqVO.getItemId())
                .eqIfPresent(IqcDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(IqcDO::getItemName, reqVO.getItemName())
                .eqIfPresent(IqcDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(IqcDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IqcDO::getQuantityMinCheck, reqVO.getQuantityMinCheck())
                .eqIfPresent(IqcDO::getQuantityMaxUnqualified, reqVO.getQuantityMaxUnqualified())
                .eqIfPresent(IqcDO::getQuantityRecived, reqVO.getQuantityRecived())
                .eqIfPresent(IqcDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(IqcDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(IqcDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(IqcDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(IqcDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(IqcDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(IqcDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(IqcDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(IqcDO::getCheckResult, reqVO.getCheckResult())
                .betweenIfPresent(IqcDO::getReciveDate, reqVO.getReciveDate())
                .betweenIfPresent(IqcDO::getInspectDate, reqVO.getInspectDate())
                .eqIfPresent(IqcDO::getInspector, reqVO.getInspector())
                .eqIfPresent(IqcDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IqcDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IqcDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IqcDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IqcDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IqcDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(IqcDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(IqcDO::getAdjuncts, reqVO.getAdjuncts())
                .eqIfPresent(IqcDO::getPoNo, reqVO.getPoNo())
                .orderByDesc(IqcDO::getId));
    }

    default List<IqcDO> selectList(IqcExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<IqcDO>()
                .eqIfPresent(IqcDO::getIqcCode, reqVO.getIqcCode())
                .likeIfPresent(IqcDO::getIqcName, reqVO.getIqcName())
                .eqIfPresent(IqcDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(IqcDO::getVendorId, reqVO.getVendorId())
                .eqIfPresent(IqcDO::getVendorCode, reqVO.getVendorCode())
                .likeIfPresent(IqcDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(IqcDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(IqcDO::getVendorBatch, reqVO.getVendorBatch())
                .eqIfPresent(IqcDO::getItemId, reqVO.getItemId())
                .eqIfPresent(IqcDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(IqcDO::getItemName, reqVO.getItemName())
                .eqIfPresent(IqcDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(IqcDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IqcDO::getQuantityMinCheck, reqVO.getQuantityMinCheck())
                .eqIfPresent(IqcDO::getQuantityMaxUnqualified, reqVO.getQuantityMaxUnqualified())
                .eqIfPresent(IqcDO::getQuantityRecived, reqVO.getQuantityRecived())
                .eqIfPresent(IqcDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(IqcDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(IqcDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(IqcDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(IqcDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(IqcDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(IqcDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(IqcDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(IqcDO::getCheckResult, reqVO.getCheckResult())
                .betweenIfPresent(IqcDO::getReciveDate, reqVO.getReciveDate())
                .betweenIfPresent(IqcDO::getInspectDate, reqVO.getInspectDate())
                .eqIfPresent(IqcDO::getInspector, reqVO.getInspector())
                .eqIfPresent(IqcDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IqcDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IqcDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IqcDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IqcDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IqcDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(IqcDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(IqcDO::getAdjuncts, reqVO.getAdjuncts())
                .eqIfPresent(IqcDO::getPoNo, reqVO.getPoNo())
                .orderByDesc(IqcDO::getId));
    }

}
