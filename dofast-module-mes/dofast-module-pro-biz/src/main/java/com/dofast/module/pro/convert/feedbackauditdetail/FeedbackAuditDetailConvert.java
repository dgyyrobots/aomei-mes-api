package com.dofast.module.pro.convert.feedbackauditdetail;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.pro.controller.admin.feedbackauditdetail.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackauditdetail.FeedbackAuditDetailDO;

/**
 * 报工审批明细 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackAuditDetailConvert {

    FeedbackAuditDetailConvert INSTANCE = Mappers.getMapper(FeedbackAuditDetailConvert.class);

    FeedbackAuditDetailDO convert(FeedbackAuditDetailCreateReqVO bean);

    FeedbackAuditDetailDO convert(FeedbackAuditDetailUpdateReqVO bean);

    FeedbackAuditDetailRespVO convert(FeedbackAuditDetailDO bean);

    FeedbackAuditDetailCreateReqVO convert01(FeedbackAuditDetailDO bean);

    FeedbackAuditDetailUpdateReqVO  convert02(FeedbackAuditDetailDO bean);

    List<FeedbackAuditDetailRespVO> convertList(List<FeedbackAuditDetailDO> list);

    PageResult<FeedbackAuditDetailRespVO> convertPage(PageResult<FeedbackAuditDetailDO> page);

    List<FeedbackAuditDetailExcelVO> convertList02(List<FeedbackAuditDetailDO> list);

}
