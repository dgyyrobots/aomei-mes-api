package com.dofast.module.iot.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.iot.service.devicefeedbacklog.DeviceFeedbackLogService;
import com.dofast.module.system.api.dict.DictDataApi;
import com.dofast.module.system.api.dict.dto.DictDataRespDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
public class iotFeedbackLogJob implements JobHandler {

    @Resource
    private DeviceFeedbackLogService deviceFeedbackLogService;

    @Resource
    private DictDataApi dictDataApi;

    @Override
    public String execute(String param) throws Exception {
        // 获取白班起止时间配置
        DictDataRespDTO startTimeDO = dictDataApi.getDictData("mes_shift_time", "day_shift_start");
        DictDataRespDTO endTimeDO = dictDataApi.getDictData("mes_shift_time", "day_shift_end");

        String startTimeStr = startTimeDO.getLabel();
        String endTimeStr = endTimeDO.getLabel();

        // 解析时间配置
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("[H:mm][HH:mm]")
                .toFormatter();

        LocalTime dayShiftStart = LocalTime.parse(startTimeStr, formatter);
        LocalTime dayShiftEnd = LocalTime.parse(endTimeStr, formatter);

        // 获取当前时间
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        // 判定当前班次
        String currentShift;
        if (!now.isBefore(dayShiftStart) && !now.isAfter(dayShiftEnd)) {
            currentShift = "0"; // 当前为白班
        } else {
            currentShift = "1"; // 当前为晚班
        }

        // 根据班次确定删除的时间范围
        LocalDateTime deleteStart;
        LocalDateTime deleteEnd;
        String shiftType;

        if ("0".equals(currentShift)) {
            shiftType = "昨天晚班";
            LocalDate yesterday = today.minusDays(1);
            deleteStart = LocalDateTime.of(yesterday, dayShiftEnd); // 昨天白班结束时刻
            deleteEnd = LocalDateTime.of(today, dayShiftStart);     // 今天白班开始时刻
        } else {
            shiftType = "今天白班";
            // 今天白班 = 今天白班开始时间 ~ 今天白班结束时间
            deleteStart = LocalDateTime.of(today, dayShiftStart);
            deleteEnd = LocalDateTime.of(today, dayShiftEnd);
        }
        // 格式化为数据库查询格式
        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String start = deleteStart.format(dbFormatter);
        String end = deleteEnd.format(dbFormatter);

        // 执行删除操作（左闭右开区间 [start, end)）
        int deletedCount = deviceFeedbackLogService.deleteByCreateTimeRange(start, end);

        return String.format("已清除%s数据：时间范围 %s 至 %s，共删除%d条记录",
                shiftType, start, end, deletedCount);

    }

}
