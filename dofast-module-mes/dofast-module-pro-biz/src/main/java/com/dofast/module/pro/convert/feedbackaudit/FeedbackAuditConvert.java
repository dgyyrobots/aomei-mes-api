package com.dofast.module.pro.convert.feedbackaudit;

import java.util.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.pro.controller.admin.feedbackaudit.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudit.FeedbackAuditDO;

/**
 * 报工审批主表 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackAuditConvert {

    FeedbackAuditConvert INSTANCE = Mappers.getMapper(FeedbackAuditConvert.class);

    FeedbackAuditDO convert(FeedbackAuditCreateReqVO bean);

    FeedbackAuditDO convert(FeedbackAuditUpdateReqVO bean);

    FeedbackAuditRespVO convert(FeedbackAuditDO bean);

    FeedbackAuditCreateReqVO convert01(FeedbackAuditDO bean);

    FeedbackAuditUpdateReqVO convert02(FeedbackAuditDO bean);

    List<FeedbackAuditRespVO> convertList(List<FeedbackAuditDO> list);

    PageResult<FeedbackAuditRespVO> convertPage(PageResult<FeedbackAuditDO> page);

    List<FeedbackAuditExcelVO> convertList02(List<FeedbackAuditDO> list);

}
