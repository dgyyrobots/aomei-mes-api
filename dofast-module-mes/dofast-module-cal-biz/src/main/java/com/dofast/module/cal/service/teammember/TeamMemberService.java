package com.dofast.module.cal.service.teammember;

import java.util.*;
import javax.validation.*;
import com.dofast.module.cal.controller.admin.teammember.vo.*;
import com.dofast.module.cal.dal.dataobject.teammember.TeamMemberDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 班组成员 Service 接口
 *
 * @author 惠智造
 */
public interface TeamMemberService {

    /**
     * 创建班组成员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTeamMember(@Valid TeamMemberCreateReqVO createReqVO);

    /**
     * 更新班组成员
     *
     * @param updateReqVO 更新信息
     */
    void updateTeamMember(@Valid TeamMemberUpdateReqVO updateReqVO);

    /**
     * 删除班组成员
     *
     * @param id 编号
     */
    void deleteTeamMember(Long id);

    /**
     * 获得班组成员
     *
     * @param id 编号
     * @return 班组成员
     */
    TeamMemberDO getTeamMember(Long id);

    /**
     * 获得班组成员
     *
     * @param id 编号
     * @return 班组成员
     */
    List<TeamMemberDO> getTeamMemberByTeamId(Long id);


    /**
     * 获得班组成员列表
     *
     * @param ids 编号
     * @return 班组成员列表
     */
    List<TeamMemberDO> getTeamMemberList(Collection<Long> ids);

    /**
     * 获得班组成员分页
     *
     * @param pageReqVO 分页查询
     * @return 班组成员分页
     */
    PageResult<TeamMemberDO> getTeamMemberPage(TeamMemberPageReqVO pageReqVO);

    /**
     * 获得班组成员列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 班组成员列表
     */
    List<TeamMemberDO> getTeamMemberList(TeamMemberExportReqVO exportReqVO);

    void updateBatchTeamMember(List<TeamMemberDO> updateList);


}
