package com.dofast.module.pro.service.feedbackaudititem;

import java.util.*;
import javax.validation.*;
import com.dofast.module.pro.controller.admin.feedbackaudititem.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 报工审批汇总 Service 接口
 *
 * @author 惠智造
 */
public interface FeedbackAuditItemService {

    /**
     * 创建报工审批汇总
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeedbackAuditItem(@Valid FeedbackAuditItemCreateReqVO createReqVO);

    /**
     * 更新报工审批汇总
     *
     * @param updateReqVO 更新信息
     */
    void updateFeedbackAuditItem(@Valid FeedbackAuditItemUpdateReqVO updateReqVO);

    /**
     * 删除报工审批汇总
     *
     * @param id 编号
     */
    void deleteFeedbackAuditItem(Long id);

    /**
     * 获得报工审批汇总
     *
     * @param id 编号
     * @return 报工审批汇总
     */
    FeedbackAuditItemDO getFeedbackAuditItem(Long id);

    /**
     * 获得报工审批汇总列表
     *
     * @param ids 编号
     * @return 报工审批汇总列表
     */
    List<FeedbackAuditItemDO> getFeedbackAuditItemList(Collection<Long> ids);

    /**
     * 获得报工审批汇总分页
     *
     * @param pageReqVO 分页查询
     * @return 报工审批汇总分页
     */
    PageResult<FeedbackAuditItemDO> getFeedbackAuditItemPage(FeedbackAuditItemPageReqVO pageReqVO);

    /**
     * 获得报工审批汇总列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 报工审批汇总列表
     */
    List<FeedbackAuditItemDO> getFeedbackAuditItemList(FeedbackAuditItemExportReqVO exportReqVO);

}
