package com.dofast.module.mes.convert.exceptionlevelconfig;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo.*;
import com.dofast.module.mes.dal.dataobject.exceptionlevelconfig.ExceptionLevelConfigDO;

/**
 * 异常等级配置 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface ExceptionLevelConfigConvert {

    ExceptionLevelConfigConvert INSTANCE = Mappers.getMapper(ExceptionLevelConfigConvert.class);

    ExceptionLevelConfigDO convert(ExceptionLevelConfigCreateReqVO bean);

    ExceptionLevelConfigDO convert(ExceptionLevelConfigUpdateReqVO bean);

    ExceptionLevelConfigRespVO convert(ExceptionLevelConfigDO bean);

    List<ExceptionLevelConfigRespVO> convertList(List<ExceptionLevelConfigDO> list);

    PageResult<ExceptionLevelConfigRespVO> convertPage(PageResult<ExceptionLevelConfigDO> page);

    List<ExceptionLevelConfigExcelVO> convertList02(List<ExceptionLevelConfigDO> list);

}
