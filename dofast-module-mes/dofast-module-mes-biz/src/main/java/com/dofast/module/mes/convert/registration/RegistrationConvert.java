package com.dofast.module.mes.convert.registration;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.mes.controller.admin.registration.vo.*;
import com.dofast.module.mes.dal.dataobject.registration.RegistrationDO;

/**
 * 计时登记 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface RegistrationConvert {

    RegistrationConvert INSTANCE = Mappers.getMapper(RegistrationConvert.class);

    RegistrationDO convert(RegistrationCreateReqVO bean);

    RegistrationDO convert(RegistrationUpdateReqVO bean);

    RegistrationRespVO convert(RegistrationDO bean);

    RegistrationUpdateReqVO convert01(RegistrationDO bean);

    List<RegistrationRespVO> convertList(List<RegistrationDO> list);

    PageResult<RegistrationRespVO> convertPage(PageResult<RegistrationDO> page);

    List<RegistrationExcelVO> convertList02(List<RegistrationDO> list);

}
