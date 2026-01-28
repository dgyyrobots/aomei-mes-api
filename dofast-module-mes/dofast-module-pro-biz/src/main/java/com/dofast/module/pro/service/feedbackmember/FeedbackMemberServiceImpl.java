package com.dofast.module.pro.service.feedbackmember;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.pro.controller.admin.feedbackmember.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackmember.FeedbackMemberDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.feedbackmember.FeedbackMemberConvert;
import com.dofast.module.pro.dal.mysql.feedbackmember.FeedbackMemberMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 报工班组人员 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class FeedbackMemberServiceImpl implements FeedbackMemberService {

    @Resource
    private FeedbackMemberMapper feedbackMemberMapper;

    @Override
    public Long createFeedbackMember(FeedbackMemberCreateReqVO createReqVO) {
        // 插入
        FeedbackMemberDO feedbackMember = FeedbackMemberConvert.INSTANCE.convert(createReqVO);
        feedbackMemberMapper.insert(feedbackMember);
        // 返回
        return feedbackMember.getId();
    }

    @Override
    public void updateFeedbackMember(FeedbackMemberUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackMemberExists(updateReqVO.getId());
        // 更新
        FeedbackMemberDO updateObj = FeedbackMemberConvert.INSTANCE.convert(updateReqVO);
        feedbackMemberMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedbackMember(Long id) {
        // 校验存在
        validateFeedbackMemberExists(id);
        // 删除
        feedbackMemberMapper.deleteById(id);
    }

    private void validateFeedbackMemberExists(Long id) {
        if (feedbackMemberMapper.selectById(id) == null) {
            throw exception(FEEDBACK_MEMBER_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackMemberDO getFeedbackMember(Long id) {
        return feedbackMemberMapper.selectById(id);
    }

    @Override
    public List<FeedbackMemberDO> getFeedbackMemberList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return feedbackMemberMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FeedbackMemberDO> getFeedbackMemberPage(FeedbackMemberPageReqVO pageReqVO) {
        return feedbackMemberMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedbackMemberDO> getFeedbackMemberList(FeedbackMemberExportReqVO exportReqVO) {
        return feedbackMemberMapper.selectList(exportReqVO);
    }

    @Override
    public List<FeedbackMemberDO> getFeedbackMembersByFeedbackIds(List<Long> feedbackIds){
        return feedbackMemberMapper.selectByFeedbackIds(feedbackIds);
    }

}
