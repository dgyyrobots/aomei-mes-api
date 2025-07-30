package com.dofast.module.wms.convert.rtissueline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.wms.controller.admin.rtissueline.vo.*;
import com.dofast.module.wms.dal.dataobject.rtissueline.RtIssueLineDO;

/**
 * 生产退料单行 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface RtIssueLineConvert {

    RtIssueLineConvert INSTANCE = Mappers.getMapper(RtIssueLineConvert.class);

    RtIssueLineDO convert(RtIssueLineCreateReqVO bean);

    RtIssueLineDO convert(RtIssueLineUpdateReqVO bean);

    RtIssueLineRespVO convert(RtIssueLineDO bean);

    RtIssueLineUpdateReqVO convert01(RtIssueLineDO bean);

    List<RtIssueLineRespVO> convertList(List<RtIssueLineDO> list);

    PageResult<RtIssueLineRespVO> convertPage(PageResult<RtIssueLineDO> page);

    List<RtIssueLineExcelVO> convertList02(List<RtIssueLineDO> list);

}
