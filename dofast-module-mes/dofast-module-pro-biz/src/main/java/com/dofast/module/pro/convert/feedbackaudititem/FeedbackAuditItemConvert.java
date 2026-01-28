package com.dofast.module.pro.convert.feedbackaudititem;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.pro.controller.admin.feedbackaudititem.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;

/**
 * 报工审批汇总 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackAuditItemConvert {

    FeedbackAuditItemConvert INSTANCE = Mappers.getMapper(FeedbackAuditItemConvert.class);

    FeedbackAuditItemDO convert(FeedbackAuditItemCreateReqVO bean);

    FeedbackAuditItemDO convert(FeedbackAuditItemUpdateReqVO bean);

    FeedbackAuditItemRespVO convert(FeedbackAuditItemDO bean);

    FeedbackAuditItemCreateReqVO convert01(FeedbackAuditItemDO bean);

    FeedbackAuditItemUpdateReqVO convert02(FeedbackAuditItemDO bean);


    List<FeedbackAuditItemRespVO> convertList(List<FeedbackAuditItemDO> list);

    PageResult<FeedbackAuditItemRespVO> convertPage(PageResult<FeedbackAuditItemDO> page);

    List<FeedbackAuditItemExcelVO> convertList02(List<FeedbackAuditItemDO> list);

}
