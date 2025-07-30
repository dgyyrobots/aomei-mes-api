package com.dofast.module.wms.controller.admin.feedline;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.dal.mysql.feedline.FeedLineMapper;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.issueline.IssueLineService;
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

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.wms.controller.admin.feedline.vo.*;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.module.wms.convert.feedline.FeedLineConvert;
import com.dofast.module.wms.service.feedline.FeedLineService;

@Tag(name = "管理后台 - 上料详情")
@RestController
@RequestMapping("/wms/feed-line")
@Validated
public class FeedLineController {

    @Resource
    private FeedLineService feedLineService;

    @Resource
    private FeedLineMapper feedLineMapper;

    @Resource
    private IssueLineService issueLineService;

    @Resource
    private IssueHeaderService issueHeaderService;

    @Resource
    private TaskApi taskService;

    @PostMapping("/create")
    @Operation(summary = "创建上料详情")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:create')")
    public CommonResult<Long> createFeedLine(@Valid @RequestBody FeedLineCreateReqVO createReqVO) {
        createReqVO.setStatus("Y");
        return success(feedLineService.createFeedLine(createReqVO));
    }

    @PostMapping("/createList")
    @Operation(summary = "创建上料详情")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:create')")
    public CommonResult<Boolean> createFeedLine(@RequestBody List<FeedLineCreateReqVO> createReqVO) {
        System.out.println("createReqVO: " + createReqVO);
        List<FeedLineDO> createReqVOList = new ArrayList<>();
        for (FeedLineCreateReqVO vo : createReqVO) {
            FeedLineDO lineDO = FeedLineConvert.INSTANCE.convert(vo);
            lineDO.setStatus("Y");
            createReqVOList.add(lineDO);
        }
        feedLineMapper.insertBatch(createReqVOList);
        return success(true);
    }

    @PostMapping("/createListByIssueId")
    @Operation(summary = "创建上料详情")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:create')")
    public CommonResult<Boolean> createFeedLine(@RequestBody Long issueId) {
        System.out.println("issueId: " + issueId);
        IssueHeaderDO issueHeader = issueHeaderService.getIssueHeader(issueId);
        List<IssueLineDO> issueLines = issueLineService.getIssueLineByHeaderId(issueId);
        List<FeedLineDO> createReqVOList = new ArrayList<>();
        for (IssueLineDO issueLine : issueLines) {
            TaskDTO task = taskService.getTask(issueHeader.getTaskId());
            FeedLineDO lineDO = new FeedLineDO();
            lineDO.setTaskCode(issueHeader.getTaskCode());
            lineDO.setTaskName(task.getTaskName());
            lineDO.setAreaName(issueLine.getAreaName());
            lineDO.setAreaCode(issueLine.getAreaCode());
            lineDO.setAreaId(issueLine.getAreaId());
            lineDO.setLocationCode(issueLine.getLocationCode());
            lineDO.setLocationName(issueLine.getLocationName());
            lineDO.setLocationId(issueLine.getLocationId());
            lineDO.setWarehouseCode(issueLine.getWarehouseCode());
            lineDO.setWarehouseName(issueLine.getWarehouseName());
            lineDO.setWarehouseId(issueLine.getWarehouseId());
            lineDO.setIssueId(issueId);
            lineDO.setMaterialStockId(issueLine.getMaterialStockId());
            lineDO.setItemId(issueLine.getItemId());
            lineDO.setItemCode(issueLine.getItemCode());
            lineDO.setItemName(issueLine.getItemName());
            lineDO.setSpecification(issueLine.getSpecification());
            lineDO.setUnitOfMeasure(issueLine.getUnitOfMeasure());
            lineDO.setQuantity(issueLine.getQuantityIssued().doubleValue());
            lineDO.setBatchCode(issueLine.getBatchCode());
            lineDO.setWorkorderCode(issueHeader.getWorkorderCode());
            lineDO.setWorkstationCode(issueHeader.getWorkstationCode());
            lineDO.setWorkstationName(issueHeader.getWorkstationName());
            lineDO.setVendorCode(issueLine.getVendorCode());
            lineDO.setStatus("Y");
            createReqVOList.add(lineDO);
        }
        feedLineMapper.insertBatch(createReqVOList);
        return success(true);
    }


    @PutMapping("/update")
    @Operation(summary = "更新上料详情")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:update')")
    public CommonResult<Boolean> updateFeedLine(@Valid @RequestBody FeedLineUpdateReqVO updateReqVO) {
        feedLineService.updateFeedLine(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除上料详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wms:feed-line:delete')")
    public CommonResult<Boolean> deleteFeedLine(@RequestParam("id") Long id) {
        feedLineService.deleteFeedLine(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得上料详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:query')")
    public CommonResult<FeedLineRespVO> getFeedLine(@RequestParam("id") Long id) {
        FeedLineDO feedLine = feedLineService.getFeedLine(id);
        return success(FeedLineConvert.INSTANCE.convert(feedLine));
    }

    @GetMapping("/getByIssueId")
    @Operation(summary = "获得上料详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:query')")
    public CommonResult<List<FeedLineRespVO>> getByIssueId(@RequestParam("id") Long id) {
        FeedLineExportReqVO feedLine = new FeedLineExportReqVO();
        feedLine.setIssueId(id);
        List<FeedLineDO> feedList = feedLineMapper.selectList(feedLine);
        return success(FeedLineConvert.INSTANCE.convertList(feedList));
    }

    @GetMapping("/getByTaskId")
    @Operation(summary = "获得上料详情")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:query')")
    public CommonResult<List<FeedLineRespVO>> getByTaskId(@RequestParam("id") Long id) {
        FeedLineExportReqVO feedLine = new FeedLineExportReqVO();
        TaskDTO task = taskService.getTask(id);
        feedLine.setTaskCode(task.getTaskCode());
        // 基于任务编号获取上料记录
        List<FeedLineDO> feedList = feedLineMapper.selectList(feedLine);
        return success(FeedLineConvert.INSTANCE.convertList(feedList));
    }

    @GetMapping("/list")
    @Operation(summary = "获得上料详情列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:query')")
    public CommonResult<List<FeedLineRespVO>> getFeedLineList(@RequestParam("ids") Collection<Long> ids) {
        List<FeedLineDO> list = feedLineService.getFeedLineList(ids);
        return success(FeedLineConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得上料详情分页")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:query')")
    public CommonResult<PageResult<FeedLineRespVO>> getFeedLinePage(@Valid FeedLinePageReqVO pageVO) {
        PageResult<FeedLineDO> pageResult = feedLineService.getFeedLinePage(pageVO);
        return success(FeedLineConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出上料详情 Excel")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:export')")
    @OperateLog(type = EXPORT)
    public void exportFeedLineExcel(@Valid FeedLineExportReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        List<FeedLineDO> list = feedLineService.getFeedLineList(exportReqVO);
        // 导出 Excel
        List<FeedLineExcelVO> datas = FeedLineConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "上料详情.xls", "数据", FeedLineExcelVO.class, datas);
    }

    @GetMapping("/summeryPage")
    @Operation(summary = "获得上料详情分页")
    @PreAuthorize("@ss.hasPermission('wms:feed-line:query')")
    public CommonResult<IPage<FeedLineSummaryVO>> getSummeryFeedLinePage(@Valid FeedLinePageReqVO pageVO) {
        IPage<FeedLineSummaryVO> pageResult = feedLineService.selectMaterialUsageSummary(pageVO);
        return success(pageResult);
    }

}
