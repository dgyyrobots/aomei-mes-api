package com.dofast.module.promotion.dal.mysql.combination;

import com.dofast.framework.common.pojo.PageParam;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.module.promotion.controller.admin.combination.vo.activity.CombinationActivityPageReqVO;
import com.dofast.module.promotion.dal.dataobject.combination.CombinationActivityDO;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 拼团活动 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface CombinationActivityMapper extends BaseMapperX<CombinationActivityDO> {

    default PageResult<CombinationActivityDO> selectPage(CombinationActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CombinationActivityDO>()
                .likeIfPresent(CombinationActivityDO::getName, reqVO.getName())
                .eqIfPresent(CombinationActivityDO::getStatus, reqVO.getStatus())
                .orderByDesc(CombinationActivityDO::getId));
    }

    default List<CombinationActivityDO> selectListByStatus(Integer status) {
        return selectList(CombinationActivityDO::getStatus, status);
    }

    default PageResult<CombinationActivityDO> selectPage(PageParam pageParam, Integer status) {
        return selectPage(pageParam, new LambdaQueryWrapperX<CombinationActivityDO>()
                .eq(CombinationActivityDO::getStatus, status));
    }

    default List<CombinationActivityDO> selectListByStatus(Integer status, Integer count) {
        return selectList(new LambdaQueryWrapperX<CombinationActivityDO>()
                .eq(CombinationActivityDO::getStatus, status)
                .last("LIMIT " + count));
    }




    /**
     * 查询出指定 spuId 的 spu 参加的活动最接近现在的一条记录。多个的话，一个 spuId 对应一个最近的活动编号
     * @param spuIds spu 编号
     * @param status 状态
     * @return 包含 spuId 和 activityId 的 map 对象列表
     */
    default List<Map<String, Object>> selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(@Param("spuIds") Collection<Long> spuIds, @Param("status") Integer status) {
        return selectMaps(new QueryWrapper<CombinationActivityDO>()
                .select("spu_id AS spuId, MAX(DISTINCT(id)) AS activityId") // 时间越大 id 也越大 直接用 id
                .in("spu_id", spuIds)
                .eq("status", status)
                .groupBy("spu_id"));
    }

    /**
     * 获取指定活动编号的活动列表且
     * 开始时间和结束时间小于给定时间 dateTime 的活动列表
     *
     * @param ids      活动编号
     * @param dateTime 指定日期
     * @return 活动列表
     */
    default List<CombinationActivityDO> selectListByIdsAndDateTimeLt(Collection<Long> ids, LocalDateTime dateTime) {
        return selectList(new LambdaQueryWrapperX<CombinationActivityDO>()
                .in(CombinationActivityDO::getId, ids)
                .lt(CombinationActivityDO::getStartTime, dateTime)
                .gt(CombinationActivityDO::getEndTime, dateTime));// 开始时间 < 指定时间 < 结束时间，也就是说获取指定时间段的活动
    }

    default CombinationActivityDO selectOne(Long spuId) {
        return selectOne(new LambdaQueryWrapperX<CombinationActivityDO>()
                .eq(CombinationActivityDO::getSpuId, spuId)

                .orderByDesc(CombinationActivityDO::getCreateTime));
    }

}
