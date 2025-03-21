package com.dofast.module.promotion.controller.app.activity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;


import com.dofast.framework.common.enums.CommonStatusEnum;



import com.dofast.framework.common.pojo.CommonResult;
import com.dofast.module.promotion.controller.app.activity.vo.AppActivityRespVO;
import com.dofast.module.promotion.dal.dataobject.bargain.BargainActivityDO;
import com.dofast.module.promotion.dal.dataobject.combination.CombinationActivityDO;


import com.dofast.module.promotion.dal.dataobject.seckill.SeckillActivityDO;

import com.dofast.module.promotion.enums.common.PromotionTypeEnum;
import com.dofast.module.promotion.service.bargain.BargainActivityService;
import com.dofast.module.promotion.service.combination.CombinationActivityService;
import com.dofast.module.promotion.service.seckill.SeckillActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


import java.util.ArrayList;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import static com.dofast.framework.common.pojo.CommonResult.success;

import java.time.LocalDateTime;
import java.util.*;

import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.common.util.collection.CollectionUtils.convertMultiMap;




@Tag(name = "用户 APP - 营销活动") // 用于提供跨多个活动的 HTTP 接口
@RestController
@RequestMapping("/promotion/activity")
@Validated
public class AppActivityController {

    @Resource
    private CombinationActivityService combinationActivityService;
    @Resource
    private SeckillActivityService seckillActivityService;
    @Resource
    private BargainActivityService bargainActivityService;

    @GetMapping("/list-by-spu-id")
    @Operation(summary = "获得单个商品，近期参与的每个活动")
    @Parameter(name = "spuId", description = "商品编号", required = true)
    public CommonResult<List<AppActivityRespVO>> getActivityListBySpuId(@RequestParam("spuId") Long spuId) {
        // 每种活动，只返回一个
        return success(getAppActivityList(Collections.singletonList(spuId)));
    }

    @GetMapping("/list-by-spu-ids")
    @Operation(summary = "获得多个商品，近期参与的每个活动")
    @Parameter(name = "spuIds", description = "商品编号数组", required = true)
    public CommonResult<Map<Long, List<AppActivityRespVO>>> getActivityListBySpuIds(@RequestParam("spuIds") List<Long> spuIds) {
        if (CollUtil.isEmpty(spuIds)) {
            return success(MapUtil.empty());
        }
        // 每种活动，只返回一个；key 为 SPU 编号
        return success(convertMultiMap(getAppActivityList(spuIds), AppActivityRespVO::getSpuId));
    }

    private List<AppActivityRespVO> getAppActivityList(Collection<Long> spuIds) {
        if (CollUtil.isEmpty(spuIds)) {
            return new ArrayList<>();
        }
        LocalDateTime now = LocalDateTime.now();
        List<AppActivityRespVO> activityList = new ArrayList<>();

        // 1. 拼团活动 - 获取开启的且开始的且没有结束的活动
        List<CombinationActivityDO> combinationActivities = combinationActivityService.getCombinationActivityBySpuIdsAndStatusAndDateTimeLt(
                spuIds, CommonStatusEnum.ENABLE.getStatus(), now);
        if (CollUtil.isNotEmpty(combinationActivities)) {
            combinationActivities.forEach(item -> {
                activityList.add(new AppActivityRespVO().setId(item.getId())
                        .setType(PromotionTypeEnum.COMBINATION_ACTIVITY.getType()).setName(item.getName())
                        .setSpuId(item.getSpuId()).setStartTime(item.getStartTime()).setEndTime(item.getEndTime()));
            });
        }

        // 2. 秒杀活动 - 获取开启的且开始的且没有结束的活动
        List<SeckillActivityDO> seckillActivities = seckillActivityService.getSeckillActivityBySpuIdsAndStatusAndDateTimeLt(
                spuIds, CommonStatusEnum.ENABLE.getStatus(), now);
        if (CollUtil.isNotEmpty(seckillActivities)) {
            seckillActivities.forEach(item -> {
                activityList.add(new AppActivityRespVO().setId(item.getId())
                        .setType(PromotionTypeEnum.SECKILL_ACTIVITY.getType()).setName(item.getName())
                        .setSpuId(item.getSpuId()).setStartTime(item.getStartTime()).setEndTime(item.getEndTime()));
            });
        }

        // 3. 砍价活动 - 获取开启的且开始的且没有结束的活动
        List<BargainActivityDO> bargainActivities = bargainActivityService.getBargainActivityBySpuIdsAndStatusAndDateTimeLt(
                spuIds, CommonStatusEnum.ENABLE.getStatus(), now);
        if (CollUtil.isNotEmpty(bargainActivities)) {
            bargainActivities.forEach(item -> {
                activityList.add(new AppActivityRespVO().setId(item.getId())
                        .setType(PromotionTypeEnum.BARGAIN_ACTIVITY.getType()).setName(item.getName())
                        .setSpuId(item.getSpuId()).setStartTime(item.getStartTime()).setEndTime(item.getEndTime()));
            });
        }
        return activityList;
    }


    private List<AppActivityRespVO> getAppActivityRespVOList(Long spuId) {
        List<AppActivityRespVO> respList = new ArrayList<>();
        CombinationActivityDO combination = combinationActivityService.getCombinationActivityBySpuId(spuId);
        if (combination != null) {
            respList.add(new AppActivityRespVO()
                    .setId(combination.getId())
                    .setType(PromotionTypeEnum.COMBINATION_ACTIVITY.getType())
                    .setName(combination.getName())
                    .setStartTime(combination.getStartTime())
                    .setEndTime(combination.getEndTime()));
        }
        SeckillActivityDO seckill = seckillActivityService.getSeckillActivityBySpuId(spuId);
        if (seckill != null) {
            respList.add(new AppActivityRespVO()
                    .setId(seckill.getId())
                    .setType(PromotionTypeEnum.SECKILL_ACTIVITY.getType())
                    .setName(seckill.getName())
                    .setStartTime(seckill.getStartTime())
                    .setEndTime(seckill.getEndTime()));
        }
        BargainActivityDO bargain = bargainActivityService.getBargainActivityBySpuId(spuId);
        if (bargain != null) {
            respList.add(new AppActivityRespVO()
                    .setId(bargain.getId())
                    .setType(PromotionTypeEnum.BARGAIN_ACTIVITY.getType())
                    .setName(bargain.getName())
                    .setStartTime(bargain.getStartTime())
                    .setEndTime(bargain.getEndTime()));
        }
        return respList;
    }

}
