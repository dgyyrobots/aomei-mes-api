package com.dofast.module.pro.convert.feedbackmember;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.pro.controller.admin.feedbackmember.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackmember.FeedbackMemberDO;

/**
 * 报工班组人员 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackMemberConvert {

    FeedbackMemberConvert INSTANCE = Mappers.getMapper(FeedbackMemberConvert.class);

    FeedbackMemberDO convert(FeedbackMemberCreateReqVO bean);

    FeedbackMemberDO convert(FeedbackMemberUpdateReqVO bean);

    FeedbackMemberRespVO convert(FeedbackMemberDO bean);

    FeedbackMemberCreateReqVO convert01(FeedbackMemberDO bean);

    FeedbackMemberUpdateReqVO convert02(FeedbackMemberDO bean);

    List<FeedbackMemberRespVO> convertList(List<FeedbackMemberDO> list);

    PageResult<FeedbackMemberRespVO> convertPage(PageResult<FeedbackMemberDO> page);

    List<FeedbackMemberExcelVO> convertList02(List<FeedbackMemberDO> list);

}
