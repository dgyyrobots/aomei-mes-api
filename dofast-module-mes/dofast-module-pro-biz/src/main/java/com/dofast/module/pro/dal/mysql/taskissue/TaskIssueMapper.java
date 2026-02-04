package com.dofast.module.pro.dal.mysql.taskissue;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.taskissue.TaskIssueDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.taskissue.vo.*;

/**
 * 生产任务投料 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TaskIssueMapper extends BaseMapperX<TaskIssueDO> {
    default TaskIssueDO checkUnique(TaskIssueBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<TaskIssueDO>()
                .eq(TaskIssueDO::getWorkorderId,baseVO.getWorkorderId())
                .eq(TaskIssueDO::getTaskId,baseVO.getTaskId())
                .eq(TaskIssueDO::getSourceDocType,baseVO.getSourceDocType())
                .eq(TaskIssueDO::getSourceLineId,baseVO.getSourceDocId())
        );
    }

    default PageResult<TaskIssueDO> selectPage(TaskIssuePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskIssueDO>()
                .eqIfPresent(TaskIssueDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(TaskIssueDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskIssueDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskIssueDO::getSourceDocId, reqVO.getSourceDocId())
                .eqIfPresent(TaskIssueDO::getSourceDocCode, reqVO.getSourceDocCode())
                .eqIfPresent(TaskIssueDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(TaskIssueDO::getSourceLineId, reqVO.getSourceLineId())
                .eqIfPresent(TaskIssueDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskIssueDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TaskIssueDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskIssueDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskIssueDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskIssueDO::getQuantityIssued, reqVO.getQuantityIssued())
                .eqIfPresent(TaskIssueDO::getQuantityAvailable, reqVO.getQuantityAvailable())
                .eqIfPresent(TaskIssueDO::getQuantityUsed, reqVO.getQuantityUsed())
                .eqIfPresent(TaskIssueDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskIssueDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TaskIssueDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskIssueDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskIssueDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskIssueDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskIssueDO::getId));
    }

    default List<TaskIssueDO> selectList(TaskIssueExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TaskIssueDO>()
                .eqIfPresent(TaskIssueDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(TaskIssueDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TaskIssueDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(TaskIssueDO::getSourceDocId, reqVO.getSourceDocId())
                .eqIfPresent(TaskIssueDO::getSourceDocCode, reqVO.getSourceDocCode())
                .eqIfPresent(TaskIssueDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(TaskIssueDO::getSourceLineId, reqVO.getSourceLineId())
                .eqIfPresent(TaskIssueDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TaskIssueDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(TaskIssueDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TaskIssueDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TaskIssueDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TaskIssueDO::getQuantityIssued, reqVO.getQuantityIssued())
                .eqIfPresent(TaskIssueDO::getQuantityAvailable, reqVO.getQuantityAvailable())
                .eqIfPresent(TaskIssueDO::getQuantityUsed, reqVO.getQuantityUsed())
                .eqIfPresent(TaskIssueDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TaskIssueDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TaskIssueDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TaskIssueDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TaskIssueDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TaskIssueDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskIssueDO::getId));
    }

}
