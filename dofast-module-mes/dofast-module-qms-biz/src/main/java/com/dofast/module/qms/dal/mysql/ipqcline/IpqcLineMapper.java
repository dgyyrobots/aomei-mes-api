package com.dofast.module.qms.dal.mysql.ipqcline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.ipqcline.IpqcLineDO;
import com.dofast.module.qms.dal.dataobject.iqcline.IqcLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.ipqcline.vo.*;

/**
 * 过程检验单行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface IpqcLineMapper extends BaseMapperX<IpqcLineDO> {

    default PageResult<IpqcLineDO> selectPage(IpqcLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IpqcLineDO>()
                .eqIfPresent(IpqcLineDO::getIpqcId, reqVO.getIpqcId())
                .eqIfPresent(IpqcLineDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(IpqcLineDO::getIndexCode, reqVO.getIndexCode())
                .likeIfPresent(IpqcLineDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(IpqcLineDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(IpqcLineDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(IpqcLineDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(IpqcLineDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(IpqcLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IpqcLineDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(IpqcLineDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(IpqcLineDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(IpqcLineDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(IpqcLineDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(IpqcLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IpqcLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IpqcLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IpqcLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IpqcLineDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(IpqcLineDO::getInspectionValue1, reqVO.getInspectionValue1())
                .eqIfPresent(IpqcLineDO::getInspectionValue2, reqVO.getInspectionValue2())
                .eqIfPresent(IpqcLineDO::getInspectionValue3, reqVO.getInspectionValue3())
                .eqIfPresent(IpqcLineDO::getInspectionValue4, reqVO.getInspectionValue4())
                .eqIfPresent(IpqcLineDO::getInspectionValue5, reqVO.getInspectionValue5())
                .eqIfPresent(IpqcLineDO::getInspectionResult, reqVO.getInspectionResult())
                .betweenIfPresent(IpqcLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IpqcLineDO::getId));
    }
    /**
     * 根据缺陷记录更新过程检验单行上的缺陷数量
     * @param qcIpqcLine
     * @return
     */
    int updateCrMajMinQuantity(IpqcLineDO qcIpqcLine);

    default int deleteByIpqcId(Long ipqcId){
        return delete(new LambdaQueryWrapperX<IpqcLineDO>().eq(IpqcLineDO::getIpqcId,ipqcId));
    }



    default List<IpqcLineDO> selectList(IpqcLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<IpqcLineDO>()
                .eqIfPresent(IpqcLineDO::getIpqcId, reqVO.getIpqcId())
                .eqIfPresent(IpqcLineDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(IpqcLineDO::getIndexCode, reqVO.getIndexCode())
                .eqIfPresent(IpqcLineDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(IpqcLineDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(IpqcLineDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(IpqcLineDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(IpqcLineDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(IpqcLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IpqcLineDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(IpqcLineDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(IpqcLineDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(IpqcLineDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(IpqcLineDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(IpqcLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IpqcLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IpqcLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IpqcLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IpqcLineDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(IpqcLineDO::getInspectionValue1, reqVO.getInspectionValue1())
                .eqIfPresent(IpqcLineDO::getInspectionValue2, reqVO.getInspectionValue2())
                .eqIfPresent(IpqcLineDO::getInspectionValue3, reqVO.getInspectionValue3())
                .eqIfPresent(IpqcLineDO::getInspectionValue4, reqVO.getInspectionValue4())
                .eqIfPresent(IpqcLineDO::getInspectionValue5, reqVO.getInspectionValue5())
                .eqIfPresent(IpqcLineDO::getInspectionResult, reqVO.getInspectionResult())
                .betweenIfPresent(IpqcLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IpqcLineDO::getId));
    }

}
