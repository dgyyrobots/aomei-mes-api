package com.dofast.module.cal.convert.teammember;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cal.api.team.dto.TeamMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.cal.controller.admin.teammember.vo.*;
import com.dofast.module.cal.dal.dataobject.teammember.TeamMemberDO;

/**
 * 班组成员 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface TeamMemberConvert {

    TeamMemberConvert INSTANCE = Mappers.getMapper(TeamMemberConvert.class);

    TeamMemberDO convert(TeamMemberCreateReqVO bean);

    TeamMemberDO convert(TeamMemberUpdateReqVO bean);

    TeamMemberRespVO convert(TeamMemberDO bean);

    List<TeamMemberRespVO> convertList(List<TeamMemberDO> list);

    PageResult<TeamMemberRespVO> convertPage(PageResult<TeamMemberDO> page);

    List<TeamMemberExcelVO> convertList02(List<TeamMemberDO> list);

    List<TeamMemberDTO> convertList03(List<TeamMemberDO> list);


}
