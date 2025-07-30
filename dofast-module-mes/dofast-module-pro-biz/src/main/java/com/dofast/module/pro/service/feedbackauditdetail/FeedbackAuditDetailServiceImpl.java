package com.dofast.module.pro.service.feedbackauditdetail;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.pro.controller.admin.feedbackauditdetail.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackauditdetail.FeedbackAuditDetailDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.feedbackauditdetail.FeedbackAuditDetailConvert;
import com.dofast.module.pro.dal.mysql.feedbackauditdetail.FeedbackAuditDetailMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 报工审批明细 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class FeedbackAuditDetailServiceImpl implements FeedbackAuditDetailService {

    @Resource
    private FeedbackAuditDetailMapper feedbackAuditDetailMapper;

    @Override
    public Long createFeedbackAuditDetail(FeedbackAuditDetailCreateReqVO createReqVO) {
        // 插入
        FeedbackAuditDetailDO feedbackAuditDetail = FeedbackAuditDetailConvert.INSTANCE.convert(createReqVO);
        feedbackAuditDetailMapper.insert(feedbackAuditDetail);
        // 返回
        return feedbackAuditDetail.getId();
    }

    @Override
    public void updateFeedbackAuditDetail(FeedbackAuditDetailUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackAuditDetailExists(updateReqVO.getId());
        // 更新
        FeedbackAuditDetailDO updateObj = FeedbackAuditDetailConvert.INSTANCE.convert(updateReqVO);
        feedbackAuditDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedbackAuditDetail(Long id) {
        // 校验存在
        validateFeedbackAuditDetailExists(id);
        // 删除
        feedbackAuditDetailMapper.deleteById(id);
    }

    private void validateFeedbackAuditDetailExists(Long id) {
        if (feedbackAuditDetailMapper.selectById(id) == null) {
            throw exception(FEEDBACK_AUDIT_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackAuditDetailDO getFeedbackAuditDetail(Long id) {
        return feedbackAuditDetailMapper.selectById(id);
    }

    @Override
    public List<FeedbackAuditDetailDO> getFeedbackAuditDetailList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return feedbackAuditDetailMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FeedbackAuditDetailDO> getFeedbackAuditDetailPage(FeedbackAuditDetailPageReqVO pageReqVO) {
        return feedbackAuditDetailMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedbackAuditDetailDO> getFeedbackAuditDetailList(FeedbackAuditDetailExportReqVO exportReqVO) {
        return feedbackAuditDetailMapper.selectList(exportReqVO);
    }

}
