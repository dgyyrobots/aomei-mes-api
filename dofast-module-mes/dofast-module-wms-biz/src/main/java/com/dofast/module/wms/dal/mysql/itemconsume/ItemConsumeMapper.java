package com.dofast.module.wms.dal.mysql.itemconsume;

import java.time.LocalDateTime;
import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.itemconsume.ItemConsumeDO;
import com.dofast.module.wms.dal.dataobject.itemconsume.ItemConsumeTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.itemconsume.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 物料消耗记录 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ItemConsumeMapper extends BaseMapperX<ItemConsumeDO> {

    default PageResult<ItemConsumeDO> selectPage(ItemConsumePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ItemConsumeDO>()
                .eqIfPresent(ItemConsumeDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ItemConsumeDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(ItemConsumeDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ItemConsumeDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(ItemConsumeDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(ItemConsumeDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(ItemConsumeDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(ItemConsumeDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(ItemConsumeDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(ItemConsumeDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(ItemConsumeDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(ItemConsumeDO::getProcessName, reqVO.getProcessName())
                .betweenIfPresent(ItemConsumeDO::getConsumeDate, reqVO.getConsumeDate())
                .eqIfPresent(ItemConsumeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ItemConsumeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ItemConsumeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ItemConsumeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ItemConsumeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ItemConsumeDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ItemConsumeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemConsumeDO::getId));
    }

    default List<ItemConsumeDO> selectList(ItemConsumeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ItemConsumeDO>()
                .eqIfPresent(ItemConsumeDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ItemConsumeDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(ItemConsumeDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ItemConsumeDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(ItemConsumeDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(ItemConsumeDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(ItemConsumeDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(ItemConsumeDO::getWorkstationCode, reqVO.getWorkstationCode())
                .eqIfPresent(ItemConsumeDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(ItemConsumeDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(ItemConsumeDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(ItemConsumeDO::getProcessName, reqVO.getProcessName())
                .betweenIfPresent(ItemConsumeDO::getConsumeDate, reqVO.getConsumeDate())
                .eqIfPresent(ItemConsumeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ItemConsumeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ItemConsumeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ItemConsumeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ItemConsumeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ItemConsumeDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ItemConsumeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemConsumeDO::getId));
    }

    public List<ItemConsumeTxBean> getTxBeans(Long recordId);

    public List<Map<String, Object>> selectWeeklyConsume(@Param("currentWeekStart") LocalDateTime currentWeekStart,
                                                         @Param("currentWeekEnd") LocalDateTime currentWeekEnd,
                                                         @Param("lastWeekStart") LocalDateTime lastWeekStart,
                                                         @Param("lastWeekEnd") LocalDateTime lastWeekEnd);
}
