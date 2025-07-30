package com.dofast.module.pro.service.task;

import com.dofast.framework.common.exception.ErrorCode;
import com.dofast.framework.common.util.date.LocalDateTimeUtils;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.cal.controller.admin.teammember.vo.TeamMemberExportReqVO;
import com.dofast.module.cal.dal.dataobject.team.TeamDO;
import com.dofast.module.cal.dal.dataobject.teammember.TeamMemberDO;
import com.dofast.module.cal.service.team.TeamService;
import com.dofast.module.cal.service.teammember.TeamMemberService;
import com.dofast.module.mes.dal.dataobject.mdworkstation.MdWorkstationDO;
import com.dofast.module.mes.dal.dataobject.mdworkstationworker.MdWorkstationWorkerDO;
import com.dofast.module.mes.service.mdworkstation.MdWorkstationService;
import com.dofast.module.mes.service.mdworkstationworker.MdWorkstationWorkerService;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.dal.mysql.task.TaskMapper;
import com.dofast.module.pro.enums.ErrorCodeConstants;
import com.dofast.module.pro.service.workorder.WorkorderService;
import com.dofast.module.report.api.PrintLog.PrintLogApi;
import com.dofast.module.report.api.PrintLog.dto.PrintLogDTO;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dofast.module.pro.controller.admin.task.vo.*;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.task.TaskConvert;
import org.springframework.web.util.WebUtils;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;

/**
 * 生产任务 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private MdWorkstationWorkerService workstationWorkerService;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private TeamMemberService teamMemberService;

    @Resource
    private TeamService teamService;


    @Override
    public Long createTask(TaskCreateReqVO createReqVO) {
        // 插入
        TaskDO task = TaskConvert.INSTANCE.convert(createReqVO);
        taskMapper.insert(task);
        // 返回
        return task.getId();
    }

    @Override
    public void updateTask(TaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateTaskExists(updateReqVO.getId());
        // 更新
        TaskDO updateObj = TaskConvert.INSTANCE.convert(updateReqVO);
        System.out.println(updateObj.getStatus());
        int i = taskMapper.updateById(updateObj);
        if (i <= 0) {
            throw exception(TASK_UPDATE_COUNT);
        }
    }

    @Override
    public void deleteTask(Long id) {
        // 校验存在
        validateTaskExists(id);
        // 删除
        taskMapper.deleteById(id);
    }

    private void validateTaskExists(Long id) {
        if (taskMapper.selectById(id) == null) {
            throw exception(TASK_NOT_EXISTS);
        }
    }

    @Override
    public TaskDO getTask(Long id) {
        return taskMapper.selectById(id);
    }

    @Override
    public TaskDO getTask(String taskCode){
        return taskMapper.selectOne(TaskDO::getTaskCode, taskCode);
    }


    @Resource
    private MdWorkstationService mdWorkstationService;

    @Resource
    private PrintLogApi printLogApi;

    @Override
    public PageResult<TaskDO> getMyTask(TaskPageReqVO pageReqVO) {
// 获取当前用户信息
//        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
//        List<MdWorkstationWorkerDO> list = workstationWorkerService.getMdWorkstationWorkerListByPostId(adminUserRespDTO.getPostIds());
//        if (list.size()<=0){
//            return new PageResult<TaskDO>().setList(null).setTotal(0L);
//        }
//        Collection<Long> workstationIds = list.stream()
//                .map(MdWorkstationWorkerDO::getWorkstationId) // 提取每个MdWorkstationWorkerDO对象的workstationId属性值
//                .distinct()
//                .collect(Collectors.toList()); // 将workstationId属性值收集到一个List<Long>集合中
//
//        PageResult<TaskDO> pageResult = taskMapper.selectPageByIds(pageReqVO, workstationIds);
//        pageResult.setList(pageResult.getList().stream().filter(v -> v.getStartTime().isBefore(LocalDateTime.MAX)).collect(Collectors.toList()));

        // 2024-11-13改
        // 获取当前用户信息
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        Set<Long> postIds =adminUserRespDTO.getPostIds();
        if(postIds.isEmpty()){
            return PageResult.empty();
        }
        // 校验postIds是否包含42(车间主任)
        if (postIds.contains(42L)) {
            // 获取所有的任务信息
            return taskMapper.selectPage(pageReqVO);
        }else{
            // 获取当前用户所在的班组
            TeamMemberExportReqVO req = new TeamMemberExportReqVO();
            req.setUserId(adminUserRespDTO.getId());
            List<TeamMemberDO> memberDO = teamMemberService.getTeamMemberList(req);
            if (memberDO.isEmpty()) {
                return PageResult.empty();
            }
            Set<Long> teamIds = new HashSet<>();
            for(TeamMemberDO member : memberDO){
                teamIds.add(member.getTeamId());
            }
            List<Long> teamList = new ArrayList<>(teamIds);
            List<TeamDO> teams = teamService.getTeamList(teamList);
            List<String> teamCodeList = teams.stream().map(TeamDO::getTeamCode).collect(Collectors.toList());

            /*TeamDO team = teamService.getTeam(memberDO.get(0).getTeamId());

            if (team == null) {
                return PageResult.empty();
            }*/
            // 根据班组编码查询派工信息
            return taskMapper.getTaskByTeamList(pageReqVO, teamCodeList);
        }

    }

    @Override
    public List<TaskDO> getMyTaskList(TaskExportReqVO taskExportReqVO) {
        //获取当前用户信息
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        List<MdWorkstationWorkerDO> list = workstationWorkerService.getMdWorkstationWorkerListByPostId(adminUserRespDTO.getPostIds());
        Collection<Long> workstationIds = list.stream()
                .map(MdWorkstationWorkerDO::getWorkstationId) // 提取每个MdWorkstationWorkerDO对象的workstationId属性值
                .distinct()
                .collect(Collectors.toList()); // 将workstationId属性值收集到一个List<Long>集合中
        List<TaskDO> taskDOS = taskMapper.selectList(taskExportReqVO, workstationIds);
        List<TaskDO> result = taskDOS.stream().filter(t -> !"FINISHED".equals(t.getStatus())).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<TaskDO> getTaskList(Collection<Long> ids) {
        return taskMapper.selectBatchIds(ids);
    }

    @Resource
    private WorkorderService workorderService;

    @Override
    public PageResult<TaskDO> getTaskPage(TaskPageReqVO pageReqVO) {
        return taskMapper.selectPage(pageReqVO);
    }

    public PageResult<TaskDO> getTaskPageBySourceCode(TaskPageReqVO pageReqVO) {
        List<WorkorderDO> workorderBySourceCode = workorderService.getWorkorderBySourceCode(pageReqVO.getSourceCode());
        List<Long> collect = workorderBySourceCode.stream().map(WorkorderDO::getId).collect(Collectors.toList());
        return taskMapper.selectPage(pageReqVO, collect);
    }

    @Override
    public List<TaskDO> getTaskList(TaskExportReqVO exportReqVO) {
        return taskMapper.selectList(exportReqVO);
    }

    @Override
    public List<TaskDO> getTaskList(TaskListVO listVO) {
        return taskMapper.selectList(listVO);
    }

    @Override
    public void updateTaskStatus(Long taskId, String status) {
        TaskDO taskDO = new TaskDO().setId(taskId).setStatus(status);
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (status.equals("STARTED")) {
            taskDO.setActualStartTime(currentDateTime);
        } else if (status.equals("FINISHED")) {
            taskDO.setActualEndTime(currentDateTime);
        }
        taskMapper.updateById(taskDO);
    }

    @Override
    public List<TaskDO> getTaskByOrder(Long workOrderId) {
        TaskExportReqVO taskExportReqVO = new TaskExportReqVO();
        taskExportReqVO.setWorkorderId(workOrderId);
        List<TaskDO> list = taskMapper.selectList(taskExportReqVO);

        if (list.isEmpty()) {
            return null;
        } else {

            /*for (TaskDO taskDO : list) {
                if (taskDO.getStatus().equals("FINISHED")){
                    throw exception(TASK_HAS_FINISHED);
                }else {
                    return ;
                }
            }*/
            List<TaskDO> finished = list.stream().filter(v -> !v.getStatus().equals("FINISHED")).collect(Collectors.toList());
            return finished;
        }
    }

    @Override
    public TaskDO getTask(Long id, Long processId) {
        return taskMapper.getTask(id, processId);
    }

    @Override
    public Boolean updatePrintById(Long id) {
        int i = taskMapper.updateById(new TaskDO().setId(id).setIsPrint("1"));
        return i > 0;
    }

    /**
     * 获得生产任务
     *
     * @param teamCode 班组编号
     * @return 生产任务
     */
    @Override
    public List<TaskDO> getTaskByTeamCode(String teamCode) {
        return taskMapper.getTaskByTeamCode(teamCode);
    }

    @Override
    public  Map<String, Integer>  getCountMonthTaskLastYear(){
        return taskMapper.getCountMonthTaskLastYear();
    }

    @Override
    public  Map<String, Integer>  getCountMonthTaskThisYear(){
        return taskMapper.getCountMonthTaskThisYear();
    }


}
