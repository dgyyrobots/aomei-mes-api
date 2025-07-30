package com.dofast.module.mes.convert.subclassexception;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.mes.controller.admin.subclassexception.vo.*;
import com.dofast.module.mes.dal.dataobject.subclassexception.SubclassExceptionDO;

/**
 * 子类异常项配置 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface SubclassExceptionConvert {

    SubclassExceptionConvert INSTANCE = Mappers.getMapper(SubclassExceptionConvert.class);

    SubclassExceptionDO convert(SubclassExceptionCreateReqVO bean);

    SubclassExceptionDO convert(SubclassExceptionUpdateReqVO bean);

    SubclassExceptionRespVO convert(SubclassExceptionDO bean);

    List<SubclassExceptionRespVO> convertList(List<SubclassExceptionDO> list);

    PageResult<SubclassExceptionRespVO> convertPage(PageResult<SubclassExceptionDO> page);

    List<SubclassExceptionExcelVO> convertList02(List<SubclassExceptionDO> list);

}
