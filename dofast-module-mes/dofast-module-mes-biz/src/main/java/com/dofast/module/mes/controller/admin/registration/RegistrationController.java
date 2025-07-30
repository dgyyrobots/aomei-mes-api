package com.dofast.module.mes.controller.admin.registration;

import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.mes.controller.admin.registrationline.vo.RegistrationLineExportReqVO;
import com.dofast.module.mes.convert.registrationline.RegistrationLineConvert;
import com.dofast.module.mes.dal.dataobject.registrationline.RegistrationLineDO;
import com.dofast.module.mes.enums.ErrorCodeConstants;
import com.dofast.module.mes.service.registrationline.RegistrationLineService;
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
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;
import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.common.pojo.CommonResult.error;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.mes.controller.admin.registration.vo.*;
import com.dofast.module.mes.dal.dataobject.registration.RegistrationDO;
import com.dofast.module.mes.convert.registration.RegistrationConvert;
import com.dofast.module.mes.service.registration.RegistrationService;

@Tag(name = "管理后台 - 计时登记")
@RestController
@RequestMapping("/mes/registration")
@Validated
public class RegistrationController {

    @Resource
    private RegistrationService registrationService;

    @Resource
    private RegistrationLineService registrationLineService;

    @Resource
    private AutoCodeApi autoCodeApi;


    @PostMapping("/create")
    @Operation(summary = "创建计时登记")
    @PreAuthorize("@ss.hasPermission('mes:registration:create')")
    public CommonResult<Long> createRegistration(@Valid @RequestBody RegistrationCreateReqVO createReqVO) {
        // 追加卡控, 相同任务单, 设备, 登记类型下不允许创建
        List<RegistrationDO> list = registrationService.getRegistrationList(new RegistrationExportReqVO().setRelatedTaskCode(createReqVO.getRelatedTaskCode()).setRegistrationType(createReqVO.getRegistrationType()).setRelatedMachineryCode(createReqVO.getRelatedMachineryCode()));
        if(!list.isEmpty()){
            error(ErrorCodeConstants.REGISTRATION_TASK_EXISTS);
        }

        String code = autoCodeApi.genSerialCode("REGISTRATION_CODE",null);
        createReqVO.setRegistrationCode(code);
        createReqVO.setRegistrationName(code);

        return success(registrationService.createRegistration(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新计时登记")
    @PreAuthorize("@ss.hasPermission('mes:registration:update')")
    public CommonResult<Boolean> updateRegistration(@Valid @RequestBody RegistrationUpdateReqVO updateReqVO) {
        registrationService.updateRegistration(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计时登记")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:registration:delete')")
    public CommonResult<Boolean> deleteRegistration(@RequestParam("id") Long id) {
        registrationService.deleteRegistration(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得计时登记")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mes:registration:query')")
    public CommonResult<RegistrationRespVO> getRegistration(@RequestParam("id") Long id) {
        RegistrationDO registration = registrationService.getRegistration(id);
        return success(RegistrationConvert.INSTANCE.convert(registration));
    }

    @GetMapping("/list")
    @Operation(summary = "获得计时登记列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('mes:registration:query')")
    public CommonResult<List<RegistrationRespVO>> getRegistrationList(@RequestParam("ids") Collection<Long> ids) {
        List<RegistrationDO> list = registrationService.getRegistrationList(ids);
        return success(RegistrationConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得计时登记分页")
    @PreAuthorize("@ss.hasPermission('mes:registration:query')")
    public CommonResult<PageResult<RegistrationRespVO>> getRegistrationPage(@Valid RegistrationPageReqVO pageVO) {
        PageResult<RegistrationDO> pageResult = registrationService.getRegistrationPage(pageVO);
        return success(RegistrationConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出计时登记 Excel")
    @PreAuthorize("@ss.hasPermission('mes:registration:export')")
    @OperateLog(type = EXPORT)
    public void exportRegistrationExcel(@Valid RegistrationExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<RegistrationDO> list = registrationService.getRegistrationList(exportReqVO);
        // 导出 Excel
        List<RegistrationExcelVO> datas = RegistrationConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "计时登记.xls", "数据", RegistrationExcelVO.class, datas);
    }


    @PutMapping("/finshRegistration")
    @Operation(summary = "更新计时登记")
    @PreAuthorize("@ss.hasPermission('mes:registration:update')")
    public CommonResult finshRegistration(@RequestBody Map<String , Object> requestMap) {
        Integer id = (Integer) requestMap.get("id");
        RegistrationDO registration = registrationService.getRegistration(id.longValue());
        if (registration.getEndTime() != null) {
            return error(ErrorCodeConstants.REGISTRATION_NOT_SUPPORT);
        }

        LocalDateTime now = LocalDateTime.now();
        registration.setEndTime(now);
        LocalDateTime startTime = registration.getStartTime();
        long minutes = Duration.between(startTime, now).toMinutes();
        registration.setDurationTime((int) minutes);
        registrationService.updateRegistration(RegistrationConvert.INSTANCE.convert01(registration));
        return success(true);
    }

    @PutMapping("/recordByRegistration")
    @Operation(summary = "计时登记")
    @PreAuthorize("@ss.hasPermission('mes:registration:update')")
    public CommonResult recordByRegistration(@RequestBody  Map<String , Object> requestMap) {
        Integer id = (Integer) requestMap.get("id");
        String type = (String)   requestMap.get("type");
        RegistrationDO registration = registrationService.getRegistration(id.longValue());
        if("START".equals(type)){
            // 启用计时
            if (!"N".equals(registration.getStatus())) {
                return error(ErrorCodeConstants.REGISTRATION_STATUS_START);
            }
            registration.setStatus("Y");
            registrationService.updateRegistration(RegistrationConvert.INSTANCE.convert01(registration));
            // 开始追加定时记录
            RegistrationLineDO line = new RegistrationLineDO();
            line.setRegistrationId(id.longValue());
            line.setRegistrationCode(registration.getRegistrationCode());
            line.setRegistrationType(registration.getRegistrationType());
            line.setStartTime(LocalDateTime.now());
            line.setRelatedMachineryCode(registration.getRelatedMachineryCode());
            line.setRelatedMachineryName(registration.getRelatedMachineryName());
            line.setRelatedErpMachineryCode(registration.getRelatedErpMachineryCode());
            line.setWorkshopId(registration.getWorkshopId());
            line.setWorkshopName(registration.getWorkshopName());
            line.setWorkshopCode(registration.getWorkshopCode());
            line.setRelatedTaskId(registration.getRelatedTaskId());
            line.setRelatedTaskCode(registration.getRelatedTaskCode());
            line.setRelatedWorkorder(registration.getRelatedWorkorder());
            registrationLineService.createRegistrationLine(RegistrationLineConvert.INSTANCE.convert01(line));
        }else{
            if (!"Y".equals(registration.getStatus())) {
                return error(ErrorCodeConstants.REGISTRATION_STATUS_STOP);
            }
            // 找寻当前的定时信息
            List<RegistrationLineDO> line =  Optional.ofNullable(registrationLineService.getRegistrationLineList(new RegistrationLineExportReqVO().setRegistrationId(id.longValue()).setRegistrationType(registration.getRegistrationType()))).orElse(null);
            if(line.isEmpty()){
                return error(ErrorCodeConstants.REGISTRATION_LINE_NOT_EXISTS);
            }
            RegistrationLineDO registrationLineDO = line.get(0);
            registrationLineDO.setEndTime(LocalDateTime.now());
            long minutes = Duration.between(registrationLineDO.getStartTime(), registrationLineDO.getEndTime()).toMinutes();
            registrationLineDO.setDurationTime((int) minutes);
            registrationLineService.updateRegistrationLine(RegistrationLineConvert.INSTANCE.convert02(registrationLineDO));

            // 关闭计时
            registration.setStatus("N");
            Integer duration = registration.getDurationTime() == null ? 0 : registration.getDurationTime();
            duration += registrationLineDO.getDurationTime();
            registration.setDurationTime(duration);
            registrationService.updateRegistration(RegistrationConvert.INSTANCE.convert01(registration));
        }
        return success(true);
    }


}
