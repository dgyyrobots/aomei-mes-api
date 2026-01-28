package com.dofast.module.cal.convert.teamschedule;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.cal.controller.admin.teamschedule.vo.*;
import com.dofast.module.cal.dal.dataobject.teamschedule.TeamScheduleDO;

/**
 * 班组排班 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface TeamScheduleConvert {

    TeamScheduleConvert INSTANCE = Mappers.getMapper(TeamScheduleConvert.class);

    TeamScheduleDO convert(TeamScheduleCreateReqVO bean);

    TeamScheduleDO convert(TeamScheduleUpdateReqVO bean);

    TeamScheduleRespVO convert(TeamScheduleDO bean);

    List<TeamScheduleRespVO> convertList(List<TeamScheduleDO> list);

    PageResult<TeamScheduleRespVO> convertPage(PageResult<TeamScheduleDO> page);

    List<TeamScheduleExcelVO> convertList02(List<TeamScheduleDO> list);

}
