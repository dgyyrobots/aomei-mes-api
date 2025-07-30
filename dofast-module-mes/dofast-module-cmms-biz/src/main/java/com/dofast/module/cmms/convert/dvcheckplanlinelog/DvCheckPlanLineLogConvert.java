package com.dofast.module.cmms.convert.dvcheckplanlinelog;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanlinelog.DvCheckPlanLineLogDO;

/**
 * 点检计划记录单身 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface DvCheckPlanLineLogConvert {

    DvCheckPlanLineLogConvert INSTANCE = Mappers.getMapper(DvCheckPlanLineLogConvert.class);

    DvCheckPlanLineLogDO convert(DvCheckPlanLineLogCreateReqVO bean);

    DvCheckPlanLineLogDO convert(DvCheckPlanLineLogUpdateReqVO bean);

    DvCheckPlanLineLogRespVO convert(DvCheckPlanLineLogDO bean);

    List<DvCheckPlanLineLogRespVO> convertList(List<DvCheckPlanLineLogDO> list);

    PageResult<DvCheckPlanLineLogRespVO> convertPage(PageResult<DvCheckPlanLineLogDO> page);

    List<DvCheckPlanLineLogExcelVO> convertList02(List<DvCheckPlanLineLogDO> list);

}
