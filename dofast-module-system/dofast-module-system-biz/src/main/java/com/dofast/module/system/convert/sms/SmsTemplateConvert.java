package com.dofast.module.system.convert.sms;

import com.dofast.module.system.controller.admin.sms.vo.template.SmsTemplateCreateReqVO;
import com.dofast.module.system.controller.admin.sms.vo.template.SmsTemplateExcelVO;
import com.dofast.module.system.controller.admin.sms.vo.template.SmsTemplateRespVO;
import com.dofast.module.system.controller.admin.sms.vo.template.SmsTemplateUpdateReqVO;
import com.dofast.module.system.dal.dataobject.sms.SmsTemplateDO;
import com.dofast.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SmsTemplateConvert {

    SmsTemplateConvert INSTANCE = Mappers.getMapper(SmsTemplateConvert.class);

    SmsTemplateDO convert(SmsTemplateCreateReqVO bean);

    SmsTemplateDO convert(SmsTemplateUpdateReqVO bean);

    SmsTemplateRespVO convert(SmsTemplateDO bean);

    List<SmsTemplateRespVO> convertList(List<SmsTemplateDO> list);

    PageResult<SmsTemplateRespVO> convertPage(PageResult<SmsTemplateDO> page);

    List<SmsTemplateExcelVO> convertList02(List<SmsTemplateDO> list);

}
