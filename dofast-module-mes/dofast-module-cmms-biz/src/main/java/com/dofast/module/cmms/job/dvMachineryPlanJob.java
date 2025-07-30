package com.dofast.module.cmms.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.cmms.controller.admin.dvcheckmachinery.vo.DvCheckMachineryExportReqVO;
import com.dofast.module.cmms.controller.admin.dvcheckplan.vo.DvCheckPlanExportReqVO;
import com.dofast.module.cmms.dal.dataobject.dvcheckmachinery.DvCheckMachineryDO;
import com.dofast.module.cmms.dal.dataobject.dvcheckplan.DvCheckPlanDO;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanheaderlog.DvCheckPlanHeaderLogDO;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanlinelog.DvCheckPlanLineLogDO;
import com.dofast.module.cmms.service.dvcheckmachinery.DvCheckMachineryService;
import com.dofast.module.cmms.service.dvcheckplan.DvCheckPlanService;
import com.dofast.module.cmms.service.dvcheckplanheaderlog.DvCheckPlanHeaderLogService;
import com.dofast.module.cmms.service.dvcheckplanlinelog.DvCheckPlanLineLogService;
import com.dofast.module.system.api.notify.NotifyMessageSendApi;
import com.dofast.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class dvMachineryPlanJob implements JobHandler {

    @Resource
    private DvCheckPlanService dvCheckPlanService;

    @Resource
    private DvCheckMachineryService dvCheckMachineryService;

    @Resource
    private NotifyMessageSendApi notifySendApi;

    @Resource
    private DvCheckPlanHeaderLogService dvCheckPlanHeaderLogService;

    @Resource
    private DvCheckPlanLineLogService dvCheckPlanLineLogService;

    @Override
    public String execute(String param) {
        try {
            List<DvCheckPlanDO> activePlans = getActivePlans();
            List<DvCheckPlanDO> needExecutePlans = filterPlans(activePlans);
            processPlans(needExecutePlans);
            return "success";
        } catch (Exception e) {
            System.out.println("设备点检计划任务执行失败");
            return "failed: " + e.getMessage();
        }
    }

    private List<DvCheckPlanDO> getActivePlans() {
        DvCheckPlanExportReqVO query = new DvCheckPlanExportReqVO();
        query.setStatus("FINISHED");
        return dvCheckPlanService.getDvCheckPlanList(query);
    }

    private List<DvCheckPlanDO> filterPlans(List<DvCheckPlanDO> plans) {
        LocalDateTime now = LocalDateTime.now();
        List<DvCheckPlanDO> result = new ArrayList<>();

        for (DvCheckPlanDO plan : plans) {
            try {
                if (isPlanEffective(plan, now) &&
                        isExecutionTime(plan, now) &&
                        !hasExecutedInCurrentCycle(plan, now)) {
                    result.add(plan);
                }
            } catch (Exception e) {
                System.out.println("计划" + plan.getPlanCode() + "检查异常");
            }
        }
        return result;
    }

    private boolean isPlanEffective(DvCheckPlanDO plan, LocalDateTime now) {
        return !now.isBefore(plan.getStartDate()) && !now.isAfter(plan.getEndDate());
    }

    private boolean isExecutionTime(DvCheckPlanDO plan, LocalDateTime now) {
        switch (plan.getCycleType()) {
            case "DAY":
                return checkDaily(plan, now);
            case "HOUR":
                return checkHourly(plan, now);
            case "WEEK":
                return checkWeekly(plan, now);
            case "MONTH":
                return checkMonthly(plan, now);
            case "QUARTER":
                return checkQuarterly(plan, now);
            case "YEAR":
                return checkYearly(plan, now);
            default:
                return false;
        }
    }

    // 新增 DAY 周期判断方法
    private boolean checkDaily(DvCheckPlanDO plan, LocalDateTime now) {
        Map<String, LocalDateTime> range = getCycleTimeRange(plan, now);
        return now.isAfter(range.get("start")) && now.isBefore(range.get("end"));
    }

    // 新增 HOUR 周期判断方法
    private boolean checkHourly(DvCheckPlanDO plan, LocalDateTime now) {
        Map<String, LocalDateTime> range = getCycleTimeRange(plan, now);
        return now.isAfter(range.get("start")) && now.isBefore(range.get("end"));
    }


    private boolean checkWeekly(DvCheckPlanDO plan, LocalDateTime now) {
        Map<String, LocalDateTime> range = getCycleTimeRange(plan, now);
        long daysBetween = DAYS.between(range.get("start"), now);
        int interval = (int) Math.ceil(7.0 / plan.getCycleCount());
        return daysBetween % interval == 0;
    }

    private boolean checkMonthly(DvCheckPlanDO plan, LocalDateTime now) {
        Map<String, LocalDateTime> range = getCycleTimeRange(plan, now);
        long totalDays = DAYS.between(range.get("start"), range.get("end"));
        int interval = (int) Math.ceil(totalDays * 1.0 / plan.getCycleCount());
        long daysPassed = DAYS.between(range.get("start"), now);
        return daysPassed % interval == 0;
    }

    private boolean checkQuarterly(DvCheckPlanDO plan, LocalDateTime now) {
        Map<String, LocalDateTime> range = getCycleTimeRange(plan, now);
        long totalDays = DAYS.between(range.get("start"), range.get("end"));
        int interval = (int) Math.ceil(totalDays * 1.0 / plan.getCycleCount());
        long daysPassed = DAYS.between(range.get("start"), now);
        return daysPassed % interval == 0;
    }

    private boolean checkYearly(DvCheckPlanDO plan, LocalDateTime now) {
        return now.getDayOfYear() == plan.getStartDate().getDayOfYear();
    }

    private boolean hasExecutedInCurrentCycle(DvCheckPlanDO plan, LocalDateTime now) {
        Map<String, LocalDateTime> range = getCycleTimeRange(plan, now);
        LocalDateTime startTime = range.get("start");
        LocalDateTime endTime = range.get("end");
        // 判定当前周期内是否存在点检记录
        int count = dvCheckPlanHeaderLogService.selectCountInCycle(plan.getPlanCode(), startTime, endTime);
        System.out.println("计划" + plan.getPlanCode() + "当前周期内执行次数：" + count);
        return count > 0;
    }

    private Map<String, LocalDateTime> getCycleTimeRange(DvCheckPlanDO plan, LocalDateTime now) {
        Map<String, LocalDateTime> range = new HashMap<>();

        switch (plan.getCycleType()) {
            case "DAY":    // DAY 类型周期范围
                long daysSinceStart = ChronoUnit.DAYS.between(
                        plan.getStartDate().truncatedTo(ChronoUnit.DAYS),
                        now.truncatedTo(ChronoUnit.DAYS)
                );
                long dayPeriods = daysSinceStart / plan.getCycleCount();
                LocalDateTime dayPeriodStart = plan.getStartDate()
                        .truncatedTo(ChronoUnit.DAYS)
                        .plusDays(dayPeriods * plan.getCycleCount());
                range.put("start", dayPeriodStart);
                range.put("end", dayPeriodStart.plusDays(plan.getCycleCount()));
                break;

            case "HOUR":   // HOUR 类型周期范围
                long hoursSinceStart = ChronoUnit.HOURS.between(
                        plan.getStartDate().truncatedTo(ChronoUnit.HOURS),
                        now.truncatedTo(ChronoUnit.HOURS)
                );
                long hourPeriods = hoursSinceStart / plan.getCycleCount();
                LocalDateTime hourPeriodStart = plan.getStartDate()
                        .truncatedTo(ChronoUnit.HOURS)
                        .plusHours(hourPeriods * plan.getCycleCount());
                range.put("start", hourPeriodStart);
                range.put("end", hourPeriodStart.plusHours(plan.getCycleCount()));
                break;

            case "WEEK":
                LocalDateTime weekStart = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                        .truncatedTo(ChronoUnit.DAYS);
                range.put("start", weekStart);
                range.put("end", weekStart.plusWeeks(1));
                break;

            case "MONTH":
                LocalDateTime monthStart = now.with(TemporalAdjusters.firstDayOfMonth())
                        .truncatedTo(ChronoUnit.DAYS);
                range.put("start", monthStart);
                range.put("end", monthStart.plusMonths(1));
                break;

            case "QUARTER":
                int quarter = (now.getMonthValue() - 1) / 3;
                LocalDateTime quarterStart = now.withMonth(quarter * 3 + 1)
                        .with(TemporalAdjusters.firstDayOfMonth())
                        .truncatedTo(ChronoUnit.DAYS);
                range.put("start", quarterStart);
                range.put("end", quarterStart.plusMonths(3));
                break;

            case "YEAR":
                LocalDateTime yearStart = now.with(TemporalAdjusters.firstDayOfYear())
                        .truncatedTo(ChronoUnit.DAYS);
                range.put("start", yearStart);
                range.put("end", yearStart.plusYears(1));
                break;

            default:
                throw new IllegalArgumentException("不支持的周期类型: " + plan.getCycleType());
        }
        return range;
    }

    private void processPlans(List<DvCheckPlanDO> plans) {
        for (DvCheckPlanDO plan : plans) {
            try {
                sendNotification(plan);
                System.out.println("计划" + plan.getPlanCode() + "处理成功");
            } catch (Exception e) {
                System.out.println("计划" + plan.getPlanCode() + "处理失败");
            }
        }
    }

    private void sendNotification(DvCheckPlanDO plan) {
        String deviceNames = getMachines(plan).stream()
                .map(DvCheckMachineryDO::getMachineryName)
                .collect(Collectors.joining(","));

        Map<String, Object> param = new HashMap<>();
        param.put("deviceName", deviceNames);

        notifySendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO()
                .setUserId(getResponsibleUserId(plan)).setTemplateCode("devicePlan").setTemplateParams(param));


    }

    private List<DvCheckMachineryDO> getMachines(DvCheckPlanDO plan) {
        DvCheckMachineryExportReqVO query = new DvCheckMachineryExportReqVO();
        query.setPlanId(plan.getId());
        return dvCheckMachineryService.getDvCheckMachineryList(query);
    }

    private Long getResponsibleUserId(DvCheckPlanDO plan) {
        return 170L;
    }
}
