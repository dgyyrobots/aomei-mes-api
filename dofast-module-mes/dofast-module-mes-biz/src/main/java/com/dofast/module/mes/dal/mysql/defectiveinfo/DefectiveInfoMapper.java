package com.dofast.module.mes.dal.mysql.defectiveinfo;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.defectiveinfo.DefectiveInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.defectiveinfo.vo.*;

/**
 * 不良品信息管理 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface DefectiveInfoMapper extends BaseMapperX<DefectiveInfoDO> {

    default PageResult<DefectiveInfoDO> selectPage(DefectiveInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DefectiveInfoDO>()
                .betweenIfPresent(DefectiveInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DefectiveInfoDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(DefectiveInfoDO::getItemName, reqVO.getItemName())
                .eqIfPresent(DefectiveInfoDO::getBatchesCode, reqVO.getBatchesCode())
                .eqIfPresent(DefectiveInfoDO::getBatches, reqVO.getBatches())
                .eqIfPresent(DefectiveInfoDO::getBadDescription, reqVO.getBadDescription())
                .eqIfPresent(DefectiveInfoDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(DefectiveInfoDO::getBadQuantity, reqVO.getBadQuantity())
                .eqIfPresent(DefectiveInfoDO::getInspectQty, reqVO.getInspectQty())
                .likeIfPresent(DefectiveInfoDO::getErrorCodeName, reqVO.getErrorCodeName())
                .betweenIfPresent(DefectiveInfoDO::getBadTime, reqVO.getBadTime())
                .eqIfPresent(DefectiveInfoDO::getEntryPerson, reqVO.getEntryPerson())
                .betweenIfPresent(DefectiveInfoDO::getEntryTime, reqVO.getEntryTime())
                .eqIfPresent(DefectiveInfoDO::getReinspector, reqVO.getReinspector())
                .betweenIfPresent(DefectiveInfoDO::getReinspectDate, reqVO.getReinspectDate())
                .eqIfPresent(DefectiveInfoDO::getReinspectConclusion, reqVO.getReinspectConclusion())
                .eqIfPresent(DefectiveInfoDO::getInspectGroup, reqVO.getInspectGroup())
                .eqIfPresent(DefectiveInfoDO::getExcuteState, reqVO.getExcuteState())
                .eqIfPresent(DefectiveInfoDO::getExcuteCode, reqVO.getExcuteCode())
                .orderByDesc(DefectiveInfoDO::getId));
    }

    default List<DefectiveInfoDO> selectList(DefectiveInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DefectiveInfoDO>()
                .betweenIfPresent(DefectiveInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DefectiveInfoDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(DefectiveInfoDO::getItemName, reqVO.getItemName())
                .eqIfPresent(DefectiveInfoDO::getBatchesCode, reqVO.getBatchesCode())
                .eqIfPresent(DefectiveInfoDO::getBatches, reqVO.getBatches())
                .eqIfPresent(DefectiveInfoDO::getBadDescription, reqVO.getBadDescription())
                .eqIfPresent(DefectiveInfoDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(DefectiveInfoDO::getBadQuantity, reqVO.getBadQuantity())
                .eqIfPresent(DefectiveInfoDO::getInspectQty, reqVO.getInspectQty())
                .eqIfPresent(DefectiveInfoDO::getErrorCodeName, reqVO.getErrorCodeName())
                .betweenIfPresent(DefectiveInfoDO::getBadTime, reqVO.getBadTime())
                .eqIfPresent(DefectiveInfoDO::getEntryPerson, reqVO.getEntryPerson())
                .betweenIfPresent(DefectiveInfoDO::getEntryTime, reqVO.getEntryTime())
                .eqIfPresent(DefectiveInfoDO::getReinspector, reqVO.getReinspector())
                .betweenIfPresent(DefectiveInfoDO::getReinspectDate, reqVO.getReinspectDate())
                .eqIfPresent(DefectiveInfoDO::getReinspectConclusion, reqVO.getReinspectConclusion())
                .eqIfPresent(DefectiveInfoDO::getInspectGroup, reqVO.getInspectGroup())
                .eqIfPresent(DefectiveInfoDO::getExcuteState, reqVO.getExcuteState())
                .eqIfPresent(DefectiveInfoDO::getExcuteCode, reqVO.getExcuteCode())
                .orderByDesc(DefectiveInfoDO::getId));
    }

}
