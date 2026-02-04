package com.dofast.module.wms.dal.mysql.rtissue;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.rtissue.RtIssueDO;
import com.dofast.module.wms.dal.dataobject.rtissue.RtIssueTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.rtissue.vo.*;

/**
 * 生产退料单头 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RtIssueMapper extends BaseMapperX<RtIssueDO> {
    /**
     *
     * @param rtId
     * @return
     */
    public List<RtIssueTxBean> getTxBeans(Long rtId);

    default RtIssueDO checkUnique(RtIssueBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RtIssueDO>().eq(RtIssueDO::getRtCode,baseVO.getRtCode()));
    }
    default PageResult<RtIssueDO> selectPage(RtIssuePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RtIssueDO>()
                .eqIfPresent(RtIssueDO::getRtCode, reqVO.getRtCode())
                .likeIfPresent(RtIssueDO::getRtName, reqVO.getRtName())
                .eqIfPresent(RtIssueDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(RtIssueDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(RtIssueDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtIssueDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(RtIssueDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtIssueDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtIssueDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(RtIssueDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtIssueDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtIssueDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(RtIssueDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(RtIssueDO::getRtDate, reqVO.getRtDate())
                .eqIfPresent(RtIssueDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RtIssueDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtIssueDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtIssueDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtIssueDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtIssueDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RtIssueDO::getTaskCode, reqVO.getTaskCode())
                .betweenIfPresent(RtIssueDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RtIssueDO::getUpdater, reqVO.getUpdator())
                .orderByDesc(RtIssueDO::getId));
    }

    default List<RtIssueDO> selectList(RtIssueExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RtIssueDO>()
                .eqIfPresent(RtIssueDO::getRtCode, reqVO.getRtCode())
                .eqIfPresent(RtIssueDO::getRtName, reqVO.getRtName())
                .eqIfPresent(RtIssueDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(RtIssueDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(RtIssueDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtIssueDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(RtIssueDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtIssueDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtIssueDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(RtIssueDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtIssueDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtIssueDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(RtIssueDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(RtIssueDO::getRtDate, reqVO.getRtDate())
                .eqIfPresent(RtIssueDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RtIssueDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtIssueDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtIssueDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtIssueDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtIssueDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RtIssueDO::getTaskCode, reqVO.getTaskCode())
                .betweenIfPresent(RtIssueDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RtIssueDO::getUpdater, reqVO.getUpdator())
                .orderByDesc(RtIssueDO::getId));
    }

}
