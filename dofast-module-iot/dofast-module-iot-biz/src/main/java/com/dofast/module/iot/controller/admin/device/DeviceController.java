package com.dofast.module.iot.controller.admin.device;

import com.dofast.module.iot.service.device.DeviceTdengineService;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.iot.controller.admin.device.vo.*;
import com.dofast.module.iot.dal.dataobject.device.DeviceDO;
import com.dofast.module.iot.convert.device.DeviceConvert;
import com.dofast.module.iot.service.device.DeviceService;

@Tag(name = "管理后台 - 设备")
@RestController
@RequestMapping("/iot/device")
@Validated
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @Resource
    private DeviceTdengineService deviceTdengineService;

    @PostMapping("/create")
    @Operation(summary = "创建设备")
    @PreAuthorize("@ss.hasPermission('iot:device:create')")
    public CommonResult<Long> createDevice(@Valid @RequestBody DeviceCreateReqVO createReqVO) {
        return success(deviceService.createDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备")
    @PreAuthorize("@ss.hasPermission('iot:device:update')")
    public CommonResult<Boolean> updateDevice(@Valid @RequestBody DeviceUpdateReqVO updateReqVO) {
        deviceService.updateDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:device:delete')")
    public CommonResult<Boolean> deleteDevice(@RequestParam("id") Long id) {
        deviceService.deleteDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:device:query')")
    public CommonResult<DeviceRespVO> getDevice(@RequestParam("id") Long id) {
        DeviceDO device = deviceService.getDevice(id);
        return success(DeviceConvert.INSTANCE.convert(device));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('iot:device:query')")
    public CommonResult<List<DeviceRespVO>> getDeviceList(@RequestParam("ids") Collection<Long> ids) {
        List<DeviceDO> list = deviceService.getDeviceList(ids);
        return success(DeviceConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备分页")
    @PreAuthorize("@ss.hasPermission('iot:device:query')")
    public CommonResult<PageResult<DeviceRespVO>> getDevicePage(@Valid DevicePageReqVO pageVO) {
        PageResult<DeviceDO> pageResult = deviceService.getDevicePage(pageVO);
        return success(DeviceConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备 Excel")
    @PreAuthorize("@ss.hasPermission('iot:device:export')")
    @OperateLog(type = EXPORT)
    public void exportDeviceExcel(@Valid DeviceExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DeviceDO> list = deviceService.getDeviceList(exportReqVO);
        // 导出 Excel
        List<DeviceExcelVO> datas = DeviceConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备.xls", "数据", DeviceExcelVO.class, datas);
    }


    @GetMapping("/getIotDevice")
    @Operation(summary = "获得设备列表")
    public CommonResult<List> getDeviceList() {
        List<Map<String, Object>> list = deviceTdengineService.initDeviceMeter();
        System.out.println(list);
        return success(list);
    }



}
