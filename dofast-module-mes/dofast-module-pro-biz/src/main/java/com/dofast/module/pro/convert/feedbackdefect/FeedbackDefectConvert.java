package com.dofast.module.pro.convert.feedbackdefect;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.pro.controller.admin.feedbackdefect.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackdefect.FeedbackDefectDO;

/**
 * 报工缺陷 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackDefectConvert {

    FeedbackDefectConvert INSTANCE = Mappers.getMapper(FeedbackDefectConvert.class);

    FeedbackDefectDO convert(FeedbackDefectCreateReqVO bean);

    FeedbackDefectDO convert(FeedbackDefectUpdateReqVO bean);

    FeedbackDefectRespVO convert(FeedbackDefectDO bean);

    FeedbackDefectCreateReqVO convert01(FeedbackDefectDO bean);

    FeedbackDefectUpdateReqVO convert02(FeedbackDefectDO bean);

    List<FeedbackDefectRespVO> convertList(List<FeedbackDefectDO> list);

    PageResult<FeedbackDefectRespVO> convertPage(PageResult<FeedbackDefectDO> page);

    List<FeedbackDefectExcelVO> convertList02(List<FeedbackDefectDO> list);

}
