package com.dofast.module.qms.dal.mysql.ipqc;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.ipqc.IpqcDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.ipqc.vo.*;

/**
 * 过程检验单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface IpqcMapper extends BaseMapperX<IpqcDO> {

    /**
     * 验证检测编码是否唯一
     * @param qcIpqc
     * @return
     */
    default IpqcDO checkIpqcCodeUnique(IpqcBaseVO qcIpqc){
        return selectOne(new LambdaQueryWrapperX<IpqcDO>().eq(IpqcDO::getIpqcCode,qcIpqc.getIpqcCode()));
    }
    /**
     * 根据缺陷记录更新头上的缺陷数量和缺陷率
     * @param ipqcId
     * @return
     */
    public int updateCrMajMinQuaAndRate(Long ipqcId);
    default PageResult<IpqcDO> selectPage(IpqcPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IpqcDO>()
                .eqIfPresent(IpqcDO::getIpqcCode, reqVO.getIpqcCode())
                .likeIfPresent(IpqcDO::getIpqcName, reqVO.getIpqcName())
                .eqIfPresent(IpqcDO::getIpqcType, reqVO.getIpqcType())
                .eqIfPresent(IpqcDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(IpqcDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(IpqcDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(IpqcDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(IpqcDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(IpqcDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(IpqcDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(IpqcDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(IpqcDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(IpqcDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(IpqcDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(IpqcDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(IpqcDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(IpqcDO::getItemId, reqVO.getItemId())
                .eqIfPresent(IpqcDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(IpqcDO::getItemName, reqVO.getItemName())
                .eqIfPresent(IpqcDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(IpqcDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IpqcDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(IpqcDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(IpqcDO::getQuantityQualified, reqVO.getQuantityQualified())
                .eqIfPresent(IpqcDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(IpqcDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(IpqcDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(IpqcDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(IpqcDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(IpqcDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(IpqcDO::getCheckResult, reqVO.getCheckResult())
                .betweenIfPresent(IpqcDO::getInspectDate, reqVO.getInspectDate())
                .eqIfPresent(IpqcDO::getInspector, reqVO.getInspector())
                .eqIfPresent(IpqcDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IpqcDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IpqcDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IpqcDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IpqcDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IpqcDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(IpqcDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(IpqcDO::getAdjuncts, reqVO.getAdjuncts())
                .orderByDesc(IpqcDO::getId));
    }

    default List<IpqcDO> selectList(IpqcExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<IpqcDO>()
                .eqIfPresent(IpqcDO::getIpqcCode, reqVO.getIpqcCode())
                .likeIfPresent(IpqcDO::getIpqcName, reqVO.getIpqcName())
                .eqIfPresent(IpqcDO::getIpqcType, reqVO.getIpqcType())
                .eqIfPresent(IpqcDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(IpqcDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(IpqcDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(IpqcDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(IpqcDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(IpqcDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(IpqcDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(IpqcDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(IpqcDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(IpqcDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(IpqcDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(IpqcDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(IpqcDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(IpqcDO::getItemId, reqVO.getItemId())
                .eqIfPresent(IpqcDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(IpqcDO::getItemName, reqVO.getItemName())
                .eqIfPresent(IpqcDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(IpqcDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IpqcDO::getQuantityCheck, reqVO.getQuantityCheck())
                .eqIfPresent(IpqcDO::getQuantityUnqualified, reqVO.getQuantityUnqualified())
                .eqIfPresent(IpqcDO::getQuantityQualified, reqVO.getQuantityQualified())
                .eqIfPresent(IpqcDO::getCrRate, reqVO.getCrRate())
                .eqIfPresent(IpqcDO::getMajRate, reqVO.getMajRate())
                .eqIfPresent(IpqcDO::getMinRate, reqVO.getMinRate())
                .eqIfPresent(IpqcDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(IpqcDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(IpqcDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(IpqcDO::getCheckResult, reqVO.getCheckResult())
                .betweenIfPresent(IpqcDO::getInspectDate, reqVO.getInspectDate())
                .eqIfPresent(IpqcDO::getInspector, reqVO.getInspector())
                .eqIfPresent(IpqcDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IpqcDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IpqcDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IpqcDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IpqcDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IpqcDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(IpqcDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(IpqcDO::getAdjuncts, reqVO.getAdjuncts())
                .orderByDesc(IpqcDO::getId));
    }

    public List<Map<String, Object>> getProcessQuality();

}
