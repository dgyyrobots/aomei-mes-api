package com.dofast.module.cal.service.teamschedule;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.cal.controller.admin.teamschedule.vo.*;
import com.dofast.module.cal.dal.dataobject.teamschedule.TeamScheduleDO;
import com.dofast.module.cal.dal.mysql.teamschedule.TeamScheduleMapper;
import com.dofast.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

import static cn.hutool.core.util.RandomUtil.*;
import static com.dofast.module.cal.enums.ErrorCodeConstants.*;
import static com.dofast.framework.test.core.util.AssertUtils.*;
import static com.dofast.framework.test.core.util.RandomUtils.*;
import static com.dofast.framework.common.util.date.LocalDateTimeUtils.*;
import static com.dofast.framework.common.util.object.ObjectUtils.*;
import static com.dofast.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link TeamScheduleServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(TeamScheduleServiceImpl.class)
public class TeamScheduleServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TeamScheduleServiceImpl teamScheduleService;

    @Resource
    private TeamScheduleMapper teamScheduleMapper;

    @Test
    public void testCreateTeamSchedule_success() {
        // 准备参数
        TeamScheduleCreateReqVO reqVO = randomPojo(TeamScheduleCreateReqVO.class);

        // 调用
        Long teamScheduleId = teamScheduleService.createTeamSchedule(reqVO);
        // 断言
        assertNotNull(teamScheduleId);
        // 校验记录的属性是否正确
        TeamScheduleDO teamSchedule = teamScheduleMapper.selectById(teamScheduleId);
        assertPojoEquals(reqVO, teamSchedule);
    }

    @Test
    public void testUpdateTeamSchedule_success() {
        // mock 数据
        TeamScheduleDO dbTeamSchedule = randomPojo(TeamScheduleDO.class);
        teamScheduleMapper.insert(dbTeamSchedule);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TeamScheduleUpdateReqVO reqVO = randomPojo(TeamScheduleUpdateReqVO.class, o -> {
            o.setId(dbTeamSchedule.getId()); // 设置更新的 ID
        });

        // 调用
        teamScheduleService.updateTeamSchedule(reqVO);
        // 校验是否更新正确
        TeamScheduleDO teamSchedule = teamScheduleMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, teamSchedule);
    }

    @Test
    public void testUpdateTeamSchedule_notExists() {
        // 准备参数
        TeamScheduleUpdateReqVO reqVO = randomPojo(TeamScheduleUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> teamScheduleService.updateTeamSchedule(reqVO), TEAM_SCHEDULE_NOT_EXISTS);
    }

    @Test
    public void testDeleteTeamSchedule_success() {
        // mock 数据
        TeamScheduleDO dbTeamSchedule = randomPojo(TeamScheduleDO.class);
        teamScheduleMapper.insert(dbTeamSchedule);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTeamSchedule.getId();

        // 调用
        teamScheduleService.deleteTeamSchedule(id);
       // 校验数据不存在了
       assertNull(teamScheduleMapper.selectById(id));
    }

    @Test
    public void testDeleteTeamSchedule_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> teamScheduleService.deleteTeamSchedule(id), TEAM_SCHEDULE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTeamSchedulePage() {
       // mock 数据
       TeamScheduleDO dbTeamSchedule = randomPojo(TeamScheduleDO.class, o -> { // 等会查询到
           o.setTeamId(null);
           o.setUserId(null);
           o.setWorkDate(null);
           o.setShiftType(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       teamScheduleMapper.insert(dbTeamSchedule);
       // 测试 teamId 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setTeamId(null)));
       // 测试 userId 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setUserId(null)));
       // 测试 workDate 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setWorkDate(null)));
       // 测试 shiftType 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setShiftType(null)));
       // 测试 remark 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setCreateTime(null)));
       // 准备参数
       TeamSchedulePageReqVO reqVO = new TeamSchedulePageReqVO();
       reqVO.setTeamId(null);
       reqVO.setUserId(null);
       reqVO.setWorkDate(LocalDate.now());
       reqVO.setShiftType(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<TeamScheduleDO> pageResult = teamScheduleService.getTeamSchedulePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbTeamSchedule, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTeamScheduleList() {
       // mock 数据
       TeamScheduleDO dbTeamSchedule = randomPojo(TeamScheduleDO.class, o -> { // 等会查询到
           o.setTeamId(null);
           o.setUserId(null);
           o.setWorkDate(null);
           o.setShiftType(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       teamScheduleMapper.insert(dbTeamSchedule);
       // 测试 teamId 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setTeamId(null)));
       // 测试 userId 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setUserId(null)));
       // 测试 workDate 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setWorkDate(null)));
       // 测试 shiftType 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setShiftType(null)));
       // 测试 remark 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       teamScheduleMapper.insert(cloneIgnoreId(dbTeamSchedule, o -> o.setCreateTime(null)));
       // 准备参数
       TeamScheduleExportReqVO reqVO = new TeamScheduleExportReqVO();
       reqVO.setTeamId(null);
       reqVO.setUserId(null);
       reqVO.setWorkDate(LocalDate.now());
       reqVO.setShiftType(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<TeamScheduleDO> list = teamScheduleService.getTeamScheduleList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbTeamSchedule, list.get(0));
    }

}
