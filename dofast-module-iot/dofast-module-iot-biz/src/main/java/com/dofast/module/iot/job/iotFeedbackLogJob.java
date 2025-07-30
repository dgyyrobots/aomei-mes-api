package com.dofast.module.iot.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.iot.service.devicefeedbacklog.DeviceFeedbackLogService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Component
public class iotFeedbackLogJob implements JobHandler {

    @Resource
    private DeviceFeedbackLogService deviceFeedbackLogService;

    @Override
    public String execute(String param) throws Exception {
        // 时间上周一00:00
        LocalDate lastMonday = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) // 本周一
                .minusWeeks(1); // 上周一

        LocalDateTime cutoffTime = lastMonday.atStartOfDay();
        String timeStr = cutoffTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 执行删除操作
        int deletedCount = deviceFeedbackLogService.deleteByCreateTimeRange(timeStr);
        return String.format("清除%d条上周一之前的所有设备产量日志数据（截止时间: %s）",
                deletedCount, cutoffTime);
    }
}
