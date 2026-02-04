package com.dofast.module.wms.dal.mysql.rtvendor;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.rtvendor.RtVendorDO;
import com.dofast.module.wms.dal.dataobject.rtvendor.RtVendorTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.rtvendor.vo.*;

/**
 * 供应商退货 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RtVendorMapper extends BaseMapperX<RtVendorDO> {
    public List<RtVendorTxBean> getTxBeans(Long rtId);
    /**
     * 检查供应商退货单编码是否唯一
     * @param baseVO
     * @return
     */
    default RtVendorDO checkCodeUnique(RtVendorBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RtVendorDO>().eq(RtVendorDO::getRtCode,baseVO.getRtCode()));
    }
    default PageResult<RtVendorDO> selectPage(RtVendorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RtVendorDO>()
                .eqIfPresent(RtVendorDO::getRtCode, reqVO.getRtCode())
                .likeIfPresent(RtVendorDO::getRtName, reqVO.getRtName())
                .eqIfPresent(RtVendorDO::getPoCode, reqVO.getPoCode())
                .eqIfPresent(RtVendorDO::getVendorId, reqVO.getVendorId())
                .eqIfPresent(RtVendorDO::getVendorCode, reqVO.getVendorCode())
                .likeIfPresent(RtVendorDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(RtVendorDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(RtVendorDO::getBatchCode, reqVO.getBatchCode())
                .betweenIfPresent(RtVendorDO::getRtDate, reqVO.getRtDate())
                .eqIfPresent(RtVendorDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RtVendorDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtVendorDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtVendorDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtVendorDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtVendorDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtVendorDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RtVendorDO::getId));
    }

    default List<RtVendorDO> selectList(RtVendorExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RtVendorDO>()
                .eqIfPresent(RtVendorDO::getRtCode, reqVO.getRtCode())
                .eqIfPresent(RtVendorDO::getRtName, reqVO.getRtName())
                .eqIfPresent(RtVendorDO::getPoCode, reqVO.getPoCode())
                .eqIfPresent(RtVendorDO::getVendorId, reqVO.getVendorId())
                .eqIfPresent(RtVendorDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(RtVendorDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(RtVendorDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(RtVendorDO::getBatchCode, reqVO.getBatchCode())
                .betweenIfPresent(RtVendorDO::getRtDate, reqVO.getRtDate())
                .eqIfPresent(RtVendorDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RtVendorDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtVendorDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtVendorDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtVendorDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtVendorDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtVendorDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RtVendorDO::getId));
    }

}
