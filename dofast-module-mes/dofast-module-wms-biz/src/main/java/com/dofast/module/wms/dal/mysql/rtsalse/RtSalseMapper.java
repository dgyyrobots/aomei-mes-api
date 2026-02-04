package com.dofast.module.wms.dal.mysql.rtsalse;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.rtsalse.RtSalseDO;
import com.dofast.module.wms.dal.dataobject.rtsalse.RtSalseTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.rtsalse.vo.*;

/**
 * 产品销售退货单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RtSalseMapper extends BaseMapperX<RtSalseDO> {
    List<RtSalseTxBean> getTxBeans(Long rtId);
    default RtSalseDO checkUnique(RtSalseBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RtSalseDO>().eq(RtSalseDO::getRtCode,baseVO.getRtCode()));
    }
    default PageResult<RtSalseDO> selectPage(RtSalsePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RtSalseDO>()
                .eqIfPresent(RtSalseDO::getRtCode, reqVO.getRtCode())
                .likeIfPresent(RtSalseDO::getRtName, reqVO.getRtName())
                .eqIfPresent(RtSalseDO::getSoCode, reqVO.getSoCode())
                .eqIfPresent(RtSalseDO::getClientId, reqVO.getClientId())
                .eqIfPresent(RtSalseDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(RtSalseDO::getClientName, reqVO.getClientName())
                .eqIfPresent(RtSalseDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(RtSalseDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtSalseDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(RtSalseDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtSalseDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtSalseDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(RtSalseDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtSalseDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtSalseDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(RtSalseDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(RtSalseDO::getRtDate, reqVO.getRtDate())
                .eqIfPresent(RtSalseDO::getRtReason, reqVO.getRtReason())
                .eqIfPresent(RtSalseDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RtSalseDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtSalseDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtSalseDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtSalseDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtSalseDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtSalseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RtSalseDO::getId));
    }

    default List<RtSalseDO> selectList(RtSalseExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RtSalseDO>()
                .eqIfPresent(RtSalseDO::getRtCode, reqVO.getRtCode())
                .eqIfPresent(RtSalseDO::getRtName, reqVO.getRtName())
                .eqIfPresent(RtSalseDO::getSoCode, reqVO.getSoCode())
                .eqIfPresent(RtSalseDO::getClientId, reqVO.getClientId())
                .eqIfPresent(RtSalseDO::getClientCode, reqVO.getClientCode())
                .eqIfPresent(RtSalseDO::getClientName, reqVO.getClientName())
                .eqIfPresent(RtSalseDO::getClientNick, reqVO.getClientNick())
                .eqIfPresent(RtSalseDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtSalseDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(RtSalseDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtSalseDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtSalseDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(RtSalseDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtSalseDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtSalseDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(RtSalseDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(RtSalseDO::getRtDate, reqVO.getRtDate())
                .eqIfPresent(RtSalseDO::getRtReason, reqVO.getRtReason())
                .eqIfPresent(RtSalseDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RtSalseDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtSalseDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtSalseDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtSalseDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtSalseDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtSalseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RtSalseDO::getId));
    }

}
