package com.dofast.module.pro.service.feedbackwarehousinglog;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.*;

import com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackwarehousinglog.FeedbackWarehousingLogDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.feedbackwarehousinglog.FeedbackWarehousingLogConvert;
import com.dofast.module.pro.dal.mysql.feedbackwarehousinglog.FeedbackWarehousingLogMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 报工入库日志 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class FeedbackWarehousingLogServiceImpl implements FeedbackWarehousingLogService {

    @Resource
    private FeedbackWarehousingLogMapper feedbackWarehousingLogMapper;

    @Override
    public Long createFeedbackWarehousingLog(FeedbackWarehousingLogCreateReqVO createReqVO) {
        // 插入
        FeedbackWarehousingLogDO feedbackWarehousingLog = FeedbackWarehousingLogConvert.INSTANCE.convert(createReqVO);
        feedbackWarehousingLogMapper.insert(feedbackWarehousingLog);
        // 返回
        return feedbackWarehousingLog.getId();
    }

    @Override
    public void updateFeedbackWarehousingLog(FeedbackWarehousingLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackWarehousingLogExists(updateReqVO.getId());
        // 更新
        FeedbackWarehousingLogDO updateObj = FeedbackWarehousingLogConvert.INSTANCE.convert(updateReqVO);
        feedbackWarehousingLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedbackWarehousingLog(Long id) {
        // 校验存在
        validateFeedbackWarehousingLogExists(id);
        // 删除
        feedbackWarehousingLogMapper.deleteById(id);
    }

    private void validateFeedbackWarehousingLogExists(Long id) {
        if (feedbackWarehousingLogMapper.selectById(id) == null) {
            throw exception(FEEDBACK_WAREHOUSING_LOG_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackWarehousingLogDO getFeedbackWarehousingLog(Long id) {
        return feedbackWarehousingLogMapper.selectById(id);
    }

    @Override
    public List<FeedbackWarehousingLogDO> getFeedbackWarehousingLogList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return feedbackWarehousingLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FeedbackWarehousingLogDO> getFeedbackWarehousingLogPage(FeedbackWarehousingLogPageReqVO pageReqVO) {
        return feedbackWarehousingLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedbackWarehousingLogDO> getFeedbackWarehousingLogList(FeedbackWarehousingLogExportReqVO exportReqVO) {
        return feedbackWarehousingLogMapper.selectList(exportReqVO);
    }


    @Override
    public void createBatch(List<FeedbackWarehousingLogDO> feedbackWarehousingLogDOList) {
        feedbackWarehousingLogMapper.insertBatch(feedbackWarehousingLogDOList);
    }

    @Override
    public void updateBatch(List<FeedbackWarehousingLogDO> feedbackWarehousingLogDOList){
        feedbackWarehousingLogMapper.updateBatch(feedbackWarehousingLogDOList);
    }



}
