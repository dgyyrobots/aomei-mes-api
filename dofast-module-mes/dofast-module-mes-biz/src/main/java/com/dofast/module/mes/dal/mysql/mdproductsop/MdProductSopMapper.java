package com.dofast.module.mes.dal.mysql.mdproductsop;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdproductsop.MdProductSopDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdproductsop.vo.*;

/**
 * 产品SOP Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdProductSopMapper extends BaseMapperX<MdProductSopDO> {

    default PageResult<MdProductSopDO> selectPage(MdProductSopPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdProductSopDO>()
                .eqIfPresent(MdProductSopDO::getItemId, reqVO.getItemId())
                .eqIfPresent(MdProductSopDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(MdProductSopDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(MdProductSopDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(MdProductSopDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(MdProductSopDO::getSopTitle, reqVO.getSopTitle())
                .eqIfPresent(MdProductSopDO::getSopDescription, reqVO.getSopDescription())
                .eqIfPresent(MdProductSopDO::getSopUrl, reqVO.getSopUrl())
                .eqIfPresent(MdProductSopDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdProductSopDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdProductSopDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdProductSopDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdProductSopDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdProductSopDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdProductSopDO::getId));
    }

    default List<MdProductSopDO> selectList(MdProductSopExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdProductSopDO>()
                .eqIfPresent(MdProductSopDO::getItemId, reqVO.getItemId())
                .eqIfPresent(MdProductSopDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(MdProductSopDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(MdProductSopDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(MdProductSopDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(MdProductSopDO::getSopTitle, reqVO.getSopTitle())
                .eqIfPresent(MdProductSopDO::getSopDescription, reqVO.getSopDescription())
                .eqIfPresent(MdProductSopDO::getSopUrl, reqVO.getSopUrl())
                .eqIfPresent(MdProductSopDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdProductSopDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdProductSopDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdProductSopDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdProductSopDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdProductSopDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdProductSopDO::getId));
    }

}
