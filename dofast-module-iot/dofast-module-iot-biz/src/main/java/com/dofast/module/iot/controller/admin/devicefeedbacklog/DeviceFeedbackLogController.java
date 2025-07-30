package com.dofast.module.iot.controller.admin.devicefeedbacklog;

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
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.iot.controller.admin.devicefeedbacklog.vo.*;
import com.dofast.module.iot.dal.dataobject.devicefeedbacklog.DeviceFeedbackLogDO;
import com.dofast.module.iot.convert.devicefeedbacklog.DeviceFeedbackLogConvert;
import com.dofast.module.iot.service.devicefeedbacklog.DeviceFeedbackLogService;

@Tag(name = "管理后台 - 设备产量日志")
@RestController
@RequestMapping("/iot/device-feedback-log")
@Validated
public class DeviceFeedbackLogController {

    @Resource
    private DeviceFeedbackLogService deviceFeedbackLogService;

    @PostMapping("/create")
    @Operation(summary = "创建设备产量日志")
    @PreAuthorize("@ss.hasPermission('iot:device-feedback-log:create')")
    public CommonResult<Long> createDeviceFeedbackLog(@Valid @RequestBody DeviceFeedbackLogCreateReqVO createReqVO) {
        return success(deviceFeedbackLogService.createDeviceFeedbackLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备产量日志")
    @PreAuthorize("@ss.hasPermission('iot:device-feedback-log:update')")
    public CommonResult<Boolean> updateDeviceFeedbackLog(@Valid @RequestBody DeviceFeedbackLogUpdateReqVO updateReqVO) {
        deviceFeedbackLogService.updateDeviceFeedbackLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备产量日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:device-feedback-log:delete')")
    public CommonResult<Boolean> deleteDeviceFeedbackLog(@RequestParam("id") Long id) {
        deviceFeedbackLogService.deleteDeviceFeedbackLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备产量日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:device-feedback-log:query')")
    public CommonResult<DeviceFeedbackLogRespVO> getDeviceFeedbackLog(@RequestParam("id") Long id) {
        DeviceFeedbackLogDO deviceFeedbackLog = deviceFeedbackLogService.getDeviceFeedbackLog(id);
        return success(DeviceFeedbackLogConvert.INSTANCE.convert(deviceFeedbackLog));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备产量日志列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('iot:device-feedback-log:query')")
    public CommonResult<List<DeviceFeedbackLogRespVO>> getDeviceFeedbackLogList(@RequestParam("ids") Collection<Long> ids) {
        List<DeviceFeedbackLogDO> list = deviceFeedbackLogService.getDeviceFeedbackLogList(ids);
        return success(DeviceFeedbackLogConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备产量日志分页")
    @PreAuthorize("@ss.hasPermission('iot:device-feedback-log:query')")
    public CommonResult<PageResult<DeviceFeedbackLogRespVO>> getDeviceFeedbackLogPage(@Valid DeviceFeedbackLogPageReqVO pageVO) {
        PageResult<DeviceFeedbackLogDO> pageResult = deviceFeedbackLogService.getDeviceFeedbackLogPage(pageVO);
        return success(DeviceFeedbackLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备产量日志 Excel")
    @PreAuthorize("@ss.hasPermission('iot:device-feedback-log:export')")
    @OperateLog(type = EXPORT)
    public void exportDeviceFeedbackLogExcel(@Valid DeviceFeedbackLogExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DeviceFeedbackLogDO> list = deviceFeedbackLogService.getDeviceFeedbackLogList(exportReqVO);
        // 导出 Excel
        List<DeviceFeedbackLogExcelVO> datas = DeviceFeedbackLogConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备产量日志.xls", "数据", DeviceFeedbackLogExcelVO.class, datas);
    }

}
