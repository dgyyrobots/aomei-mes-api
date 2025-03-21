package com.dofast.module.promotion.controller.app.coupon;

import cn.hutool.core.collection.CollUtil;
import com.dofast.framework.common.pojo.CommonResult;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.security.core.annotations.PreAuthenticated;
import com.dofast.module.promotion.controller.app.coupon.vo.coupon.*;
import com.dofast.module.promotion.convert.coupon.CouponConvert;
import com.dofast.module.promotion.dal.dataobject.coupon.CouponDO;
import com.dofast.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import com.dofast.module.promotion.enums.coupon.CouponTakeTypeEnum;
import com.dofast.module.promotion.service.coupon.CouponService;
import com.dofast.module.promotion.service.coupon.CouponTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 优惠劵")
@RestController
@RequestMapping("/promotion/coupon")
@Validated
public class AppCouponController {

    @Resource
    private CouponService couponService;
    @Resource
    private CouponTemplateService couponTemplateService;

    @PostMapping("/take")
    @Operation(summary = "领取优惠劵")
    @Parameter(name = "templateId", description = "优惠券模板编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<Boolean> takeCoupon(@Valid @RequestBody AppCouponTakeReqVO reqVO) {
        // 1. 领取优惠劵
        Long userId = getLoginUserId();
        couponService.takeCoupon(reqVO.getTemplateId(), CollUtil.newHashSet(userId), CouponTakeTypeEnum.USER);

        // 2. 检查是否可以继续领取
        CouponTemplateDO couponTemplate = couponTemplateService.getCouponTemplate(reqVO.getTemplateId());
        boolean canTakeAgain = true;
        if (couponTemplate.getTakeLimitCount() != null && couponTemplate.getTakeLimitCount() > 0) {
            Integer takeCount = couponService.getTakeCount(reqVO.getTemplateId(), userId);
            canTakeAgain = takeCount < couponTemplate.getTakeLimitCount();
        }
        return success(canTakeAgain);
    }

    @GetMapping("/match-list")
    @Operation(summary = "获得匹配指定商品的优惠劵列表", description = "用于下单页，展示优惠劵列表")
    public CommonResult<List<AppCouponMatchRespVO>> getMatchCouponList(AppCouponMatchReqVO matchReqVO) {
        // todo: 优化：优惠金额倒序
        return success(CouponConvert.INSTANCE.convertList(couponService.getMatchCouponList(getLoginUserId(), matchReqVO)));
    }

    @GetMapping("/page")
    @Operation(summary = "我的优惠劵列表")
    @PreAuthenticated
    public CommonResult<PageResult<AppCouponRespVO>> getCouponPage(AppCouponPageReqVO pageReqVO) {
        PageResult<CouponDO> pageResult = couponService.getCouponPage(
                CouponConvert.INSTANCE.convert(pageReqVO, Collections.singleton(getLoginUserId())));
        return success(CouponConvert.INSTANCE.convertAppPage(pageResult));
    }

    @GetMapping(value = "/get-unused-count")
    @Operation(summary = "获得未使用的优惠劵数量")
    @PreAuthenticated
    public CommonResult<Long> getUnusedCouponCount() {
        return success(couponService.getUnusedCouponCount(getLoginUserId()));
    }

}
