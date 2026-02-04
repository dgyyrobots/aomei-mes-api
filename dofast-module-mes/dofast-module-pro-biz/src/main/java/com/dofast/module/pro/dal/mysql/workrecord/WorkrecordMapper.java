package com.dofast.module.pro.dal.mysql.workrecord;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.workrecord.WorkrecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.workrecord.vo.*;

/**
 * 上下工记录 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface WorkrecordMapper extends BaseMapperX<WorkrecordDO> {

    default PageResult<WorkrecordDO> selectPage(WorkrecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WorkrecordDO>()
                .eqIfPresent(WorkrecordDO::getUserId, reqVO.getUserId())
                .likeIfPresent(WorkrecordDO::getUserName, reqVO.getUserName())
                .likeIfPresent(WorkrecordDO::getNickName, String.valueOf(reqVO.getNickName()))
                .eqIfPresent(WorkrecordDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(WorkrecordDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(WorkrecordDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(WorkrecordDO::getOperationFlag, reqVO.getOperationFlag())
                .betweenIfPresent(WorkrecordDO::getOperationTime, reqVO.getOperationTime())
                .eqIfPresent(WorkrecordDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WorkrecordDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WorkrecordDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WorkrecordDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WorkrecordDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(WorkrecordDO::getCreateBy, reqVO.getCreateBy())
                .betweenIfPresent(WorkrecordDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(WorkrecordDO::getUpdateBy, reqVO.getUpdateBy())
                .orderByDesc(WorkrecordDO::getRecordId));
    }

    default List<WorkrecordDO> selectList(WorkrecordExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WorkrecordDO>()
                .eqIfPresent(WorkrecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(WorkrecordDO::getUserName, reqVO.getUserName())
                .eqIfPresent(WorkrecordDO::getNickName, String.valueOf(reqVO.getNickName()))
                .eqIfPresent(WorkrecordDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(WorkrecordDO::getWorkstationCode, reqVO.getWorkstationCode())
                .eqIfPresent(WorkrecordDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(WorkrecordDO::getOperationFlag, reqVO.getOperationFlag())
                .betweenIfPresent(WorkrecordDO::getOperationTime, reqVO.getOperationTime())
                .eqIfPresent(WorkrecordDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WorkrecordDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WorkrecordDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WorkrecordDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WorkrecordDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(WorkrecordDO::getCreateBy, reqVO.getCreateBy())
                .betweenIfPresent(WorkrecordDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(WorkrecordDO::getUpdateBy, reqVO.getUpdateBy())
                .orderByDesc(WorkrecordDO::getRecordId));
    }

}
