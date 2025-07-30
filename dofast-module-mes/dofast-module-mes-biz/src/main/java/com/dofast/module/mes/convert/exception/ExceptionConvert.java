package com.dofast.module.mes.convert.exception;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.mes.controller.admin.exception.vo.*;
import com.dofast.module.mes.dal.dataobject.exception.ExceptionDO;

/**
 * 异常登记 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface ExceptionConvert {

    ExceptionConvert INSTANCE = Mappers.getMapper(ExceptionConvert.class);

    ExceptionDO convert(ExceptionCreateReqVO bean);

    ExceptionDO convert(ExceptionUpdateReqVO bean);

    ExceptionRespVO convert(ExceptionDO bean);

    List<ExceptionRespVO> convertList(List<ExceptionDO> list);

    PageResult<ExceptionRespVO> convertPage(PageResult<ExceptionDO> page);

    List<ExceptionExcelVO> convertList02(List<ExceptionDO> list);

}
