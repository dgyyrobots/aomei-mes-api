package com.dofast.module.pro.controller.admin.task;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.dofast.framework.common.pojo.AjaxResult;
import com.dofast.framework.common.util.collection.CollectionUtils;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.cal.controller.admin.teammember.vo.TeamMemberExportReqVO;
import com.dofast.module.cal.dal.dataobject.team.TeamDO;
import com.dofast.module.cal.dal.dataobject.teammember.TeamMemberDO;
import com.dofast.module.cal.service.team.TeamService;
import com.dofast.module.cal.service.teammember.TeamMemberService;
import com.dofast.module.mes.api.WorkStationAPi.WorkStationApi;
import com.dofast.module.mes.api.WorkStationAPi.dto.WorkStationDTO;
import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.dal.dataobject.mdworkstationworker.MdWorkstationWorkerDO;
import com.dofast.module.mes.service.mdworkstationworker.MdWorkstationWorkerService;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderBaseVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderExportReqVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderListVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderUpdateReqVO;
import com.dofast.module.pro.controller.pad.protask.vo.PadTaskUpdateStatusReqVO;
import com.dofast.module.pro.convert.workorder.WorkorderConvert;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.process.ProcessDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.dal.mysql.task.TaskMapper;
import com.dofast.module.pro.enums.ErrorCodeConstants;
import com.dofast.module.pro.gantt.GanttData;
import com.dofast.module.pro.gantt.GanttLink;
import com.dofast.module.pro.gantt.GanttTask;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.process.ProcessService;
import com.dofast.module.pro.service.routeprocess.RouteProcessService;
import com.dofast.module.pro.service.workorder.WorkorderService;
import com.dofast.module.report.api.PrintLog.PrintLogApi;
import com.dofast.module.report.api.PrintLog.dto.PrintLogDTO;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.trade.api.mixinorder.MixinOrderApi;
import com.dofast.module.trade.api.mixinorder.dto.MixinOrderDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;
import static com.dofast.module.pro.enums.ErrorCodeConstants.TASK_IS_EXITS;
import static com.dofast.module.pro.enums.ErrorCodeConstants.UODATE_COME;

import com.dofast.module.pro.controller.admin.task.vo.*;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.module.pro.convert.task.TaskConvert;
import com.dofast.module.pro.service.task.TaskService;

@Tag(name = "生产管理 - 生产任务")
@RestController
@RequestMapping("/mes/pro/task")
@Validated
public class TaskController {

    @Resource
    private TaskService taskService;

    @Resource
    private WorkorderService workorderService;

    @Resource
    private ProcessService processService;

    @Resource
    private WorkStationApi workStationApi;

    @Resource
    private AutoCodeApi autoCodeApi;

    @Resource
    private MdWorkstationWorkerService workstationWorkerService;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private MixinOrderApi mixinOrderApi;

    @Resource
    private PrintLogApi printLogApi;

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private RouteProcessService routeProcessService;

    @Resource
    private TeamMemberService teamMemberService;

    @Resource
    private TeamService teamService;

    @Resource
    private TaskMapper taskMapper;


    @PostMapping("/create")
    @Operation(summary = "创建生产任务")
    @PreAuthorize("@ss.hasPermission('pro:task:create')")
    public CommonResult<Long> createTask(@Valid @RequestBody TaskCreateReqVO createReqVO) {
        if (createReqVO.getQuantity().doubleValue() < 0) {
            return error(ErrorCodeConstants.TASK_NUM_MORE_THAN_0);
        }

        WorkStationDTO workStationDTO = workStationApi.getWorkstation(createReqVO.getWorkstationCode());
        createReqVO.setWorkstationId(workStationDTO.getId());
        //生产工单
        WorkorderDO order = workorderService.getWorkorder(createReqVO.getWorkorderId());

        //判断该工序是否已经报工
        TaskExportReqVO taskExportReqVO = new TaskExportReqVO();
        taskExportReqVO.setWorkorderId(createReqVO.getWorkorderId());
        taskExportReqVO.setWorkorderCode(createReqVO.getWorkorderCode());
        taskExportReqVO.setProcessId(createReqVO.getProcessId());
        List<TaskDO> taskList = taskService.getTaskList(taskExportReqVO);
        if (taskList.size() > 0) {
            if (order.getQuantity() - order.getQuantityProduced() < createReqVO.getQuantity().doubleValue()) {
                return error(ErrorCodeConstants.WORKORDER_BIG_COUNT);
            }
            if (createReqVO.getQuantity().compareTo(BigDecimal.valueOf(order.getQuantity())) == 1) {
                return error(ErrorCodeConstants.WORKORDER_NUMBER_MORE);
            }
        }
        order.setQuantityProduced(order.getQuantityProduced() + createReqVO.getQuantity().doubleValue());
        WorkorderUpdateReqVO workorderUpdateReqVO = WorkorderConvert.INSTANCE.convert1(order);
        //判断该任务是否为关键工序
        if (routeProcessService.checkKeyProcess(BeanUtil.toBean(createReqVO, FeedbackDO.class))) {
            //如果是关键工序，则改变workorder工单的已排产数量
            workorderUpdateReqVO.setQuantityScheduled(createReqVO.getQuantity().doubleValue());
        }
        workorderService.updateWorkorder(workorderUpdateReqVO);

        createReqVO.setWorkorderCode(order.getWorkorderCode());
        createReqVO.setWorkorderName(order.getWorkorderName());
        createReqVO.setItemId(order.getProductId());
        createReqVO.setItemCode(order.getProductCode());
        createReqVO.setItemName(order.getProductName());
        createReqVO.setSpecification(order.getProductSpc());
        createReqVO.setUnitOfMeasure(order.getUnitOfMeasure());
        createReqVO.setClientId(order.getClientId());
        createReqVO.setClientCode(order.getClientCode());
        createReqVO.setClientName(order.getClientName());

        //工序信息
        if (createReqVO.getProcessCode() != null) {
            ProcessDO process = processService.getcess(createReqVO.getProcessCode());
            createReqVO.setProcessId(process.getId());
            createReqVO.setProcessCode(process.getProcessCode());
            createReqVO.setProcessName(process.getProcessName());
        } else {
            ProcessDO process = processService.getcess(createReqVO.getProcessId());
            createReqVO.setProcessId(process.getId());
            createReqVO.setProcessCode(process.getProcessCode());
            createReqVO.setProcessName(process.getProcessName());
        }
        //自动生成任务编号和名称
        createReqVO.setTaskCode(autoCodeApi.genSerialCode(Constant.TASK_CODE, null));
        createReqVO.setTaskName(
                new StringBuilder()
                        .append(createReqVO.getItemName())
                        .append("【").append(createReqVO.getQuantity().toString())
                        .append("】").append(createReqVO.getUnitOfMeasure()).toString());


        return success(taskService.createTask(createReqVO));
    }

    /**
     * 获取甘特图中需要显示的TASK，包括三种类型的内容：
     * 1.Project：基于时间范围搜索的生产工单转换而来的Project。
     * 搜索逻辑为：默认使用当前日期作为开始时间，搜索所有需求时间大于当前时间的生产工单
     * 2.Task：基于生产工单拆分到具体工作站后的生产任务转换而来的Task。
     * 3.Link：根据工序与工序之间的依赖关系转换而来的Link。
     */
    @Operation(summary = "获取甘特图中需要显示的TASK")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    @GetMapping("/listGanttTaskList")
    public CommonResult getGanttTaskList(WorkorderListVO proWorkorder) {
        GanttTask ganttTask = new GanttTask();
        List<GanttData> ganttData = new ArrayList<GanttData>();
        List<GanttLink> ganttLinks = new ArrayList<GanttLink>();

        //查询所有的WorkOrder
        List<WorkorderDO> workorders = workorderService.getWorkorderList(proWorkorder);


        //为每个workOrder生成type=project的GanttData
        //为每个proTask生产type=task的GanttData
        TaskListVO param = new TaskListVO();
        if (CollUtil.isNotEmpty(workorders)) {
            for (WorkorderDO workorder : workorders
            ) {
                if ("CONFIRMED".equals(workorder.getStatus())) {
                    //先添加当前的生产工单TASK
                    GanttData wdata = new GanttData();
                    wdata.setCode(workorder.getWorkorderCode());
                    wdata.setId("MO" + workorder.getId().toString());
                    wdata.setText(new StringBuilder().append(workorder.getProductName()).append(workorder.getQuantity().toString()).append(workorder.getUnitOfMeasure()).toString());//默认使用“[产品]+[数量]+[单位]”格式。
                    wdata.setProduct(workorder.getProductName());
                    wdata.setQuantity(BigDecimal.valueOf(workorder.getQuantity()));
                    if (workorder.getParentId().longValue() != 0L) {
                        wdata.setParent("MO" + workorder.getParentId().toString());
                    }
                    Double produced = workorder.getQuantityProduced();
                    Double quantitiy = workorder.getQuantity();
                    wdata.setProgress(BigDecimal.valueOf(produced).divide(BigDecimal.valueOf(quantitiy), BigDecimal.ROUND_HALF_UP).floatValue());
                    wdata.setDuration(0L);
                    wdata.setType(Constant.GANTT_TASK_TYPE_PROJECT);
                    if (wdata.getParent() == null || wdata.getParent().isEmpty())
                        wdata.setText(workorder.getWorkorderName());
                    ganttData.add(wdata);


                    //查询当前生产工单下所有的生产任务
                    param.setWorkorderId(workorder.getId());
                    List<TaskDO> proTasks = taskService.getTaskList(param);
                    proTasks = proTasks.stream()
                            .filter(task -> !task.getStatus().equals("FINISHED"))
                            .collect(Collectors.toList());

                    if (CollUtil.isNotEmpty(proTasks)) {
                        for (TaskDO task : proTasks
                        ) {
                            GanttData data = new GanttData();
                            data.setId(task.getId().toString());//使用生产任务的ID作为甘特图TASK的ID
                            data.setColor(task.getColorCode());
                            data.setCode(task.getTaskCode());
                            data.setDuration(task.getDuration());
                            data.setStart_date(task.getStartTime());

                            //data.setStart_date(oktime.format(new Date(task.getStartTime())));
                            data.setParent("MO" + workorder.getId().toString());//这里要设置为"MO+生产工单ID"的格式
                            data.setProduct(task.getItemName());
                            data.setQuantity(BigDecimal.valueOf(task.getQuantity()));
                            data.setProcess(task.getProcessName());
                            data.setWorkstation(task.getWorkstationName());
                            Double taskproduced = task.getQuantityProduced();
                            Double taskquantitiy = task.getQuantity();
                            data.setProgress(BigDecimal.valueOf(taskproduced).divide(BigDecimal.valueOf(taskquantitiy), BigDecimal.ROUND_HALF_UP).floatValue());
                            data.setType(Constant.GANTT_TASK_TYPE_TASK);
                            data.setText(new StringBuilder().append(task.getItemName()).append(task.getQuantity().toString()).append(task.getUnitOfMeasure()).toString()); //默认使用“[产品]+[数量]+[单位]”格式。
                            ganttData.add(data);
                        }
                    }
                }
            }
        }

        ganttTask.setData(ganttData);
        ganttTask.setLinks(ganttLinks);

        return success(ganttTask);
    }

    @PutMapping("/update")
    @Operation(summary = "更新生产任务")
    @PreAuthorize("@ss.hasPermission('pro:task:update')")
    public CommonResult<Boolean> updateTask(@Valid @RequestBody TaskUpdateReqVO updateReqVO) {
        if (updateReqVO.getQuantity().doubleValue() < 0) {
            return error(ErrorCodeConstants.TASK_NUM_MORE_THAN_0);
        }
        TaskDO task = taskService.getTask(updateReqVO.getId());
        task.setQuantityProduced(task.getQuantityProduced() + updateReqVO.getQuantityProduced().doubleValue());
        /*if(task.getQuantityProduced().compareTo(updateReqVO.getQuantity()) ==1){
            return error(ErrorCodeConstants.TASK_NUM_MORE);
        }*/
        taskService.updateTask(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生产任务")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:task:delete')")
    public CommonResult<Boolean> deleteTask(@RequestParam("id") Long id) {
        taskService.deleteTask(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得生产任务")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<TaskRespVO> getTask(@RequestParam("id") Long id) {
        TaskDO task = taskService.getTask(id);
        return success(TaskConvert.INSTANCE.convert(task));
    }

    @GetMapping("/list")
    @Operation(summary = "获得生产任务列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<List<TaskRespVO>> getTaskList(@RequestParam("ids") Collection<Long> ids) {
        List<TaskDO> list = taskService.getTaskList(ids);
        return success(TaskConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/count-wait-produce")
    @Operation(summary = "获得待排产总数")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<Integer> CountAll() {
        TaskExportReqVO taskExportReqVO = new TaskExportReqVO();
        taskExportReqVO.setStatus("NORMAL");
        List<TaskDO> list = taskService.getTaskList(taskExportReqVO);
        Integer result = list == null ? 0 : list.size();
        return success(result);
    }

    @GetMapping("/page")
    @Operation(summary = "获得生产任务分页")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<PageResult<TaskRespVO>> getTaskPage(@Valid TaskPageReqVO pageVO) {
        PageResult<TaskDO> pageResult = taskService.getTaskPage(pageVO);
        return success(TaskConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/pageBySourceCode")
    @Operation(summary = "根据SourceCode获得生产任务分页")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<PageResult<TaskRespVO>> getTaskPageBySourceCode(@Valid TaskPageReqVO pageVO) {
        PageResult<TaskDO> pageResult = taskService.getTaskPageBySourceCode(pageVO);
        return success(TaskConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出生产任务 Excel")
    @PreAuthorize("@ss.hasPermission('pro:task:export')")
    @OperateLog(type = EXPORT)
    public void exportTaskExcel(@Valid TaskExportReqVO exportReqVO,
                                HttpServletResponse response) throws IOException {
        List<TaskDO> list = taskService.getTaskList(exportReqVO);
        // 导出 Excel
        List<TaskExcelVO> datas = TaskConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "生产任务.xls", "数据", TaskExcelVO.class, datas);
    }

    @GetMapping("/get-my-task-page")
    @Operation(summary = "获得当前用户的生产任务分页")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<PageResult<TaskRespVO>> getMyTask(@Valid TaskPageReqVO pageVO) {
        PageResult<TaskDO> pageResult = taskService.getMyTask(pageVO);
        if (pageResult.getTotal() <= 0) {
            return success(TaskConvert.INSTANCE.convertPage(pageResult));
        }
        PageResult<TaskRespVO> taskRespVOPageResult = TaskConvert.INSTANCE.convertPage(pageResult);
        for (int i = 0; i < taskRespVOPageResult.getList().size(); i++) {
            taskRespVOPageResult.getList().get(i).setActualStartTime(pageResult.getList().get(i).getActualStartTime());
            taskRespVOPageResult.getList().get(i).setActualEndTime(pageResult.getList().get(i).getActualEndTime());
        }
        for (TaskRespVO taskRespVO : taskRespVOPageResult.getList()) {
            if (printLogApi.selectAllByPrintLog(taskRespVO.getTaskCode()).size() > 0) {
                taskRespVO.setIsPrint("1");
            } else {
                taskRespVO.setIsPrint("0");
            }
            taskRespVO.setIsReport(feedbackService.getFeedbackListByTaskId(taskRespVO.getId()).size());

            WorkorderDO workorder = workorderService.getWorkorder(taskRespVO.getWorkorderId());
            if(workorder!= null){
                String sourceCode = Optional.ofNullable(workorder.getSourceCode()).orElse(null);
                taskRespVO.setSourceCode(sourceCode);
            }
        }

        return success(taskRespVOPageResult);

    }

    @PutMapping("/change")
    @Operation(summary = "更新生产任务状态")
    @PreAuthorize("@ss.hasPermission('pro:task:update')")
    public CommonResult<Boolean> updateTaskStatus(@Valid @RequestBody TaskUpdateStatusReqVO proTask) {
        taskService.updateTaskStatus(proTask.getId(), proTask.getStatus());
        return success(true);
    }

    /**
     * 获取当前用户的生产任务
     */
    @Operation(summary = "获取当前用户的生产任务")
    @GetMapping("/get-task")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<List<TaskRespVO>> getMyTask(@RequestParam("no") String no) {
     /*   //判断当前用户的岗位
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        if (CollectionUtil.isEmpty(adminUserRespDTO.getPostIds())) {
            return error(ErrorCodeConstants.FEEDBACK_NOT_ACQUIRE);
        }


        //根据销售单号查询工单
//        MixinOrderDTO mixinOrderDTO = mixinOrderApi.getByNo(no);
        WorkorderExportReqVO workorderExportReqVO = new WorkorderExportReqVO();
//        workorderExportReqVO.setMixinOrderId(mixinOrderDTO.getId());
        workorderExportReqVO.setSourceCode(no);
        List<WorkorderDO> workorderDOS = workorderService.getWorkorderList(workorderExportReqVO);
        if (workorderDOS.isEmpty()){
            return error(ErrorCodeConstants.WORKORDER_NOT_EXIST);
        }
        List<TaskDO> taskDOS = new ArrayList<>();

        List<MdWorkstationWorkerDO>  workstationWorkers = workstationWorkerService.getMdWorkstationWorkerListByPostId(adminUserRespDTO.getPostIds());
        if (CollectionUtil.isEmpty(workstationWorkers)) {
            return error(ErrorCodeConstants.FEEDBACK_NOT_ACQUIRE);
        }


        Set<Long> workstationIds= CollectionUtils.convertSet(workstationWorkers, MdWorkstationWorkerDO::getWorkstationId);

        for (WorkorderDO workorderDO : workorderDOS){
            List<TaskDO> tasks = taskService.getTaskByOrder(workorderDO.getId());
            if (tasks == null){
                continue;
            }
            for (TaskDO task : tasks) {
                if (workstationIds.contains(task.getWorkstationId())){
                    taskDOS.add(task);
                }
            }

        }
        if (taskDOS.isEmpty()){
            return error(ErrorCodeConstants.FEEDBACK_NOT_ACQUIRE);
        }
        return success(TaskConvert.INSTANCE.convertList(taskDOS));*/

        // 2024-11-13改
        // 获取当前用户信息
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        // 获取当前用户所在的班组
        TeamMemberExportReqVO req = new TeamMemberExportReqVO();
        req.setUserId(adminUserRespDTO.getId());
        List<TeamMemberDO> memberDO = teamMemberService.getTeamMemberList(req);
        TeamDO team =  teamService.getTeam(memberDO.get(0).getTeamId());
        // 根据班组编码查询派工信息
        List<TaskDO> tasks = taskService.getTaskByTeamCode(team.getTeamCode());
        return success(TaskConvert.INSTANCE.convertList(tasks));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "更新生产任务的打印状态")
    @PreAuthorize("@ss.hasPermission('pro:task:update')")
    public CommonResult<Boolean> UpdatePrintById(@PathVariable("id") Long id) {
        Boolean b = taskService.updatePrintById(id);
        return success(b);
    }

    @PutMapping("/updateTeamById")
    @Operation(summary = "更新生产任务的打印状态")
    @PreAuthorize("@ss.hasPermission('pro:task:update')")
    public CommonResult<Boolean> updateTeamById(@RequestBody Map<String, Object> request) {
        String teamCode = (String) request.get("teamCode"); // 班组编码
        Integer taskId = (Integer) request.get("taskId"); // 任务ID
        TaskDO taskDO = taskService.getTask(taskId.longValue());
        taskDO.setAttr1(teamCode); // 存储班组编码
        taskService.updateTask(TaskConvert.INSTANCE.convert01(taskDO));
        return success(true);
    }

}
