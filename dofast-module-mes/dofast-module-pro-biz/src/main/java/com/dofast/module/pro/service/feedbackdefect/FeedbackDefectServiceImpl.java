package com.dofast.module.pro.service.feedbackdefect;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.pro.controller.admin.feedbackdefect.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackdefect.FeedbackDefectDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.feedbackdefect.FeedbackDefectConvert;
import com.dofast.module.pro.dal.mysql.feedbackdefect.FeedbackDefectMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 报工缺陷 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class FeedbackDefectServiceImpl implements FeedbackDefectService {

    @Resource
    private FeedbackDefectMapper feedbackDefectMapper;

    @Override
    public Long createFeedbackDefect(FeedbackDefectCreateReqVO createReqVO) {
        // 插入
        FeedbackDefectDO feedbackDefect = FeedbackDefectConvert.INSTANCE.convert(createReqVO);
        feedbackDefectMapper.insert(feedbackDefect);
        // 返回
        return feedbackDefect.getId();
    }

    @Override
    public void updateFeedbackDefect(FeedbackDefectUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackDefectExists(updateReqVO.getId());
        // 更新
        FeedbackDefectDO updateObj = FeedbackDefectConvert.INSTANCE.convert(updateReqVO);
        feedbackDefectMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedbackDefect(Long id) {
        // 校验存在
        validateFeedbackDefectExists(id);
        // 删除
        feedbackDefectMapper.deleteById(id);
    }

    private void validateFeedbackDefectExists(Long id) {
        if (feedbackDefectMapper.selectById(id) == null) {
            throw exception(FEEDBACK_DEFECT_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackDefectDO getFeedbackDefect(Long id) {
        return feedbackDefectMapper.selectById(id);
    }

    @Override
    public List<FeedbackDefectDO> getFeedbackDefectList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return feedbackDefectMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FeedbackDefectDO> getFeedbackDefectPage(FeedbackDefectPageReqVO pageReqVO) {
        return feedbackDefectMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedbackDefectDO> getFeedbackDefectList(FeedbackDefectExportReqVO exportReqVO) {
        return feedbackDefectMapper.selectList(exportReqVO);
    }

    @Override
    public List<FeedbackDefectDO> getFeedbackDefectsByFeedbackIds(List<Long> feedbackIds){
        return feedbackDefectMapper.selectListByFeedbackIds(feedbackIds);
    }

}
