package com.dofast.module.cmms.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.cmms.controller.admin.dvcheckmachinery.vo.DvCheckMachineryExportReqVO;
import com.dofast.module.cmms.controller.admin.dvcheckplan.vo.DvCheckPlanExportReqVO;
import com.dofast.module.cmms.controller.admin.dvchecksubject.vo.DvCheckSubjectExportReqVO;
import com.dofast.module.cmms.controller.admin.dvrepair.vo.DvRepairCreateReqVO;
import com.dofast.module.cmms.dal.dataobject.dvcheckmachinery.DvCheckMachineryDO;
import com.dofast.module.cmms.dal.dataobject.dvcheckplan.DvCheckPlanDO;
import com.dofast.module.cmms.dal.dataobject.dvchecksubject.DvCheckSubjectDO;
import com.dofast.module.cmms.dal.dataobject.dvmachinery.DvMachineryDO;
import com.dofast.module.cmms.dal.dataobject.dvrepairline.DvRepairLineDO;
import com.dofast.module.cmms.service.dvcheckmachinery.DvCheckMachineryService;
import com.dofast.module.cmms.service.dvcheckplan.DvCheckPlanService;
import com.dofast.module.cmms.service.dvchecksubject.DvCheckSubjectService;
import com.dofast.module.cmms.service.dvmachinery.DvMachineryService;
import com.dofast.module.cmms.service.dvrepair.DvRepairService;
import com.dofast.module.cmms.service.dvrepairline.DvRepairLineService;
import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.system.api.notify.NotifyMessageSendApi;
import com.dofast.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class dvMachineryPlanJob implements JobHandler {

    @Resource
    private DvCheckPlanService dvCheckPlanService;
    @Resource
    private DvCheckMachineryService dvCheckMachineryService;
    @Resource
    private DvCheckSubjectService dvCheckSubjectService;
    @Resource
    private DvRepairService dvRepairService;
    @Resource
    private DvRepairLineService dvRepairLineService;
    @Resource
    private DvMachineryService dvMachineryService;
    @Resource
    private AutoCodeApi autoCodeApi;
    @Resource
    private NotifyMessageSendApi notifySendApi;

    // ======================== Job 入口 ========================

    @Override
    public String execute(String param) {
        LocalDateTime now = LocalDateTime.now();
        List<DvCheckPlanDO> plans = getActivePlans();

        for (DvCheckPlanDO plan : plans) {
            try {
                processPlan(plan, now);
            } catch (Exception e) {
                System.err.println("点检计划执行失败：" + plan.getPlanCode());
                e.printStackTrace();
            }
        }
        return "SUCCESS";
    }

    // ======================== 主流程 ========================

    private void processPlan(DvCheckPlanDO plan, LocalDateTime now) {

        if (!isPlanEffective(plan, now)) {
            return;
        }

        CycleRange cycle = calculateCycle(plan, now);

        List<DvCheckMachineryDO> machines = getMachines(plan);
        if (machines.isEmpty()) {
            return;
        }

        boolean hasCreated = false;

        for (DvCheckMachineryDO machinery : machines) {

            // 幂等判断（唯一事实源）
            boolean exists = dvRepairService.countInspectionRepairInCycle(
                    plan.getId(),
                    machinery.getMachineryId(),
                    cycle.start,
                    cycle.end
            ) > 0;

            if (exists) {
                continue;
            }

            createInspectionRepair(plan, machinery);
            hasCreated = true;
        }

        // ⭐ 本周期首次创建，才通知
        if (hasCreated) {
            sendNotification(plan, machines);
        }
    }

    // ======================== 周期计算（企业级） ========================

    private CycleRange calculateCycle(DvCheckPlanDO plan, LocalDateTime now) {

        switch (plan.getCycleType()) {

            case "HOUR": {
                LocalDateTime start = now.truncatedTo(ChronoUnit.HOURS);
                return new CycleRange(start, start.plusHours(plan.getCycleCount()));
            }

            case "DAY": {
                LocalDateTime start = now.toLocalDate().atStartOfDay();
                return new CycleRange(start, start.plusDays(plan.getCycleCount()));
            }

            case "WEEK": {
                LocalDate start = now.toLocalDate()
                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                return new CycleRange(start.atStartOfDay(), start.plusWeeks(1).atStartOfDay());
            }

            case "MONTH": {
                LocalDate start = now.toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
                return new CycleRange(start.atStartOfDay(), start.plusMonths(1).atStartOfDay());
            }

            case "QUARTER": {
                int quarter = (now.getMonthValue() - 1) / 3;
                LocalDate start = LocalDate.of(
                        now.getYear(),
                        quarter * 3 + 1,
                        1
                );
                return new CycleRange(start.atStartOfDay(), start.plusMonths(3).atStartOfDay());
            }

            case "YEAR": {
                LocalDate start = now.toLocalDate().with(TemporalAdjusters.firstDayOfYear());
                return new CycleRange(start.atStartOfDay(), start.plusYears(1).atStartOfDay());
            }

            default:
                throw new IllegalArgumentException("不支持的周期类型：" + plan.getCycleType());
        }
    }

    // ======================== 维修单创建 ========================

    private void createInspectionRepair(DvCheckPlanDO plan, DvCheckMachineryDO machinery) {

        String repairCode = autoCodeApi.genSerialCode("REPAIR_CODE", null);

        DvRepairCreateReqVO req = new DvRepairCreateReqVO();
        req.setRepairCode(repairCode);
        req.setRepairName(repairCode);
        req.setRepairType("INSPECTION");
        req.setStatus("PREPARE");
        req.setRequireDate(LocalDateTime.now());

        req.setMachineryId(machinery.getMachineryId());
        req.setMachineryCode(machinery.getMachineryCode());
        req.setMachineryName(machinery.getMachineryName());

        DvMachineryDO machine = dvMachineryService.getDvMachinery(machinery.getMachineryId());
        req.setMachineryTypeId(machine.getMachineryTypeId());

        req.setRemark("系统自动生成，来源点检计划：" + plan.getPlanCode());

        Long repairId = dvRepairService.createDvRepair(req);

        // 创建维修单行
        List<DvCheckSubjectDO> subjects =
                Optional.ofNullable(
                        dvCheckSubjectService.getDvCheckSubjectList(
                                new DvCheckSubjectExportReqVO().setPlanId(machinery.getPlanId())
                        )
                ).orElse(Collections.emptyList());

        List<DvRepairLineDO> lines = new ArrayList<>();
        for (DvCheckSubjectDO s : subjects) {
            DvRepairLineDO line = new DvRepairLineDO();
            line.setRepairId(repairId);
            line.setSubjectId(s.getSubjectId());
            line.setSubjectCode(s.getSubjectCode());
            line.setSubjectName(s.getSubjectName());
            line.setSubjectType(s.getSubjectType());
            line.setSubjectContent(s.getSubjectContent());
            line.setSubjectStandard(s.getSubjectStandard());
            lines.add(line);
        }

        if (!lines.isEmpty()) {
            dvRepairLineService.insertBatch(lines);
        }
    }

    // ======================== 通知 ========================

    private void sendNotification(DvCheckPlanDO plan, List<DvCheckMachineryDO> machines) {

        String deviceNames = machines.stream()
                .map(DvCheckMachineryDO::getMachineryName)
                .collect(Collectors.joining(","));

        Map<String, Object> params = new HashMap<>();
        params.put("deviceName", deviceNames);

        notifySendApi.sendSingleMessageToAdmin(
                new NotifySendSingleToUserReqDTO()
                        .setUserId(getResponsibleUserId(plan))
                        .setTemplateCode("devicePlan")
                        .setTemplateParams(params)
        );
    }

    // ======================== 基础方法 ========================

    private List<DvCheckPlanDO> getActivePlans() {
        DvCheckPlanExportReqVO query = new DvCheckPlanExportReqVO();
        query.setStatus("FINISHED");
        return dvCheckPlanService.getDvCheckPlanList(query);
    }

    private boolean isPlanEffective(DvCheckPlanDO plan, LocalDateTime now) {
        return !now.isBefore(plan.getStartDate())
                && !now.isAfter(plan.getEndDate());
    }

    private List<DvCheckMachineryDO> getMachines(DvCheckPlanDO plan) {
        DvCheckMachineryExportReqVO query = new DvCheckMachineryExportReqVO();
        query.setPlanId(plan.getId());
        return dvCheckMachineryService.getDvCheckMachineryList(query);
    }

    private Long getResponsibleUserId(DvCheckPlanDO plan) {
        return 170L;
    }

    // ======================== 内部对象 ========================

    private static class CycleRange {
        LocalDateTime start;
        LocalDateTime end;

        CycleRange(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }
    }
}
