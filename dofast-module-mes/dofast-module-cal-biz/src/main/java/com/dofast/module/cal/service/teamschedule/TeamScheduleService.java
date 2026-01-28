package com.dofast.module.cal.service.teamschedule;

import java.util.*;
import javax.validation.*;
import com.dofast.module.cal.controller.admin.teamschedule.vo.*;
import com.dofast.module.cal.dal.dataobject.teamschedule.TeamScheduleDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 班组排班 Service 接口
 *
 * @author 惠智造
 */
public interface TeamScheduleService {

    /**
     * 创建班组排班
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTeamSchedule(@Valid TeamScheduleCreateReqVO createReqVO);

    /**
     * 更新班组排班
     *
     * @param updateReqVO 更新信息
     */
    void updateTeamSchedule(@Valid TeamScheduleUpdateReqVO updateReqVO);

    /**
     * 删除班组排班
     *
     * @param id 编号
     */
    void deleteTeamSchedule(Long id);

    /**
     * 获得班组排班
     *
     * @param id 编号
     * @return 班组排班
     */
    TeamScheduleDO getTeamSchedule(Long id);

    /**
     * 获得班组排班列表
     *
     * @param ids 编号
     * @return 班组排班列表
     */
    List<TeamScheduleDO> getTeamScheduleList(Collection<Long> ids);

    /**
     * 获得班组排班分页
     *
     * @param pageReqVO 分页查询
     * @return 班组排班分页
     */
    PageResult<TeamScheduleDO> getTeamSchedulePage(TeamSchedulePageReqVO pageReqVO);

    /**
     * 获得班组排班列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 班组排班列表
     */
    List<TeamScheduleDO> getTeamScheduleList(TeamScheduleExportReqVO exportReqVO);

}
