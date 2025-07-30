package com.dofast.module.cmms.convert.dvcheckplanheaderlog;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanheaderlog.DvCheckPlanHeaderLogDO;

/**
 * 点检计划记录单头 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface DvCheckPlanHeaderLogConvert {

    DvCheckPlanHeaderLogConvert INSTANCE = Mappers.getMapper(DvCheckPlanHeaderLogConvert.class);

    DvCheckPlanHeaderLogDO convert(DvCheckPlanHeaderLogCreateReqVO bean);

    DvCheckPlanHeaderLogDO convert(DvCheckPlanHeaderLogUpdateReqVO bean);

    DvCheckPlanHeaderLogRespVO convert(DvCheckPlanHeaderLogDO bean);

    List<DvCheckPlanHeaderLogRespVO> convertList(List<DvCheckPlanHeaderLogDO> list);

    PageResult<DvCheckPlanHeaderLogRespVO> convertPage(PageResult<DvCheckPlanHeaderLogDO> page);

    List<DvCheckPlanHeaderLogExcelVO> convertList02(List<DvCheckPlanHeaderLogDO> list);

}
