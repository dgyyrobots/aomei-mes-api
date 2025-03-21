package com.dofast.module.promotion.service.discount;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.util.collection.CollectionUtils;
import com.dofast.framework.common.enums.CommonStatusEnum;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.promotion.controller.admin.discount.vo.DiscountActivityBaseVO;
import com.dofast.module.promotion.controller.admin.discount.vo.DiscountActivityCreateReqVO;
import com.dofast.module.promotion.controller.admin.discount.vo.DiscountActivityPageReqVO;
import com.dofast.module.promotion.controller.admin.discount.vo.DiscountActivityUpdateReqVO;
import com.dofast.module.promotion.convert.discount.DiscountActivityConvert;
import com.dofast.module.promotion.dal.dataobject.discount.DiscountActivityDO;
import com.dofast.module.promotion.dal.dataobject.discount.DiscountProductDO;
import com.dofast.module.promotion.dal.mysql.discount.DiscountActivityMapper;
import com.dofast.module.promotion.dal.mysql.discount.DiscountProductMapper;
import com.dofast.module.promotion.enums.common.PromotionActivityStatusEnum;

import com.dofast.module.promotion.service.discount.bo.DiscountProductDetailBO;

import com.dofast.module.promotion.util.PromotionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;




import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.framework.common.util.collection.CollectionUtils.*;
import java.util.stream.Collectors;
import static com.dofast.framework.common.util.collection.CollectionUtils.convertList;

import static com.dofast.module.promotion.enums.ErrorCodeConstants.*;

/**
 * 限时折扣 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DiscountActivityServiceImpl implements DiscountActivityService {

    @Resource
    private DiscountActivityMapper discountActivityMapper;
    @Resource
    private DiscountProductMapper discountProductMapper;

    @Override
    public List<DiscountProductDO> getMatchDiscountProductList(Collection<Long> skuIds) {

        // TODO @zhangshuai：这里是不是可以直接 return discountProductMapper.getMatchDiscountProductList(skuIds)； 一般来说，如果 idea 报“黄色”的警告，尽量都处理下哈；原则是，一切警告，皆为异常（错误），这样可以写出更好的代码。
        List<DiscountProductDO> matchDiscountProductList = discountProductMapper.getMatchDiscountProductList(skuIds);
        return matchDiscountProductList;


    }

    @Override
    public Map<Long, DiscountProductDetailBO> getMatchDiscountProducts(Collection<Long> skuIds) {
        List<DiscountProductDetailBO> discountProducts = getRewardProductListBySkuIds(skuIds, singleton(PromotionActivityStatusEnum.RUN.getStatus()));
        return convertMap(discountProducts, DiscountProductDetailBO::getSkuId);
    }

    private List<DiscountProductDetailBO> getRewardProductListBySkuIds(Collection<Long> skuIds,
                                                                       Collection<Integer> statuses) {
        // 查询商品
        List<DiscountProductDO> products = discountProductMapper.selectListBySkuId(skuIds);
        if (CollUtil.isEmpty(products)) {
            return new ArrayList<>(0);
        }

        // 查询活动
        List<DiscountActivityDO> activities = discountActivityMapper.selectBatchIds(skuIds);
        activities.removeIf(activity -> !statuses.contains(activity.getStatus())); // 移除不满足 statuses 状态的
        Map<Long, DiscountActivityDO> activityMap = CollectionUtils.convertMap(activities, DiscountActivityDO::getId);

        // 移除不满足活动的商品
        products.removeIf(product -> !activityMap.containsKey(product.getActivityId()));

        return DiscountActivityConvert.INSTANCE.convertList1(products, activityMap);

    }

    @Override
    public Long createDiscountActivity(DiscountActivityCreateReqVO createReqVO) {
        // 校验商品是否冲突
        validateDiscountActivityProductConflicts(null, createReqVO.getProducts());

        // 插入活动
        DiscountActivityDO discountActivity = DiscountActivityConvert.INSTANCE.convert(createReqVO)

                // TODO @zhangshuai：这里的调用去掉哈，强制就是开启的；


                .setStatus(PromotionUtils.calculateActivityStatus(createReqVO.getEndTime()));
        discountActivityMapper.insert(discountActivity);
        // 插入商品
        // TODO @zhangshuai：activityStatus 最好代码里，也做下设置噢。
        List<DiscountProductDO> discountProducts = convertList(createReqVO.getProducts(),
                product -> DiscountActivityConvert.INSTANCE.convert(product).setActivityId(discountActivity.getId()));
        discountProductMapper.insertBatch(discountProducts);
        // 返回
        return discountActivity.getId();
    }

    @Override
    public void updateDiscountActivity(DiscountActivityUpdateReqVO updateReqVO) {
        // 校验存在
        DiscountActivityDO discountActivity = validateDiscountActivityExists(updateReqVO.getId());
        if (discountActivity.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) { // 已关闭的活动，不能修改噢
            throw exception(DISCOUNT_ACTIVITY_UPDATE_FAIL_STATUS_CLOSED);
        }
        // 校验商品是否冲突
        validateDiscountActivityProductConflicts(updateReqVO.getId(), updateReqVO.getProducts());

        // 更新活动
        DiscountActivityDO updateObj = DiscountActivityConvert.INSTANCE.convert(updateReqVO)
                .setStatus(PromotionUtils.calculateActivityStatus(updateReqVO.getEndTime()));
        discountActivityMapper.updateById(updateObj);
        // 更新商品
        updateDiscountProduct(updateReqVO);
    }

    @Override
    public void closeRewardActivity(Long id) {

    }

    private void updateDiscountProduct(DiscountActivityUpdateReqVO updateReqVO) {
        // TODO @zhangshuai：这里的逻辑，可以优化下哈；参考 CombinationActivityServiceImpl 的 updateCombinationProduct，主要是 CollectionUtils.diffList 的使用哈；
        //  然后原先是使用 DiscountActivityConvert.INSTANCE.isEquals 对比，现在看看是不是简化就基于 skuId 对比就完事了；之前写的太精细，意义不大；
        List<DiscountProductDO> dbDiscountProducts = discountProductMapper.selectListByActivityId(updateReqVO.getId());
        // 计算要删除的记录
        List<Long> deleteIds = convertList(dbDiscountProducts, DiscountProductDO::getId,
                discountProductDO -> updateReqVO.getProducts().stream()
                        .noneMatch(product -> DiscountActivityConvert.INSTANCE.isEquals(discountProductDO, product)));
        if (CollUtil.isNotEmpty(deleteIds)) {
            discountProductMapper.deleteBatchIds(deleteIds);
        }
        // 计算新增的记录
        List<DiscountProductDO> newDiscountProducts = convertList(updateReqVO.getProducts(),
                product -> DiscountActivityConvert.INSTANCE.convert(product).setActivityId(updateReqVO.getId()));
        newDiscountProducts.removeIf(product -> dbDiscountProducts.stream().anyMatch(
                dbProduct -> DiscountActivityConvert.INSTANCE.isEquals(dbProduct, product))); // 如果匹配到，说明是更新的
        if (CollectionUtil.isNotEmpty(newDiscountProducts)) {
            discountProductMapper.insertBatch(newDiscountProducts);
        }
    }

    // TODO 芋艿：校验逻辑简化，只查询时间冲突的活动，开启状态的。
    /**
     * 校验商品是否冲突
     *
     * @param id 编号
     * @param products 商品列表
     */
    private void validateDiscountActivityProductConflicts(Long id, List<DiscountActivityBaseVO.Product> products) {
        if (CollUtil.isEmpty(products)) {
            return;
        }
        // 查询商品参加的活动

        // TODO @zhangshuai：下面 121 这个查询，是不是不用做呀；直接 convert 出 skuId 集合就 ok 啦；
        List<DiscountProductDO> list = discountProductMapper.selectListByActivityId(id);
        // TODO @zhangshuai：一般简单的 stream 方法，建议是使用 CollectionUtils，例如说这里是 convertList 对把。
        List<Long> skuIds = list.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
        List<DiscountProductDO> matchDiscountProductList = getMatchDiscountProductList(skuIds);

        List<DiscountProductDO> discountActivityProductList = null;
//                getRewardProductListBySkuIds(
//                convertSet(products, DiscountActivityBaseVO.Product::getSkuId),
//                asList(PromotionActivityStatusEnum.WAIT.getStatus(), PromotionActivityStatusEnum.RUN.getStatus()));

        if (id != null) { // 排除自己这个活动
            matchDiscountProductList.removeIf(product -> id.equals(product.getActivityId()));
        }
        // 如果非空，则说明冲突
        if (CollUtil.isNotEmpty(matchDiscountProductList)) {
            throw exception(DISCOUNT_ACTIVITY_SPU_CONFLICTS);
        }
    }

    @Override
    public void closeDiscountActivity(Long id) {
        // 校验存在
        DiscountActivityDO activity = validateDiscountActivityExists(id);
        if (activity.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) { // 已关闭的活动，不能关闭噢
            throw exception(DISCOUNT_ACTIVITY_CLOSE_FAIL_STATUS_CLOSED);
        }

        // 更新为关闭。
        DiscountActivityDO updateObj = new DiscountActivityDO().setId(id).setStatus(PromotionActivityStatusEnum.CLOSE.getStatus());
        discountActivityMapper.updateById(updateObj);
    }

    @Override
    public void deleteDiscountActivity(Long id) {
        // 校验存在
        DiscountActivityDO activity = validateDiscountActivityExists(id);
        if (CommonStatusEnum.isEnable(activity.getStatus())) { // 未关闭的活动，不能删除噢
            throw exception(DISCOUNT_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED);
        }

        // 删除
        discountActivityMapper.deleteById(id);
    }

    private DiscountActivityDO validateDiscountActivityExists(Long id) {
        DiscountActivityDO discountActivity = discountActivityMapper.selectById(id);
        if (discountActivity == null) {
            throw exception(DISCOUNT_ACTIVITY_NOT_EXISTS);
        }
        return discountActivity;
    }

    @Override
    public DiscountActivityDO getDiscountActivity(Long id) {
        return discountActivityMapper.selectById(id);
    }

    @Override
    public PageResult<DiscountActivityDO> getDiscountActivityPage(DiscountActivityPageReqVO pageReqVO) {
        return discountActivityMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DiscountProductDO> getDiscountProductsByActivityId(Long activityId) {
        return discountProductMapper.selectListByActivityId(activityId);
    }

    @Override
    public List<DiscountProductDO> getDiscountProductsByActivityId(Collection<Long> activityIds) {
        return discountProductMapper.selectList("activity_id", activityIds);
    }

}
