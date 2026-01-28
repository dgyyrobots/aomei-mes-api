package com.dofast.module.pro.service.feedbackdefect;

import java.util.*;
import javax.validation.*;
import com.dofast.module.pro.controller.admin.feedbackdefect.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackdefect.FeedbackDefectDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 报工缺陷 Service 接口
 *
 * @author 惠智造
 */
public interface FeedbackDefectService {

    /**
     * 创建报工缺陷
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeedbackDefect(@Valid FeedbackDefectCreateReqVO createReqVO);

    /**
     * 更新报工缺陷
     *
     * @param updateReqVO 更新信息
     */
    void updateFeedbackDefect(@Valid FeedbackDefectUpdateReqVO updateReqVO);

    /**
     * 删除报工缺陷
     *
     * @param id 编号
     */
    void deleteFeedbackDefect(Long id);

    /**
     * 获得报工缺陷
     *
     * @param id 编号
     * @return 报工缺陷
     */
    FeedbackDefectDO getFeedbackDefect(Long id);

    /**
     * 获得报工缺陷列表
     *
     * @param ids 编号
     * @return 报工缺陷列表
     */
    List<FeedbackDefectDO> getFeedbackDefectList(Collection<Long> ids);

    /**
     * 获得报工缺陷分页
     *
     * @param pageReqVO 分页查询
     * @return 报工缺陷分页
     */
    PageResult<FeedbackDefectDO> getFeedbackDefectPage(FeedbackDefectPageReqVO pageReqVO);

    /**
     * 获得报工缺陷列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 报工缺陷列表
     */
    List<FeedbackDefectDO> getFeedbackDefectList(FeedbackDefectExportReqVO exportReqVO);

    List<FeedbackDefectDO> getFeedbackDefectsByFeedbackIds(List<Long> feedbackIds);

}
