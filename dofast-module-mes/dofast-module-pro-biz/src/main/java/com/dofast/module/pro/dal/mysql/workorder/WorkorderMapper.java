package com.dofast.module.pro.dal.mysql.workorder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.workorder.vo.*;

/**
 * 生产工单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WorkorderMapper extends BaseMapperX<WorkorderDO> {
    default WorkorderDO checkWorkorderCodeUnique(WorkorderBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<WorkorderDO>().eq(WorkorderDO::getWorkorderCode,baseVO.getWorkorderCode()));
    }

    default PageResult<WorkorderDO> selectPage(WorkorderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WorkorderDO>()
                .likeIfPresent(WorkorderDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(WorkorderDO::getWorkorderName, reqVO.getWorkorderName())
                //.eqIfPresent(WorkorderDO::getOrderSource, reqVO.getOrderSource())
                .eqIfPresent(WorkorderDO::getSourceCode, reqVO.getSourceCode())
                .eqIfPresent(WorkorderDO::getProductId, reqVO.getProductId())
                .eqIfPresent(WorkorderDO::getProductCode, reqVO.getProductCode())
                .likeIfPresent(WorkorderDO::getProductName, reqVO.getProductName())
                .eqIfPresent(WorkorderDO::getProductSpc, reqVO.getProductSpc())
                .eqIfPresent(WorkorderDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(WorkorderDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(WorkorderDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(WorkorderDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(WorkorderDO::getQuantityScheduled, reqVO.getQuantityScheduled())
                .eqIfPresent(WorkorderDO::getClientId, reqVO.getClientId())
                .eqIfPresent(WorkorderDO::getClientCode, reqVO.getClientCode())
                .likeIfPresent(WorkorderDO::getClientName, reqVO.getClientName())
                .eqIfPresent(WorkorderDO::getBatchCode, reqVO.getBatchCode())
                .betweenIfPresent(WorkorderDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(WorkorderDO::getParentId, 0)
                .eqIfPresent(WorkorderDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(WorkorderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WorkorderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WorkorderDO::getIsOut, reqVO.getIsOut())
                .eqIfPresent(WorkorderDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WorkorderDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WorkorderDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WorkorderDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(WorkorderDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(WorkorderDO::getAdjuncts, reqVO.getAdjuncts())
                .eqIfPresent(WorkorderDO::getMixinOrderId, reqVO.getMixinOrderId())
                .notInIfPresent(WorkorderDO::getOrderSource, Collections.singleton("4"))
                .eqIfPresent(WorkorderDO::getCloseFlag, reqVO.getCloseFlag())
                .inIfPresent(WorkorderDO::getWorkorderCode, reqVO.getWorkorderList())
                .orderByDesc(WorkorderDO::getId));
    }
    default List<WorkorderDO> selectList(WorkorderListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WorkorderDO>()
                .eqIfPresent(WorkorderDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(WorkorderDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(WorkorderDO::getOrderSource, reqVO.getOrderSource())
                .eqIfPresent(WorkorderDO::getSourceCode, reqVO.getSourceCode())
                .eqIfPresent(WorkorderDO::getProductId, reqVO.getProductId())
                .eqIfPresent(WorkorderDO::getProductCode, reqVO.getProductCode())
                .eqIfPresent(WorkorderDO::getProductName, reqVO.getProductName())
                .eqIfPresent(WorkorderDO::getProductSpc, reqVO.getProductSpc())
                .eqIfPresent(WorkorderDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(WorkorderDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(WorkorderDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(WorkorderDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(WorkorderDO::getQuantityScheduled, reqVO.getQuantityScheduled())
                .eqIfPresent(WorkorderDO::getClientId, reqVO.getClientId())
                .eqIfPresent(WorkorderDO::getClientCode, reqVO.getClientCode())
                .eqIfPresent(WorkorderDO::getClientName, reqVO.getClientName())
                .eqIfPresent(WorkorderDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(WorkorderDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(WorkorderDO::getParentId, reqVO.getParentId())
                .eqIfPresent(WorkorderDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(WorkorderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WorkorderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WorkorderDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WorkorderDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WorkorderDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WorkorderDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(WorkorderDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(WorkorderDO::getAdjuncts, reqVO.getAdjuncts())
                .eqIfPresent(WorkorderDO::getCloseFlag, reqVO.getCloseFlag())
                .eqIfPresent(WorkorderDO::getIsOut, reqVO.getIsOut())
                .orderByDesc(WorkorderDO::getId));
    }


    default List<WorkorderDO> selectList(WorkorderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WorkorderDO>()
                .eqIfPresent(WorkorderDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(WorkorderDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(WorkorderDO::getOrderSource, reqVO.getOrderSource())
                .eqIfPresent(WorkorderDO::getSourceCode, reqVO.getSourceCode())
                .eqIfPresent(WorkorderDO::getProductId, reqVO.getProductId())
                .eqIfPresent(WorkorderDO::getProductCode, reqVO.getProductCode())
                .eqIfPresent(WorkorderDO::getProductName, reqVO.getProductName())
                .eqIfPresent(WorkorderDO::getProductSpc, reqVO.getProductSpc())
                .eqIfPresent(WorkorderDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(WorkorderDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(WorkorderDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(WorkorderDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(WorkorderDO::getQuantityScheduled, reqVO.getQuantityScheduled())
                .eqIfPresent(WorkorderDO::getClientId, reqVO.getClientId())
                .eqIfPresent(WorkorderDO::getClientCode, reqVO.getClientCode())
                .eqIfPresent(WorkorderDO::getClientName, reqVO.getClientName())
                .eqIfPresent(WorkorderDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(WorkorderDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(WorkorderDO::getParentId, reqVO.getParentId())
                .eqIfPresent(WorkorderDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(WorkorderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WorkorderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WorkorderDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WorkorderDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WorkorderDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WorkorderDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(WorkorderDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(WorkorderDO::getAdjuncts, reqVO.getAdjuncts())
                .eqIfPresent(WorkorderDO::getIsOut, reqVO.getIsOut())
                .eqIfPresent(WorkorderDO::getCloseFlag, reqVO.getCloseFlag())
                .eqIfPresent(WorkorderDO::getMixinOrderId, reqVO.getMixinOrderId())
                .orderByDesc(WorkorderDO::getId));
    }


    default Long selectCount(WorkorderPageReqVO reqVO) {
        return selectCount(new LambdaQueryWrapperX<WorkorderDO>()
                .eqIfPresent(WorkorderDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(WorkorderDO::getWorkorderName, reqVO.getWorkorderName())
                //.eqIfPresent(WorkorderDO::getOrderSource, reqVO.getOrderSource())
                .eqIfPresent(WorkorderDO::getSourceCode, reqVO.getSourceCode())
                .eqIfPresent(WorkorderDO::getProductId, reqVO.getProductId())
                .eqIfPresent(WorkorderDO::getProductCode, reqVO.getProductCode())
                .eqIfPresent(WorkorderDO::getProductName, reqVO.getProductName())
                .eqIfPresent(WorkorderDO::getProductSpc, reqVO.getProductSpc())
                .eqIfPresent(WorkorderDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(WorkorderDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(WorkorderDO::getQuantityProduced, reqVO.getQuantityProduced())
                .eqIfPresent(WorkorderDO::getQuantityChanged, reqVO.getQuantityChanged())
                .eqIfPresent(WorkorderDO::getQuantityScheduled, reqVO.getQuantityScheduled())
                .eqIfPresent(WorkorderDO::getClientId, reqVO.getClientId())
                .eqIfPresent(WorkorderDO::getClientCode, reqVO.getClientCode())
                .eqIfPresent(WorkorderDO::getClientName, reqVO.getClientName())
                .eqIfPresent(WorkorderDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(WorkorderDO::getRequestDate, reqVO.getRequestDate())
                .eqIfPresent(WorkorderDO::getParentId, 0)
                .eqIfPresent(WorkorderDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(WorkorderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WorkorderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WorkorderDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WorkorderDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WorkorderDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WorkorderDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(WorkorderDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(WorkorderDO::getAdjuncts, reqVO.getAdjuncts())
                .eqIfPresent(WorkorderDO::getIsOut, reqVO.getIsOut())
                .eqIfPresent(WorkorderDO::getCloseFlag, reqVO.getCloseFlag())
                .eqIfPresent(WorkorderDO::getMixinOrderId, reqVO.getMixinOrderId())
                .notInIfPresent(WorkorderDO::getOrderSource, Collections.singleton("4")));
    }


    default Map<String, Integer> getCountMonthWorkorderLastYear() {
        QueryWrapper<WorkorderDO> wrapper = new QueryWrapper<>();
        wrapper.select("DATE_FORMAT(create_time, '%Y-%m') AS month", "SUM(quantity_produced) AS total")
                .groupBy("month")
                .ge("create_time", LocalDate.now().minusYears(1).withDayOfYear(1)) // 上一年第一天
                .lt("create_time", LocalDate.now().withDayOfYear(1)); // 今年第一天

        List<Map<String, Object>> result = selectMaps(wrapper);

        Map<String, Integer> resultMap = new HashMap<>();
        if (result == null || result.isEmpty()) return resultMap;
        for (Map<String, Object> map : result) {
            String month = (String) map.get("month");
            Double total = (Double) map.get("total");
            resultMap.put(month, total != null ? total.intValue() : 0);
        }
        return resultMap;
    }


    default Map<String, Integer> getCountMonthWorkorderThisYear() {
        QueryWrapper<WorkorderDO> wrapper = new QueryWrapper<>();
        wrapper.select("DATE_FORMAT(create_time, '%Y-%m') AS month", "SUM(quantity_produced) AS total")
                .groupBy("month")
                // 时间范围：今年第一天 ~ 明年第一天（确保覆盖全年）
                .ge("create_time", LocalDate.now().withDayOfYear(1))          // 今年第一天
                .lt("create_time", LocalDate.now().plusYears(1).withDayOfYear(1)); // 明年第一天

        List<Map<String, Object>> result = selectMaps(wrapper);

        Map<String, Integer> resultMap = new HashMap<>();
        if (result == null || result.isEmpty()) {
            return resultMap;
        }
        for (Map<String, Object> map : result) {
            String month = (String) map.get("month");
            Double total = (Double) map.get("total");
            resultMap.put(month, total != null ? total.intValue() : 0);
        }
        return resultMap;
    }

}
