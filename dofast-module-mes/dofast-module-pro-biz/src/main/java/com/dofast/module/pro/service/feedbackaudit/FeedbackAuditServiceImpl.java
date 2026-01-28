package com.dofast.module.pro.service.feedbackaudit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLineSummaryVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.pro.controller.admin.feedbackaudit.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudit.FeedbackAuditDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.feedbackaudit.FeedbackAuditConvert;
import com.dofast.module.pro.dal.mysql.feedbackaudit.FeedbackAuditMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 报工审批主表 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class FeedbackAuditServiceImpl implements FeedbackAuditService {

    @Resource
    private FeedbackAuditMapper feedbackAuditMapper;

    @Override
    public Long createFeedbackAudit(FeedbackAuditCreateReqVO createReqVO) {
        // 插入
        FeedbackAuditDO feedbackAudit = FeedbackAuditConvert.INSTANCE.convert(createReqVO);
        feedbackAuditMapper.insert(feedbackAudit);
        // 返回
        return feedbackAudit.getId();
    }

    @Override
    public void updateFeedbackAudit(FeedbackAuditUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackAuditExists(updateReqVO.getId());
        // 更新
        FeedbackAuditDO updateObj = FeedbackAuditConvert.INSTANCE.convert(updateReqVO);
        feedbackAuditMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedbackAudit(Long id) {
        // 校验存在
        validateFeedbackAuditExists(id);
        // 删除
        feedbackAuditMapper.deleteById(id);
    }

    private void validateFeedbackAuditExists(Long id) {
        if (feedbackAuditMapper.selectById(id) == null) {
            throw exception(FEEDBACK_AUDIT_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackAuditDO getFeedbackAudit(Long id) {
        return feedbackAuditMapper.selectById(id);
    }

    @Override
    public List<FeedbackAuditDO> getFeedbackAuditList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return feedbackAuditMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FeedbackAuditDO> getFeedbackAuditPage(FeedbackAuditPageReqVO pageReqVO) {
        return feedbackAuditMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedbackAuditDO> getFeedbackAuditList(FeedbackAuditExportReqVO exportReqVO) {
        return feedbackAuditMapper.selectList(exportReqVO);
    }

    @Override
    public IPage<FeedbackAuditRespVO> selectAuditList(FeedbackAuditPageReqVO pageVO){
        Page<FeedbackAuditDO> page = new Page<>(
                pageVO.getPageNo() == null ? 1 : pageVO.getPageNo(),
                pageVO.getPageSize() == null ? 10 : pageVO.getPageSize()
        );

        return feedbackAuditMapper.selectAuditList(page, pageVO);
    }

}
