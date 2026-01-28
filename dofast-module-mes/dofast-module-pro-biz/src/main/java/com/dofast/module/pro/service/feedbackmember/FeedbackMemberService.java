package com.dofast.module.pro.service.feedbackmember;

import java.util.*;
import javax.validation.*;
import com.dofast.module.pro.controller.admin.feedbackmember.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackmember.FeedbackMemberDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 报工班组人员 Service 接口
 *
 * @author 惠智造
 */
public interface FeedbackMemberService {

    /**
     * 创建报工班组人员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeedbackMember(@Valid FeedbackMemberCreateReqVO createReqVO);

    /**
     * 更新报工班组人员
     *
     * @param updateReqVO 更新信息
     */
    void updateFeedbackMember(@Valid FeedbackMemberUpdateReqVO updateReqVO);

    /**
     * 删除报工班组人员
     *
     * @param id 编号
     */
    void deleteFeedbackMember(Long id);

    /**
     * 获得报工班组人员
     *
     * @param id 编号
     * @return 报工班组人员
     */
    FeedbackMemberDO getFeedbackMember(Long id);

    /**
     * 获得报工班组人员列表
     *
     * @param ids 编号
     * @return 报工班组人员列表
     */
    List<FeedbackMemberDO> getFeedbackMemberList(Collection<Long> ids);

    /**
     * 获得报工班组人员分页
     *
     * @param pageReqVO 分页查询
     * @return 报工班组人员分页
     */
    PageResult<FeedbackMemberDO> getFeedbackMemberPage(FeedbackMemberPageReqVO pageReqVO);

    /**
     * 获得报工班组人员列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 报工班组人员列表
     */
    List<FeedbackMemberDO> getFeedbackMemberList(FeedbackMemberExportReqVO exportReqVO);


    List<FeedbackMemberDO> getFeedbackMembersByFeedbackIds(List<Long> feedbackIds);

}
