package com.dofast.module.pro.job;

import cn.hutool.core.bean.BeanUtil;
import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.cmms.api.dvmachinery.DvMachineryApi;
import com.dofast.module.cmms.api.dvmachinery.dto.DvMachineryDTO;
import com.dofast.module.pro.controller.admin.feedback.vo.FeedbackUpdateReqVO;
import com.dofast.module.pro.controller.admin.routeprocess.vo.RouteProcessExportReqVO;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;
import com.dofast.module.pro.dal.dataobject.routeprocess.RouteProcessDO;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.route.RouteService;
import com.dofast.module.pro.service.routeprocess.RouteProcessService;
import com.dofast.module.pro.service.task.TaskService;
import com.dofast.module.pro.service.workorder.WorkorderService;
import com.dofast.module.wms.api.ERPApi.WorkorderERPAPI;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class erpFeedbackJob implements JobHandler {

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private WorkorderERPAPI workorderERPAPI;

    @Resource
    private TaskService taskService;

    @Resource
    private WorkorderService workorderService;

    @Resource
    private DvMachineryApi dvMachineryApi;

    @Resource
    private RouteService routeService;

    @Resource
    private RouteProcessService routeProcessService;

    @Override
    public String execute(String param) throws Exception {

        // 查询待同步ERP的数据
        List<FeedbackDO> list = feedbackService.selectErpPendingList();

        if (list.isEmpty()) {
            return "";
        }

        System.out.println("ERP报工接口调用开始，待同步数据量：" + list.size());
        for (FeedbackDO feedback : list) {
            try {
                TaskDO task = taskService.getTask(feedback.getTaskId());

                WorkorderDO workorder = workorderService.getWorkorder(feedback.getWorkorderId());

                Map<String, Object> erpParams = buildERPParams(feedback, task, workorder);

                String erpResult = workorderERPAPI.workOrderReportCreate(erpParams, feedback.getUserName());
                if (erpResult.contains("SUCCESS")) {
                    String erpFeedback = erpResult.split(",")[1];
                    feedback.setErpFeedback(erpFeedback);
                    feedback.setErpFeedbackStatus("Y");
                    FeedbackUpdateReqVO update = BeanUtil.toBean(feedback, FeedbackUpdateReqVO.class);
                    feedbackService.updateFeedback(update);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "success";
    }

    private Map<String, Object> buildERPParams(FeedbackDO feedback, TaskDO task, WorkorderDO workorder) {

        LocalDateTime feedbackTime = feedback.getFeedbackTime();

        DvMachineryDTO machineryDTO = dvMachineryApi.getMachineryInfo(feedback.getMachineryCode());

        Map<String, Object> erpParams = new HashMap<>(32);

        // 获取工作序
        String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
        RouteDO route = routeService.getRoute(routeCode);
        RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
        exportReqVO.setRouteId(route.getId());
        exportReqVO.setProcessCode(task.getProcessCode());
        List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(exportReqVO);
        RouteProcessDO process = routeProcess.get(0);
        Long workorderSequence = task.getWorkorderSequence() != null ? task.getWorkorderSequence() : process.getWorkorderSequence();
        erpParams.put("source_no", feedback.getFeedbackCode());
        erpParams.put("sffb002", feedback.getUserName());
        erpParams.put("sffb005", workorder.getWorkorderCode());
        erpParams.put("sffb007", feedback.getProcessCode());
        erpParams.put("sffb008", String.valueOf(workorderSequence));
        erpParams.put("sffb009", feedback.getWorkstationCode());
        erpParams.put("sffb010", machineryDTO.getErpMachineryCode() + "#");
        erpParams.put("sffb012", feedbackTime.toLocalDate());
        erpParams.put("sffb013", feedbackTime.toLocalTime());
        erpParams.put("sffb014", 0);
        erpParams.put("sffb015", 0);
        if ("AM006".equals(feedback.getProcessCode()) && "BF".equals(feedback.getMachineryCode().split("-")[1].substring(0, 2))) {
            erpParams.put("sffb017", feedback.getConversionQuantity());
            erpParams.put("sffb018", feedback.getConversionQuantityUnquanlified());
            erpParams.put("sffb016", feedback.getConversionUnit());
        } else {
            erpParams.put("sffb017", feedback.getQuantityQualified());
            erpParams.put("sffb018", feedback.getQuantityUnquanlified());
            erpParams.put("sffb016", feedback.getUnitOfMeasure());
        }
        return erpParams;
    }

}
