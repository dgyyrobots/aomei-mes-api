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
import com.dofast.module.cmms.api.dvmachinery.DvMachineryApi;
import com.dofast.module.mes.api.WorkStationAPi.WorkStationApi;
import com.dofast.module.mes.api.WorkStationAPi.dto.WorkStationDTO;
import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.dataobject.mdworkstationworker.MdWorkstationWorkerDO;
import com.dofast.module.mes.service.mditem.MdItemService;
import com.dofast.module.mes.service.mdworkstationworker.MdWorkstationWorkerService;
import com.dofast.module.pro.controller.admin.routeprocess.vo.RouteProcessExportReqVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderBaseVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderExportReqVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderListVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderUpdateReqVO;
import com.dofast.module.pro.controller.pad.protask.vo.PadTaskUpdateStatusReqVO;
import com.dofast.module.pro.convert.workorder.WorkorderConvert;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.process.ProcessDO;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;
import com.dofast.module.pro.dal.dataobject.routeprocess.RouteProcessDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.dal.mysql.route.RouteMapper;
import com.dofast.module.pro.dal.mysql.routeprocess.RouteProcessMapper;
import com.dofast.module.pro.dal.mysql.routeproduct.RouteProductMapper;
import com.dofast.module.pro.dal.mysql.task.TaskMapper;
import com.dofast.module.pro.enums.ErrorCodeConstants;
import com.dofast.module.pro.gantt.GanttData;
import com.dofast.module.pro.gantt.GanttLink;
import com.dofast.module.pro.gantt.GanttTask;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.process.ProcessService;
import com.dofast.module.pro.service.route.RouteOracleService;
import com.dofast.module.pro.service.route.RouteService;
import com.dofast.module.pro.service.routeprocess.RouteProcessService;
import com.dofast.module.pro.service.task.TaskOracleService;
import com.dofast.module.pro.service.workorder.WorkorderService;
import com.dofast.module.report.api.PrintLog.PrintLogApi;
import com.dofast.module.report.api.PrintLog.dto.PrintLogDTO;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.trade.api.mixinorder.MixinOrderApi;
import com.dofast.module.trade.api.mixinorder.dto.MixinOrderDTO;
import com.dofast.module.wms.controller.admin.issueheader.vo.IssueHeaderExportReqVO;
import com.dofast.module.wms.controller.admin.issueline.vo.IssueLineExportReqVO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.issueline.IssueLineService;
import dm.jdbc.util.StringUtil;
import org.apache.tika.utils.StringUtils;
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

    @Resource
    private IssueHeaderService issueHeaderService;

    @Resource
    private IssueLineService issueLineService;

    @Resource
    private  DvMachineryApi dvMachineryApi;

    @Resource
    private MdItemService mdItemService;

    @Resource
    private TaskOracleService taskOracleService;

    @Resource
    private RouteService routeService;

    @Resource
    private RouteMapper routeMapper;

    @Resource
    private RouteProcessMapper routeProcessMapper;

    @Resource
    private RouteProductMapper routeProductMapper;

    @Resource
    private RouteOracleService routeOracleService;

    @PostMapping("/create")
    @Operation(summary = "创建生产任务")
    @PreAuthorize("@ss.hasPermission('pro:task:create')")
    public CommonResult<Long> createTask(@Valid @RequestBody TaskCreateReqVO createReqVO) {
        if (createReqVO.getQuantity().doubleValue() < 0) {
            return error(ErrorCodeConstants.TASK_NUM_MORE_THAN_0);
        }

        ProcessDO processDO = processService.getcess(createReqVO.getProcessId());
        if(processDO == null){
            return error(ErrorCodeConstants.CESS_NOT_EXISTS);
        }

        WorkStationDTO workStationDTO = workStationApi.getWorkstation(createReqVO.getWorkstationCode(), processDO.getProcessCode());
        createReqVO.setWorkstationId(workStationDTO.getId());
        //生产工单
        WorkorderDO order = workorderService.getWorkorder(createReqVO.getWorkorderId());
        // 2025-10-16 工单工作需改为任务单创建时赋予
        String fullRouteCode =  order.getProductCode() + "-" + order.getRouteCode();
        // 获取工艺路线
        RouteDO routeDO = routeMapper.selectOne(RouteDO::getRouteCode, fullRouteCode);
        if(routeDO != null) {
            // 获取工艺路线工序
            List<RouteProcessDO> routeProcessDOList = routeProcessMapper.selectList(RouteProcessDO::getRouteId, routeDO.getId());
            if (!routeProcessDOList.isEmpty()) {
                for (RouteProcessDO routeProcess : routeProcessDOList) {
                    // 获取工作序
                    Map<String, Object> workOderSequence = routeOracleService.initWorkSequence(order.getWorkorderCode(), routeProcess.getProcessCode(), String.valueOf(routeProcess.getSequence()));
                    if (workOderSequence == null) {
                        continue;
                    }
                    String workorderSequence = (String) workOderSequence.get("WORKORDER_SEQUENCE");
                    createReqVO.setWorkorderSequence(Long.valueOf(workorderSequence));
                }
            }
        }

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
        // 2025-01-13改: 工单生产数量由报工进行管控
        /*order.setQuantityProduced(order.getQuantityProduced() + createReqVO.getQuantity().doubleValue());
        WorkorderUpdateReqVO workorderUpdateReqVO = WorkorderConvert.INSTANCE.convert1(order);
        //判断该任务是否为关键工序
        if (routeProcessService.checkKeyProcess(BeanUtil.toBean(createReqVO, FeedbackDO.class))) {
            //如果是关键工序，则改变workorder工单的已排产数量
            workorderUpdateReqVO.setQuantityScheduled(createReqVO.getQuantity().doubleValue());
        }
        workorderService.updateWorkorder(workorderUpdateReqVO);*/

        createReqVO.setWorkorderCode(order.getWorkorderCode());
        createReqVO.setWorkorderName(order.getWorkorderName());
        createReqVO.setItemId(order.getProductId());
        createReqVO.setItemCode(order.getProductCode());
        createReqVO.setItemName(order.getProductName());
        createReqVO.setSpecification(order.getProductSpc());
        // createReqVO.setUnitOfMeasure(order.getUnitOfMeasure());
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
        String status = updateReqVO.getTaskStatus();
        updateReqVO.setStatus(status);
       /* TaskDO task = taskService.getTask(updateReqVO.getId());
        task.setQuantityProduced(task.getQuantityProduced() + updateReqVO.getQuantityProduced().doubleValue());*/

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

    @GetMapping("/initChangeInfo")
    @Operation(summary = "获得生产任务转换数量")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<Map<String, Object>> initTaskChangeQuantity(@RequestParam("workorderCode") String workorderCode ,@RequestParam("processCode") String processCode) {

        WorkorderDO workorder = workorderService.getWorkorder(workorderCode);
        // 判定工单开头是否为MO
        String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
        // 获取工艺路线详情
        RouteDO route = routeService.getRoute(routeCode);
        if(route == null){
            return error(ErrorCodeConstants.ROUTE_NOT_EXISTS);
        }
        RouteProcessExportReqVO routeProcessExportReqVO = new RouteProcessExportReqVO();
        routeProcessExportReqVO.setRouteId(route.getId());
        routeProcessExportReqVO.setProcessCode(processCode);
        List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(routeProcessExportReqVO);
        if(routeProcess.isEmpty()){
            return error(ErrorCodeConstants.ROUTE_PROCESS_NOT_EXISTS);
        }
        RouteProcessDO process = routeProcess.get(0);
        String nextProcessCode = Optional.ofNullable(routeProcess.get(0).getNextProcessCode()).orElse(null);

        if(nextProcessCode == null && !"张".equals(routeProcess.get(0).getOutUnits())){// 若当前末工序产出为张, 则无需变更
            // 末工序取上道制程转换数量
            routeProcessExportReqVO.setProcessCode(null);
            List<RouteProcessDO> queryFirstRouteProcess = routeProcessService.getRouteProcessList(routeProcessExportReqVO);
            if(queryFirstRouteProcess.isEmpty()){
                return error(ErrorCodeConstants.ROUTE_PROCESS_NOT_EXISTS);
            }
            queryFirstRouteProcess.sort(Comparator.comparingInt(RouteProcessDO::getOrderNum));
            process = queryFirstRouteProcess.get(0);
            processCode = process.getProcessCode();
        }

        Map<String, Object> requestMap = taskOracleService.getChangeQuantity(workorderCode,processCode);
        // 卡控requestMap不为空
        if (!workorderCode.startsWith("MO")) {
            if (requestMap == null) {
                return error(ErrorCodeConstants.TASK_CHANGE_QUANTITY_ERROR);
            }
        }

        return success(requestMap);
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
       /* for(TaskDO taskDO:pageResult.getList()){
            String teamCode = taskDO.getAttr1();
            // 基于班组获取设备信息
            TeamDO team = teamService.getTeam(teamCode);
            if (team == null) {
                continue;
            }
            taskDO.setMachineryName(team.getMachineryName());
            taskDO.setMachineryCode(team.getMachineryCode());
            taskDO.setMachineryId(String.valueOf(team.getMachineryId()));
        }*/
        PageResult<TaskRespVO> pageResultVO = TaskConvert.INSTANCE.convertPage(pageResult);
        return success(pageResultVO);
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
        PageResult<TaskDO> pageResult = Optional.ofNullable(taskService.getMyTask(pageVO)).orElse(null);
        if(pageResult==null){
            return success(TaskConvert.INSTANCE.convertPage(pageResult));
        }
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
    public CommonResult<Boolean> updateTaskStatus(@Valid @RequestBody TaskUpdateReqVO proTask) {
        taskService.updateTask(proTask);
        //taskService.updateTaskStatus(proTask.getId(), proTask.getStatus());
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
        if (!tasks.isEmpty()) {
            return success(TaskConvert.INSTANCE.convertList(tasks));
        }else{
            return success(new ArrayList<>());
        }
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
        List<String> machineryCodes = (List<String>) request.get("machineryCodes");

        TaskDO taskDO = taskService.getTask(taskId.longValue());
        taskDO.setAttr1(teamCode); // 存储班组编码

        String machineryCodesStr = machineryCodes != null ? machineryCodes.toString() : null;
        taskDO.setMachineryCodes(machineryCodesStr);

        taskDO.setMachineryId(taskDO.getMachineryId());
        taskDO.setMachineryName(taskDO.getMachineryName());
        taskDO.setMachineryCode(taskDO.getMachineryCode());

        // 根据选取的班组信息同步更新任务单的机台设备
        TeamDO team = teamService.getTeam(taskDO.getAttr1());
        taskDO.setMachineryCode(team.getMachineryCode());
        taskDO.setMachineryName(team.getMachineryName());
        taskDO.setTaskStatus("Y");
        taskDO.setMachineryId(String.valueOf(team.getMachineryId())); //更新任务单的机台设备ID
        taskService.updateTask(TaskConvert.INSTANCE.convert01(taskDO));
        return success(true);
    }


    @GetMapping("/count-month-task-lastYear")
    @Operation(summary = "获取任务单去年产出总额")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<Map<String, Integer>> CountMonthTaskLastYear() {
        return success(taskService.getCountMonthTaskLastYear());
    }

    @GetMapping("/count-month-task-thisYear")
    @Operation(summary = "获取任务单今年产出总额")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<Map<String, Integer>> CountMonthTaskThisYear() {
        return success(taskService.getCountMonthTaskThisYear());
    }

    @GetMapping("/getTaskDetail")
    @Operation(summary = "获得生产任务")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:task:query')")
    public CommonResult<Map<String, Object>> getTaskDetail(String taskCode) {
        Map<String, Object> resultMap = new HashMap<>();
        TaskDO task = taskService.getTask(taskCode);
        WorkorderDO workorderDO = workorderService.getWorkorder(task.getWorkorderCode());
        MdItemDO itemDO = mdItemService.getMdItem(task.getItemCode());
        resultMap.put("taskId" , task.getId());
        resultMap.put("taskCode" , task.getTaskCode());
        resultMap.put("taskName" , task.getTaskName());

        resultMap.put("workorderCode" , task.getWorkorderCode());
        resultMap.put("workorderName" , task.getWorkorderName());
        resultMap.put("itemCode" , task.getItemCode());
        resultMap.put("itemName" , task.getItemName());
        resultMap.put("specification" , itemDO.getSpecification());
        resultMap.put("unitOfMeasure" , itemDO.getUnitOfMeasure());

        resultMap.put("workstationCode" , task.getWorkstationCode());
        resultMap.put("workstationName" , task.getWorkstationName());

        resultMap.put("processId" , task.getProcessId());
        resultMap.put("processCode" , task.getProcessCode());
        resultMap.put("processName" , task.getProcessName());
        resultMap.put("teamCode" , task.getAttr1());

        // 基于当前任务单获取领料单, 以最新的领料单行
        IssueHeaderDO headerDO = Optional.ofNullable(issueHeaderService.getIssueHeaderList(new IssueHeaderExportReqVO().setTaskCode(taskCode)).get(0)).orElse(null);
        if(headerDO == null){
            return error(ErrorCodeConstants.ISSUE_NOT_EXISTS);
        }

        List<IssueLineDO> lineDOList = Optional.ofNullable(issueLineService.getIssueLineList(new IssueLineExportReqVO().setIssueId(headerDO.getId()).setStatus("Y").setFeedbackStatus("N"))).orElse(null);
        if(lineDOList.isEmpty()){
            return error(ErrorCodeConstants.TASK_NOT_RECEPT);
        }


        IssueLineDO line = Optional.ofNullable(lineDOList.get(0)).orElse(null);
        if(line == null){
            return error(ErrorCodeConstants.TASK_NOT_RECEPT);
        }

        resultMap.put("machineryId" , line.getMachineryId());
        resultMap.put("machineryCode" , line.getMachineryCode());
        resultMap.put("machineryName" , line.getMachineryName());
        String erpMachineryName = dvMachineryApi.getMachineryInfo(line.getMachineryCode()).getErpMachineryCode();
        resultMap.put("erpMachineryName" , erpMachineryName);

        WorkStationDTO workStationDTO = workStationApi.getWorkstationByProcessCode(task.getProcessCode());
        resultMap.put("workstationId" , workStationDTO.getId());
        resultMap.put("workstationCode" , workStationDTO.getWorkstationCode());
        resultMap.put("workstationName" , workStationDTO.getWorkstationName());

        resultMap.put("workshopId" , workStationDTO.getWorkshopId());
        resultMap.put("workshopCode" , workStationDTO.getWorkshopCode());
        resultMap.put("workshopName" , workStationDTO.getWorkshopName());

        return success(resultMap);
    }

}
