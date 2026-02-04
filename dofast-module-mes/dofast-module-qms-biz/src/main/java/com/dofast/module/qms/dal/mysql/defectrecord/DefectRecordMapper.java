package com.dofast.module.qms.dal.mysql.defectrecord;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.defectrecord.DefectRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.defectrecord.vo.*;

/**
 * 检验单缺陷记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DefectRecordMapper extends BaseMapperX<DefectRecordDO> {
    default int deleteByQcIdAndType(DefectRecordBaseVO baseVO){
        return delete(new LambdaQueryWrapperX<DefectRecordDO>().eq(DefectRecordDO::getQcId,baseVO.getQcId()).eq(DefectRecordDO::getQcType,baseVO.getQcId()));
    }

    default PageResult<DefectRecordDO> selectPage(DefectRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DefectRecordDO>()
                .eqIfPresent(DefectRecordDO::getQcType, reqVO.getQcType())
                .eqIfPresent(DefectRecordDO::getQcId, reqVO.getQcId())
                .eqIfPresent(DefectRecordDO::getLineId, reqVO.getLineId())
                .likeIfPresent(DefectRecordDO::getDefectName, reqVO.getDefectName())
                .eqIfPresent(DefectRecordDO::getDefectLevel, reqVO.getDefectLevel())
                .eqIfPresent(DefectRecordDO::getDefectQuantity, reqVO.getDefectQuantity())
                .eqIfPresent(DefectRecordDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DefectRecordDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DefectRecordDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DefectRecordDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DefectRecordDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DefectRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DefectRecordDO::getId));
    }

    default List<DefectRecordDO> selectList(DefectRecordExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DefectRecordDO>()
                .eqIfPresent(DefectRecordDO::getQcType, reqVO.getQcType())
                .eqIfPresent(DefectRecordDO::getQcId, reqVO.getQcId())
                .eqIfPresent(DefectRecordDO::getLineId, reqVO.getLineId())
                .eqIfPresent(DefectRecordDO::getDefectName, reqVO.getDefectName())
                .eqIfPresent(DefectRecordDO::getDefectLevel, reqVO.getDefectLevel())
                .eqIfPresent(DefectRecordDO::getDefectQuantity, reqVO.getDefectQuantity())
                .eqIfPresent(DefectRecordDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DefectRecordDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DefectRecordDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DefectRecordDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DefectRecordDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DefectRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DefectRecordDO::getId));
    }

}
