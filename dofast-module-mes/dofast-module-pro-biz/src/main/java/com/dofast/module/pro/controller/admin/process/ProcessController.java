package com.dofast.module.pro.controller.admin.process;

import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.enums.ErrorCodeConstants;
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

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.pro.controller.admin.process.vo.*;
import com.dofast.module.pro.dal.dataobject.process.ProcessDO;
import com.dofast.module.pro.convert.process.ProcessConvert;
import com.dofast.module.pro.service.process.ProcessService;

@Tag(name = "生产管理 - 生产工序")
@RestController
@RequestMapping("/mes/pro/cess")
@Validated
public class ProcessController {

    @Resource
    private ProcessService processService;

    @PostMapping("/create")
    @Operation(summary = "创建生产工序")
    @PreAuthorize("@ss.hasPermission('pro:cess:create')")
    public CommonResult<Long> createcess(@Valid @RequestBody ProcessCreateReqVO createReqVO) {
        if (Constant.NOT_UNIQUE.equals(processService.checkProcessCodeUnique(createReqVO))) {
            return error(ErrorCodeConstants.CESS_CODE_EXISTS);
        }
        if (Constant.NOT_UNIQUE.equals(processService.checkProcessNameUnique(createReqVO))) {
            return error(ErrorCodeConstants.CESS_NAME_EXISTS);
        }
        return success(processService.createcess(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新生产工序")
    @PreAuthorize("@ss.hasPermission('pro:cess:update')")
    public CommonResult<Boolean> updatecess(@Valid @RequestBody ProcessUpdateReqVO updateReqVO) {
        if (Constant.NOT_UNIQUE.equals(processService.checkProcessCodeUnique(updateReqVO))) {
            return error(ErrorCodeConstants.CESS_CODE_EXISTS);
        }
        if (Constant.NOT_UNIQUE.equals(processService.checkProcessNameUnique(updateReqVO))) {
            return error(ErrorCodeConstants.CESS_NAME_EXISTS);
        }
        processService.updatecess(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生产工序")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:cess:delete')")
    public CommonResult<Boolean> deletecess(@RequestParam("id") Long id) {
        processService.deletecess(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得生产工序")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:cess:query')")
    public CommonResult<ProcessRespVO> getcess(@RequestParam("id") Long id) {
        ProcessDO cess = processService.getcess(id);
        return success(ProcessConvert.INSTANCE.convert(cess));
    }

    /**
     * 查询所有可用工序的清单
     *
     * @return
     */
    @Operation(summary = "获得生产工序列表")
    @PreAuthorize("@ss.hasPermission('pro:cess:query')")
    @GetMapping("/listAll")
    public CommonResult<List<ProcessRespVO>> listAll() {
        ProcessListVO process = new ProcessListVO();
        process.setEnableFlag("Y");
        List<ProcessDO> list = processService.selectAll(process);
        return success(ProcessConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list")
    @Operation(summary = "获得生产工序列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pro:cess:query')")
    public CommonResult<List<ProcessRespVO>> getcessList(@RequestParam("ids") Collection<Long> ids) {
        List<ProcessDO> list = processService.getcessList(ids);
        return success(ProcessConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获得生产工序简单列表")
    @PreAuthorize("@ss.hasPermission('pro:cess:query')")
    public CommonResult<List<ProcessSimpleVO>> getcessSimpleList() {
        List<ProcessSimpleVO> list = processService.getcessSimpleList();
        return success(list);
    }

    @GetMapping("/page")
    @Operation(summary = "获得生产工序分页")
    @PreAuthorize("@ss.hasPermission('pro:cess:query')")
    public CommonResult<PageResult<ProcessRespVO>> getcessPage(@Valid ProcessPageReqVO pageVO) {
        PageResult<ProcessDO> pageResult = processService.getcessPage(pageVO);
        return success(ProcessConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出生产工序 Excel")
    @PreAuthorize("@ss.hasPermission('pro:cess:export')")
    @OperateLog(type = EXPORT)
    public void exportcessExcel(@Valid ProcessExportReqVO exportReqVO,
                                HttpServletResponse response) throws IOException {
        List<ProcessDO> list = processService.getcessList(exportReqVO);
        // 导出 Excel
        List<ProcessExcelVO> datas = ProcessConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "生产工序.xls", "数据", ProcessExcelVO.class, datas);
    }

    /**
     * 积木报表-查询工序
     *
     * @return
     */
    @Operation(summary = "获得生产工序列表")
    @PreAuthorize("@ss.hasPermission('pro:cess:query')")
    @GetMapping("/getProcessJson")
    public List<Map<String, Object>> getProcessJson() {
        ProcessListVO process = new ProcessListVO();
        process.setEnableFlag("Y");
        List<ProcessDO> list = processService.selectAll(process);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (ProcessDO processDO : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", processDO.getProcessName());
            map.put("value", processDO.getProcessCode());
            resultList.add(map);
        }
        return resultList;
    }

}
