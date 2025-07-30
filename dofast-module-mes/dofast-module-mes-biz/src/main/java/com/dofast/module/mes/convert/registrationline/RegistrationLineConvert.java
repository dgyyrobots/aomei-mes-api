package com.dofast.module.mes.convert.registrationline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.mes.controller.admin.registrationline.vo.*;
import com.dofast.module.mes.dal.dataobject.registrationline.RegistrationLineDO;

/**
 * 计时登记记录 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface RegistrationLineConvert {

    RegistrationLineConvert INSTANCE = Mappers.getMapper(RegistrationLineConvert.class);

    RegistrationLineDO convert(RegistrationLineCreateReqVO bean);

    RegistrationLineDO convert(RegistrationLineUpdateReqVO bean);

    RegistrationLineRespVO convert(RegistrationLineDO bean);

    RegistrationLineCreateReqVO  convert01(RegistrationLineDO bean);

    RegistrationLineUpdateReqVO  convert02(RegistrationLineDO bean);

    List<RegistrationLineRespVO> convertList(List<RegistrationLineDO> list);

    PageResult<RegistrationLineRespVO> convertPage(PageResult<RegistrationLineDO> page);

    List<RegistrationLineExcelVO> convertList02(List<RegistrationLineDO> list);

}
