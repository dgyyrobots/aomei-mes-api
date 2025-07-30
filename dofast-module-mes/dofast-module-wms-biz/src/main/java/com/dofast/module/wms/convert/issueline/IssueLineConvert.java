package com.dofast.module.wms.convert.issueline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.api.Issueheader.dto.IssueLineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.wms.controller.admin.issueline.vo.*;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;

/**
 * 生产领料单行 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface IssueLineConvert {

    IssueLineConvert INSTANCE = Mappers.getMapper(IssueLineConvert.class);

    IssueLineDO convert(IssueLineCreateReqVO bean);

    IssueLineDO convert(IssueLineUpdateReqVO bean);

    IssueLineRespVO convert(IssueLineDO bean);

    IssueLineUpdateReqVO convert01(IssueLineDO bean);

    IssueLineUpdateReqVO convert02(IssueLineDTO bean);

    List<IssueLineRespVO> convertList(List<IssueLineDO> list);

    PageResult<IssueLineRespVO> convertPage(PageResult<IssueLineDO> page);

    List<IssueLineExcelVO> convertList02(List<IssueLineDO> list);

}
