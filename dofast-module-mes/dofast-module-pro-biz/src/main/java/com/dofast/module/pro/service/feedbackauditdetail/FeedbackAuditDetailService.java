package com.dofast.module.pro.service.feedbackauditdetail;

import java.util.*;
import javax.validation.*;
import com.dofast.module.pro.controller.admin.feedbackauditdetail.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackauditdetail.FeedbackAuditDetailDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 报工审批明细 Service 接口
 *
 * @author 惠智造
 */
public interface FeedbackAuditDetailService {

    /**
     * 创建报工审批明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeedbackAuditDetail(@Valid FeedbackAuditDetailCreateReqVO createReqVO);

    /**
     * 更新报工审批明细
     *
     * @param updateReqVO 更新信息
     */
    void updateFeedbackAuditDetail(@Valid FeedbackAuditDetailUpdateReqVO updateReqVO);

    /**
     * 删除报工审批明细
     *
     * @param id 编号
     */
    void deleteFeedbackAuditDetail(Long id);

    /**
     * 获得报工审批明细
     *
     * @param id 编号
     * @return 报工审批明细
     */
    FeedbackAuditDetailDO getFeedbackAuditDetail(Long id);

    /**
     * 获得报工审批明细列表
     *
     * @param ids 编号
     * @return 报工审批明细列表
     */
    List<FeedbackAuditDetailDO> getFeedbackAuditDetailList(Collection<Long> ids);

    /**
     * 获得报工审批明细分页
     *
     * @param pageReqVO 分页查询
     * @return 报工审批明细分页
     */
    PageResult<FeedbackAuditDetailDO> getFeedbackAuditDetailPage(FeedbackAuditDetailPageReqVO pageReqVO);

    /**
     * 获得报工审批明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 报工审批明细列表
     */
    List<FeedbackAuditDetailDO> getFeedbackAuditDetailList(FeedbackAuditDetailExportReqVO exportReqVO);

    List<FeedbackAuditDetailDO> selectBatchIds(List<Long> ids);

}
