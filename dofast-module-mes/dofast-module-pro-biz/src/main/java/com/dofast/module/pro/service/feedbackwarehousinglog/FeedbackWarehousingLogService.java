package com.dofast.module.pro.service.feedbackwarehousinglog;

import java.util.*;
import javax.validation.*;

import com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackwarehousinglog.FeedbackWarehousingLogDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 报工入库日志 Service 接口
 *
 * @author 惠智造
 */
public interface FeedbackWarehousingLogService {

    /**
     * 创建报工入库日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeedbackWarehousingLog(@Valid FeedbackWarehousingLogCreateReqVO createReqVO);

    /**
     * 更新报工入库日志
     *
     * @param updateReqVO 更新信息
     */
    void updateFeedbackWarehousingLog(@Valid FeedbackWarehousingLogUpdateReqVO updateReqVO);

    /**
     * 删除报工入库日志
     *
     * @param id 编号
     */
    void deleteFeedbackWarehousingLog(Long id);

    /**
     * 获得报工入库日志
     *
     * @param id 编号
     * @return 报工入库日志
     */
    FeedbackWarehousingLogDO getFeedbackWarehousingLog(Long id);

    /**
     * 获得报工入库日志列表
     *
     * @param ids 编号
     * @return 报工入库日志列表
     */
    List<FeedbackWarehousingLogDO> getFeedbackWarehousingLogList(Collection<Long> ids);

    /**
     * 获得报工入库日志分页
     *
     * @param pageReqVO 分页查询
     * @return 报工入库日志分页
     */
    PageResult<FeedbackWarehousingLogDO> getFeedbackWarehousingLogPage(FeedbackWarehousingLogPageReqVO pageReqVO);

    /**
     * 获得报工入库日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 报工入库日志列表
     */
    List<FeedbackWarehousingLogDO> getFeedbackWarehousingLogList(FeedbackWarehousingLogExportReqVO exportReqVO);

    void createBatch(List<FeedbackWarehousingLogDO> feedbackWarehousingLogDOList);

    void updateBatch(List<FeedbackWarehousingLogDO> feedbackWarehousingLogDOList);

}
