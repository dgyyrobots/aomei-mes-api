package com.dofast.module.pro.dal.mysql.feedbackaudit;

import java.util.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.feedbackaudit.FeedbackAuditDO;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLinePageReqVO;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLineSummaryVO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.feedbackaudit.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 报工审批主表 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackAuditMapper extends BaseMapperX<FeedbackAuditDO> {

    default PageResult<FeedbackAuditDO> selectPage(FeedbackAuditPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackAuditDO>()
                .eqIfPresent(FeedbackAuditDO::getAuditCode, reqVO.getAuditCode())
                .eqIfPresent(FeedbackAuditDO::getSubmitUserId, reqVO.getSubmitUserId())
                .likeIfPresent(FeedbackAuditDO::getSubmitUserName, reqVO.getSubmitUserName())
                .likeIfPresent(FeedbackAuditDO::getSubmitNickName, reqVO.getSubmitNickName())
                .betweenIfPresent(FeedbackAuditDO::getSubmitTime, reqVO.getSubmitTime())
                .eqIfPresent(FeedbackAuditDO::getAuditUserId, reqVO.getAuditUserId())
                .likeIfPresent(FeedbackAuditDO::getAuditUserName, reqVO.getAuditUserName())
                .likeIfPresent(FeedbackAuditDO::getAuditNickName, reqVO.getAuditNickName())
                .betweenIfPresent(FeedbackAuditDO::getAuditTime, reqVO.getAuditTime())
                .eqIfPresent(FeedbackAuditDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FeedbackAuditDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(FeedbackAuditDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackAuditDO::getOriginalAuditUserId, reqVO.getOriginalAuditUserId())
                .eqIfPresent(FeedbackAuditDO::getTransferHistory, reqVO.getTransferHistory())
                .eqIfPresent(FeedbackAuditDO::getSourceAuditId, reqVO.getSourceAuditId())
                .eqIfPresent(FeedbackAuditDO::getMethodArgs, reqVO.getMethodArgs())
                .orderByDesc(FeedbackAuditDO::getId));
    }

    default List<FeedbackAuditDO> selectList(FeedbackAuditExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FeedbackAuditDO>()
                .eqIfPresent(FeedbackAuditDO::getAuditCode, reqVO.getAuditCode())
                .eqIfPresent(FeedbackAuditDO::getSubmitUserId, reqVO.getSubmitUserId())
                .likeIfPresent(FeedbackAuditDO::getSubmitUserName, reqVO.getSubmitUserName())
                .likeIfPresent(FeedbackAuditDO::getSubmitNickName, reqVO.getSubmitNickName())
                .betweenIfPresent(FeedbackAuditDO::getSubmitTime, reqVO.getSubmitTime())
                .eqIfPresent(FeedbackAuditDO::getAuditUserId, reqVO.getAuditUserId())
                .likeIfPresent(FeedbackAuditDO::getAuditUserName, reqVO.getAuditUserName())
                .likeIfPresent(FeedbackAuditDO::getAuditNickName, reqVO.getAuditNickName())
                .betweenIfPresent(FeedbackAuditDO::getAuditTime, reqVO.getAuditTime())
                .eqIfPresent(FeedbackAuditDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FeedbackAuditDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(FeedbackAuditDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackAuditDO::getOriginalAuditUserId, reqVO.getOriginalAuditUserId())
                .eqIfPresent(FeedbackAuditDO::getTransferHistory, reqVO.getTransferHistory())
                .eqIfPresent(FeedbackAuditDO::getSourceAuditId, reqVO.getSourceAuditId())
                .eqIfPresent(FeedbackAuditDO::getMethodArgs, reqVO.getMethodArgs())
                .orderByDesc(FeedbackAuditDO::getId));
    }

    IPage<FeedbackAuditRespVO> selectAuditList(Page<FeedbackAuditDO> page, @Param("req") FeedbackAuditPageReqVO req);


}
