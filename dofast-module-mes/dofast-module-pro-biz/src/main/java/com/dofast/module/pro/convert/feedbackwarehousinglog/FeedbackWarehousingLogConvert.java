package com.dofast.module.pro.convert.feedbackwarehousinglog;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackwarehousinglog.FeedbackWarehousingLogDO;

/**
 * 报工入库日志 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface FeedbackWarehousingLogConvert {

    FeedbackWarehousingLogConvert INSTANCE = Mappers.getMapper(FeedbackWarehousingLogConvert.class);

    FeedbackWarehousingLogDO convert(FeedbackWarehousingLogCreateReqVO bean);

    FeedbackWarehousingLogDO convert(FeedbackWarehousingLogUpdateReqVO bean);

    FeedbackWarehousingLogRespVO convert(FeedbackWarehousingLogDO bean);

    List<FeedbackWarehousingLogRespVO> convertList(List<FeedbackWarehousingLogDO> list);

    PageResult<FeedbackWarehousingLogRespVO> convertPage(PageResult<FeedbackWarehousingLogDO> page);

    List<FeedbackWarehousingLogExcelVO> convertList02(List<FeedbackWarehousingLogDO> list);

}
