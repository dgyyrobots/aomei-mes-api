package com.dofast.module.pro.service.feedbackaudititem;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.pro.controller.admin.feedbackaudititem.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.feedbackaudititem.FeedbackAuditItemConvert;
import com.dofast.module.pro.dal.mysql.feedbackaudititem.FeedbackAuditItemMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 报工审批汇总 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class FeedbackAuditItemServiceImpl implements FeedbackAuditItemService {

    @Resource
    private FeedbackAuditItemMapper feedbackAuditItemMapper;

    @Override
    public Long createFeedbackAuditItem(FeedbackAuditItemCreateReqVO createReqVO) {
        // 插入
        FeedbackAuditItemDO feedbackAuditItem = FeedbackAuditItemConvert.INSTANCE.convert(createReqVO);
        feedbackAuditItemMapper.insert(feedbackAuditItem);
        // 返回
        return feedbackAuditItem.getId();
    }

    @Override
    public void updateFeedbackAuditItem(FeedbackAuditItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackAuditItemExists(updateReqVO.getId());
        // 更新
        FeedbackAuditItemDO updateObj = FeedbackAuditItemConvert.INSTANCE.convert(updateReqVO);
        feedbackAuditItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedbackAuditItem(Long id) {
        // 校验存在
        validateFeedbackAuditItemExists(id);
        // 删除
        feedbackAuditItemMapper.deleteById(id);
    }

    private void validateFeedbackAuditItemExists(Long id) {
        if (feedbackAuditItemMapper.selectById(id) == null) {
            throw exception(FEEDBACK_AUDIT_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackAuditItemDO getFeedbackAuditItem(Long id) {
        return feedbackAuditItemMapper.selectById(id);
    }

    @Override
    public List<FeedbackAuditItemDO> getFeedbackAuditItemList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return feedbackAuditItemMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FeedbackAuditItemDO> getFeedbackAuditItemPage(FeedbackAuditItemPageReqVO pageReqVO) {
        return feedbackAuditItemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedbackAuditItemDO> getFeedbackAuditItemList(FeedbackAuditItemExportReqVO exportReqVO) {
        return feedbackAuditItemMapper.selectList(exportReqVO);
    }

}
