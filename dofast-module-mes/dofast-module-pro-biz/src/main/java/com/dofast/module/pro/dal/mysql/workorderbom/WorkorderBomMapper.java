package com.dofast.module.pro.dal.mysql.workorderbom;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.workorderbom.WorkorderBomDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.workorderbom.vo.*;

/**
 * 生产工单BOM组成 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WorkorderBomMapper extends BaseMapperX<WorkorderBomDO> {

    default int deleteProWorkorderBomByWorkorderId(Long workid){
       return delete(new LambdaQueryWrapperX<WorkorderBomDO>().eq(WorkorderBomDO::getWorkorderId,workid));
    }
    default PageResult<WorkorderBomDO> selectPage(WorkorderBomPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WorkorderBomDO>()
                .eqIfPresent(WorkorderBomDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(WorkorderBomDO::getItemId, reqVO.getItemId())
                .eqIfPresent(WorkorderBomDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(WorkorderBomDO::getItemName, reqVO.getItemName())
                .eqIfPresent(WorkorderBomDO::getItemSpc, reqVO.getItemSpc())
                .eqIfPresent(WorkorderBomDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(WorkorderBomDO::getItemOrProduct, reqVO.getItemOrProduct())
                .eqIfPresent(WorkorderBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(WorkorderBomDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WorkorderBomDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WorkorderBomDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WorkorderBomDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WorkorderBomDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(WorkorderBomDO::getSequence, reqVO.getSequence())
                .eqIfPresent(WorkorderBomDO::getSequenceOrder, reqVO.getSequenceOrder())
                .betweenIfPresent(WorkorderBomDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WorkorderBomDO::getId));
    }

    default List<WorkorderBomDO> selectList(WorkorderBomExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WorkorderBomDO>()
                .eqIfPresent(WorkorderBomDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(WorkorderBomDO::getItemId, reqVO.getItemId())
                .eqIfPresent(WorkorderBomDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(WorkorderBomDO::getItemName, reqVO.getItemName())
                .eqIfPresent(WorkorderBomDO::getItemSpc, reqVO.getItemSpc())
                .eqIfPresent(WorkorderBomDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(WorkorderBomDO::getItemOrProduct, reqVO.getItemOrProduct())
                .eqIfPresent(WorkorderBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(WorkorderBomDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WorkorderBomDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WorkorderBomDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WorkorderBomDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WorkorderBomDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(WorkorderBomDO::getSequence, reqVO.getSequence())
                .eqIfPresent(WorkorderBomDO::getSequenceOrder, reqVO.getSequenceOrder())
                .betweenIfPresent(WorkorderBomDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WorkorderBomDO::getId));
    }

}
