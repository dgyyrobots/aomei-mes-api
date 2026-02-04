package com.dofast.module.pro.dal.mysql.feedbackmember;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.feedbackmember.FeedbackMemberDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.feedbackmember.vo.*;
import org.springframework.security.core.parameters.P;
import org.apache.ibatis.annotations.Param;

/**
 * 报工班组人员 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackMemberMapper extends BaseMapperX<FeedbackMemberDO> {

    default PageResult<FeedbackMemberDO> selectPage(FeedbackMemberPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackMemberDO>()
                .eqIfPresent(FeedbackMemberDO::getFeedbackId, reqVO.getFeedbackId())
                .eqIfPresent(FeedbackMemberDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(FeedbackMemberDO::getTeamCode, reqVO.getTeamCode())
                .eqIfPresent(FeedbackMemberDO::getUserId, reqVO.getUserId())
                .likeIfPresent(FeedbackMemberDO::getUserName, reqVO.getUserName())
                .likeIfPresent(FeedbackMemberDO::getNickName, reqVO.getNickName())
                .eqIfPresent(FeedbackMemberDO::getPostIds, reqVO.getPostIds())
                .betweenIfPresent(FeedbackMemberDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FeedbackMemberDO::getId));
    }

    default List<FeedbackMemberDO> selectList(FeedbackMemberExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FeedbackMemberDO>()
                .eqIfPresent(FeedbackMemberDO::getFeedbackId, reqVO.getFeedbackId())
                .eqIfPresent(FeedbackMemberDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(FeedbackMemberDO::getTeamCode, reqVO.getTeamCode())
                .eqIfPresent(FeedbackMemberDO::getUserId, reqVO.getUserId())
                .eqIfPresent(FeedbackMemberDO::getUserName, reqVO.getUserName())
                .eqIfPresent(FeedbackMemberDO::getNickName, reqVO.getNickName())
                .eqIfPresent(FeedbackMemberDO::getPostIds, reqVO.getPostIds())
                .betweenIfPresent(FeedbackMemberDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FeedbackMemberDO::getId));
    }

    List<FeedbackMemberDO> selectByFeedbackIds(@Param("feedbackIds") List<Long> feedbackIds);


}
