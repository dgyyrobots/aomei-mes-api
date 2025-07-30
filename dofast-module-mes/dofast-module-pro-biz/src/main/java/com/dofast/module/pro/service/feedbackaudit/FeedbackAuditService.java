package com.dofast.module.pro.service.feedbackaudit;

import java.util.*;
import javax.validation.*;
import com.dofast.module.pro.controller.admin.feedbackaudit.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudit.FeedbackAuditDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 报工审批主表 Service 接口
 *
 * @author 惠智造
 */
public interface FeedbackAuditService {

    /**
     * 创建报工审批主表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeedbackAudit(@Valid FeedbackAuditCreateReqVO createReqVO);

    /**
     * 更新报工审批主表
     *
     * @param updateReqVO 更新信息
     */
    void updateFeedbackAudit(@Valid FeedbackAuditUpdateReqVO updateReqVO);

    /**
     * 删除报工审批主表
     *
     * @param id 编号
     */
    void deleteFeedbackAudit(Long id);

    /**
     * 获得报工审批主表
     *
     * @param id 编号
     * @return 报工审批主表
     */
    FeedbackAuditDO getFeedbackAudit(Long id);

    /**
     * 获得报工审批主表列表
     *
     * @param ids 编号
     * @return 报工审批主表列表
     */
    List<FeedbackAuditDO> getFeedbackAuditList(Collection<Long> ids);

    /**
     * 获得报工审批主表分页
     *
     * @param pageReqVO 分页查询
     * @return 报工审批主表分页
     */
    PageResult<FeedbackAuditDO> getFeedbackAuditPage(FeedbackAuditPageReqVO pageReqVO);

    /**
     * 获得报工审批主表列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 报工审批主表列表
     */
    List<FeedbackAuditDO> getFeedbackAuditList(FeedbackAuditExportReqVO exportReqVO);

}
