package com.dofast.module.pro.convert.feedback;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.controller.admin.task.vo.TaskUpdateReqVO;
import com.dofast.module.pro.controller.pad.protask.vo.ProTaskFeedbackReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.pro.controller.admin.feedback.vo.*;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;

/**
 * 生产报工记录 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FeedbackConvert {

    FeedbackConvert INSTANCE = Mappers.getMapper(FeedbackConvert.class);

    FeedbackDO convert(FeedbackCreateReqVO bean);

    FeedbackDO convert(ProTaskFeedbackReqVO bean);

    FeedbackDO convert(FeedbackUpdateReqVO bean);

    FeedbackRespVO convert(FeedbackDO bean);

    FeedbackCreateReqVO convert01(FeedbackDO bean);

    FeedbackUpdateReqVO convert02(FeedbackDO bean);

    FeedbackCreateReqVO convert03(FeedbackUpdateReqVO bean);


    List<FeedbackRespVO> convertList(List<FeedbackDO> list);

    PageResult<FeedbackRespVO> convertPage(PageResult<FeedbackDO> page);

    List<FeedbackExcelVO> convertList02(List<FeedbackDO> list);

}
