package com.dofast.module.promotion.service.combination;

import com.dofast.framework.common.core.KeyValue;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.module.promotion.api.combination.dto.CombinationRecordCreateReqDTO;
import com.dofast.module.promotion.api.combination.dto.CombinationRecordCreateRespDTO;
import com.dofast.module.promotion.api.combination.dto.CombinationValidateJoinRespDTO;
import com.dofast.module.promotion.controller.admin.combination.vo.recrod.CombinationRecordReqPageVO;
import com.dofast.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import com.dofast.module.promotion.dal.dataobject.combination.CombinationProductDO;
import com.dofast.module.promotion.dal.dataobject.combination.CombinationRecordDO;

import javax.annotation.Nullable;
import javax.validation.Valid;


import java.time.LocalDateTime;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 拼团记录 Service 接口
 *
 * @author HUIHUI
 */
public interface CombinationRecordService {

    /**
     * 更新拼团状态
     *
     * @param status  状态
     * @param userId  用户编号
     * @param orderId 订单编号
     */
    void updateCombinationRecordStatusByUserIdAndOrderId(Integer status, Long userId, Long orderId);

    /**
     * 校验是否满足拼团条件
     * 如果不满足，会抛出异常
     *
     * @param activityId 活动编号
     * @param userId     用户编号
     * @param skuId      sku 编号
     * @param count      数量
     * @return 返回拼团活动和拼团活动商品
     */
    KeyValue<CombinationActivityDO, CombinationProductDO> validateCombinationRecord(Long activityId, Long userId, Long skuId, Integer count);

    /**
     * 创建拼团记录
     *
     * @param reqDTO 创建信息
     */
    void createCombinationRecord(CombinationRecordCreateReqDTO reqDTO);


    /**
     * 创建拼团记录
     *
     * @param reqDTO 创建信息
     * @return 团信息
     */
    CombinationRecordDO createCombinationRecord1(CombinationRecordCreateReqDTO reqDTO);

    /**
     * 更新拼团状态和开始时间
     *
     * @param status    状态
     * @param userId    用户编号
     * @param orderId   订单编号
     * @param startTime 开始时间
     */
    void updateRecordStatusAndStartTimeByUserIdAndOrderId(Integer status, Long userId, Long orderId, LocalDateTime startTime);

    /**
     * 获得拼团记录
     *
     * @param userId  用户编号
     * @param orderId 订单编号
     * @return 拼团记录
     */
    CombinationRecordDO getCombinationRecord(Long userId, Long orderId);

    /**
     * 获取拼团记录
     *
     * @param userId     用户 id
     * @param activityId 活动 id
     * @return 拼团记录列表
     */
    List<CombinationRecordDO> getCombinationRecordListByUserIdAndActivityId(Long userId, Long activityId);

    /**
     * 【下单前】校验是否满足拼团活动条件
     *
     * 如果校验失败，则抛出业务异常
     *
     * @param activityId 活动编号
     * @param userId     用户编号
     * @param skuId      sku 编号
     * @param count      数量
     * @return 拼团信息
     */
    CombinationValidateJoinRespDTO validateJoinCombination(Long activityId, Long userId, Long skuId, Integer count);

    /**
     * 获取所有拼团记录数
     *
     * @return 记录数
     */
    Long getCombinationRecordCount();

    /**
     * 获取成功记录数
     *
     * @return 记录数
     */
    Long getCombinationRecordsSuccessCount();

    /**
     * 获取虚拟成团记录数
     *
     * @return 记录数
     */
    Long getRecordsVirtualGroupCount();

    /**
     * 获取指定日期类型的记录数，比如说获取最近七天的拼团记录数
     *
     * @param dateType 日期类型
     * @return 记录数
     */
    Long getCombinationRecordsCountByDateType(Integer dateType);

    /**
     * 获取最近的 count 条拼团记录
     *
     * @param count 限制数量
     * @return 拼团记录列表
     */
    List<CombinationRecordDO> getLatestCombinationRecordList(int count);

    /**
     * 获得最近 n 条拼团记录（团长发起的）
     *
     * @param activityId 拼团活动编号
     * @param status     状态
     * @param count      数量
     * @return 拼团记录列表
     */
    List<CombinationRecordDO> getCombinationRecordListWithHead(Long activityId, Integer status, Integer count);

    /**
     * 获取指定编号的拼团记录
     *
     * @param id 拼团记录编号
     * @return 拼团记录
     */
    CombinationRecordDO getCombinationRecordById(Long id);

    /**
     * 获取指定团长编号的拼团记录
     *
     * @param headId 团长编号
     * @return 拼团记录列表
     */
    List<CombinationRecordDO> getCombinationRecordListByHeadId(Long headId);

    /**
     * 获取拼团记录分页数据
     *
     * @param pageVO 分页请求
     * @return 拼团记录分页数据
     */
    PageResult<CombinationRecordDO> getCombinationRecordPage(CombinationRecordReqPageVO pageVO);

    /**
     * 【拼团活动】获得拼团记录数量 Map
     *
     * @param activityIds 活动记录编号数组
     * @param status     拼团状态，允许空
     * @param headId    团长编号，允许空。目的 headId 设置为 {@link CombinationRecordDO#HEAD_ID_GROUP} 时，可以设置
     * @return 拼团记录数量 Map
     */
    Map<Long, Integer> getCombinationRecordCountMapByActivity(Collection<Long> activityIds,
                                                              @Nullable Integer status,
                                                              @Nullable Integer headId);



    /**
     * 【下单前】校验是否满足拼团活动条件
     *
     * 如果校验失败，则抛出业务异常
     *
     * @param userId     用户编号
     * @param activityId 活动编号
     * @param headId     团长编号
     * @param skuId      sku 编号
     * @param count      数量
     * @return 拼团信息
     */
    KeyValue<CombinationActivityDO, CombinationProductDO> validateCombinationRecord(Long userId, Long activityId, Long headId,
                                                                                    Long skuId, Integer count);


    /**
     * 【下单前】校验是否满足拼团活动条件
     *
     * 如果校验失败，则抛出业务异常
     *
     * @param userId     用户编号
     * @param activityId 活动编号
     * @param headId     团长编号
     * @param skuId      sku 编号
     * @param count      数量
     * @return 拼团信息
     */
    CombinationValidateJoinRespDTO validateJoinCombination(Long userId, Long activityId, Long headId, Long skuId, Integer count);

    /**
     * 获取拼团记录数
     *
     * @param status       状态-允许为空
     * @param virtualGroup 是否虚拟成团-允许为空
     * @param headId       团长编号，允许空。目的 headId 设置为 {@link CombinationRecordDO#HEAD_ID_GROUP} 时，可以设置
     * @return 记录数
     */
    Long getCombinationRecordCount(@Nullable Integer status, @Nullable Boolean virtualGroup, Long headId);

    /**
     * 查询用户拼团记录（DISTINCT 去重），也就是说查询会员表中的用户有多少人参与过拼团活动每个人只统计一次
     *
     * @return 参加过拼团的用户数
     */
    Long getCombinationUserCount();


    /**
     * 获得最近 n 条拼团记录（团长发起的）
     *
     * @param activityId 拼团活动编号
     * @param status     状态
     * @param count      数量
     * @return 拼团记录列表
     */
    List<CombinationRecordDO> getHeadCombinationRecordList(Long activityId, Integer status, Integer count);

    /**
     * 【拼团活动】获得拼团记录数量 Map
     *
     * @param activityIds 活动记录编号数组
     * @param status      拼团状态，允许空
     * @param headId      团长编号，允许空。目的 headId 设置为 {@link CombinationRecordDO#HEAD_ID_GROUP} 时，可以设置
     * @return 拼团记录数量 Map
     */
    Map<Long, Integer> getCombinationRecordCountMapByActivity1(Collection<Long> activityIds,
                                                              @Nullable Integer status,
                                                              @Nullable Long headId);

    /**
     * 获取拼团记录
     *
     * @param userId 用户编号
     * @param id     拼团记录编号
     * @return 拼团记录
     */
    CombinationRecordDO getCombinationRecordByIdAndUser(Long userId, Long id);

    /**
     * 取消拼团
     *
     * @param userId 用户编号
     * @param id     拼团记录编号
     * @param headId 团长编号
     */
    void cancelCombinationRecord(Long userId, Long id, Long headId);

    /**
     * 处理过期拼团
     *
     * @return key 过期拼团数量, value 虚拟成团数量
     */
    KeyValue<Integer, Integer> expireCombinationRecord();

}
