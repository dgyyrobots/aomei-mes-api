package com.dofast.module.qms.controller.admin.ipqc;

import cn.hutool.core.collection.CollUtil;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.api.WorkStationAPi.WorkStationApi;
import com.dofast.module.mes.api.WorkStationAPi.dto.WorkStationDTO;
import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.api.WorkorderApi.WorkorderApi;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderDTO;
import com.dofast.module.qms.controller.admin.defectrecord.vo.DefectRecordBaseVO;
import com.dofast.module.qms.controller.admin.ipqcline.vo.IpqcLineCreateReqVO;
import com.dofast.module.qms.controller.admin.template.vo.TemplateBaseVO;
import com.dofast.module.qms.controller.admin.templateindex.vo.TemplateIndexListVO;
import com.dofast.module.qms.dal.dataobject.template.TemplateDO;
import com.dofast.module.qms.dal.dataobject.templateindex.TemplateIndexDO;
import com.dofast.module.qms.enums.ErrorCodeConstants;
import com.dofast.module.qms.service.defectrecord.DefectRecordService;
import com.dofast.module.qms.service.ipqcline.IpqcLineService;
import com.dofast.module.qms.service.template.TemplateService;
import com.dofast.module.qms.service.templateindex.TemplateIndexService;
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

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.qms.controller.admin.ipqc.vo.*;
import com.dofast.module.qms.dal.dataobject.ipqc.IpqcDO;
import com.dofast.module.qms.convert.ipqc.IpqcConvert;
import com.dofast.module.qms.service.ipqc.IpqcService;

@Tag(name = "质量管理 - 过程检验单")
@RestController
@RequestMapping("/mes/qms/ipqc")
@Validated
public class IpqcController {

    @Resource
    private IpqcService ipqcService;
    @Resource
    private WorkorderApi workorderApi;

    @Resource
    private WorkStationApi workStationApi;

    @Resource
    private TemplateIndexService templateIndexService;

    @Resource
    private TemplateService templateService;

    @Resource
    private IpqcLineService ipqcLineService;

    @Resource
    private DefectRecordService defectRecordService;

    @Resource
    private AutoCodeApi autoCodeApi;

    @PostMapping("/create")
    @Operation(summary = "创建过程检验单")
    @PreAuthorize("@ss.hasPermission('qms:ipqc:create')")
    public CommonResult<Long> createIpqc(@Valid @RequestBody IpqcCreateReqVO createReqVO) {
        if(Constant.NOT_UNIQUE.equals(ipqcService.checkIpqcCodeUnique(createReqVO))){
            return error(ErrorCodeConstants.IPQC_CODE_EXISTS);
        }

        String code = autoCodeApi.genSerialCode("IPQC_CODE",null);
        createReqVO.setIpqcCode(code);
        createReqVO.setIpqcName(code);

        //根据工单获取产品信息
        WorkorderDTO workorder = workorderApi.getWorkorder(createReqVO.getWorkorderCode());
        createReqVO.setWorkorderId(workorder.getId());
        createReqVO.setWorkorderCode(workorder.getWorkorderCode());
        createReqVO.setWorkorderName(workorder.getWorkorderName());
        createReqVO.setItemId(workorder.getProductId());
        createReqVO.setItemCode(workorder.getProductCode());
        createReqVO.setItemName(workorder.getProductName());
        createReqVO.setSpecification(workorder.getProductSpc());
        createReqVO.setUnitOfMeasure(workorder.getUnitOfMeasure());
        WorkStationDTO workStationDTO = workStationApi.getWorkstation(createReqVO.getWorkstationCode(), createReqVO.getProcessCode());
        createReqVO.setWorkstationId(workStationDTO.getId());
        createReqVO.setWorkstationCode(workStationDTO.getWorkstationCode());
        createReqVO.setWorkstationName(workStationDTO.getWorkstationName());

        //根据产品和检测类型获取检测模板
        TemplateBaseVO param = new TemplateBaseVO();
        param.setQcTypes(createReqVO.getIpqcType());
        param.setItemId(workorder.getProductId());
        TemplateDO template = templateService.selectQcTemplateByProductAndQcType(param);
        if(StrUtils.isNotNull(template)){
            createReqVO.setTemplateId(template.getId());
        }else{
            return error(ErrorCodeConstants.WORKORDER_NOT_HAS_IPQC_TEMPLATE);
        }
        Long id = ipqcService.createIpqc(createReqVO);
        createReqVO.setId(id);
        //生成行信息
        generateLine(createReqVO);
        return success(id);
    }

    /**
     * 根据头信息生成行信息
     * @param iqc
     */
    private void generateLine(IpqcBaseVO iqc){
        TemplateIndexListVO param = new TemplateIndexListVO();
        param.setTemplateId(iqc.getTemplateId());
        List<TemplateIndexDO > indexs = templateIndexService.getTemplateIndexList(param);
        if(CollUtil.isNotEmpty(indexs)){
            for (TemplateIndexDO index:indexs
            ) {
                IpqcLineCreateReqVO line = new IpqcLineCreateReqVO();
                line.setIpqcId(iqc.getId());
                line.setIndexId(index.getIndexId());
                line.setIndexCode(index.getIndexCode());
                line.setIndexName(index.getIndexName());
                line.setIndexType(index.getIndexType());
                line.setQcTool(index.getQcTool());
                line.setCheckMethod(index.getCheckMethod());
                line.setStanderVal(index.getStanderVal());
                line.setUnitOfMeasure(index.getUnitOfMeasure());
                line.setThresholdMax(index.getThresholdMax());
                line.setThresholdMin(index.getThresholdMin());
                line.setCrQuantity(new BigDecimal(0L));
                line.setMajQuantity(new BigDecimal(0L));
                line.setMajQuantity(new BigDecimal(0L));
                ipqcLineService.createIpqcLine(line);
            }
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新过程检验单")
    @PreAuthorize("@ss.hasPermission('qms:ipqc:update')")
    public CommonResult<Boolean> updateIpqc(@Valid @RequestBody IpqcUpdateReqVO updateReqVO) {
        if(Constant.NOT_UNIQUE.equals(ipqcService.checkIpqcCodeUnique(updateReqVO))){
            return error(ErrorCodeConstants.IPQC_CODE_EXISTS);
        }

        //根据工单获取产品信息
        WorkorderDTO workorder = workorderApi.getWorkorder(updateReqVO.getWorkorderId());
        updateReqVO.setWorkorderId(workorder.getId());
        updateReqVO.setWorkorderCode(workorder.getWorkorderCode());
        updateReqVO.setWorkorderName(workorder.getWorkorderName());
        updateReqVO.setItemId(workorder.getProductId());
        updateReqVO.setItemCode(workorder.getProductCode());
        updateReqVO.setItemName(workorder.getProductName());
        updateReqVO.setSpecification(workorder.getProductSpc());
        updateReqVO.setUnitOfMeasure(workorder.getUnitOfMeasure());

        //根据产品和检测类型获取检测模板
        TemplateBaseVO param = new TemplateBaseVO();
        param.setQcTypes(updateReqVO.getIpqcType());
        param.setItemId(workorder.getProductId());
        TemplateDO template = templateService.selectQcTemplateByProductAndQcType(param);
        if(StrUtils.isNotNull(template)){
            updateReqVO.setTemplateId(template.getId());
        }else{
            return error(ErrorCodeConstants.WORKORDER_NOT_HAS_IPQC_TEMPLATE);
        }

        ipqcService.updateIpqc(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除过程检验单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('qms:ipqc:delete')")
    public CommonResult<Boolean> deleteIpqc(@RequestParam("id") Long id) {
        IpqcDO ipqc = ipqcService.getIpqc(id);
        if(!Constant.ORDER_STATUS_PREPARE.equals(ipqc.getStatus())){
            return error(ErrorCodeConstants.CAN_NOT_DELETE);
        }

        ipqcLineService.deleteByIpqcId(id); //删除对应的行信息
        DefectRecordBaseVO p2 = new DefectRecordBaseVO();
        p2.setQcId(id);
        p2.setQcType(Constant.QC_TYPE_IPQC);
        defectRecordService.deleteByQcIdAndType(p2);//删除对应的缺陷记录
        ipqcService.deleteIpqc(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得过程检验单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('qms:ipqc:query')")
    public CommonResult<IpqcRespVO> getIpqc(@RequestParam("id") Long id) {
        IpqcDO ipqc = ipqcService.getIpqc(id);
        return success(IpqcConvert.INSTANCE.convert(ipqc));
    }

    @GetMapping("/list")
    @Operation(summary = "获得过程检验单列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('qms:ipqc:query')")
    public CommonResult<List<IpqcRespVO>> getIpqcList(@RequestParam("ids") Collection<Long> ids) {
        List<IpqcDO> list = ipqcService.getIpqcList(ids);
        return success(IpqcConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得过程检验单分页")
    @PreAuthorize("@ss.hasPermission('qms:ipqc:query')")
    public CommonResult<PageResult<IpqcRespVO>> getIpqcPage(@Valid IpqcPageReqVO pageVO) {
        PageResult<IpqcDO> pageResult = ipqcService.getIpqcPage(pageVO);
        return success(IpqcConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出过程检验单 Excel")
    @PreAuthorize("@ss.hasPermission('qms:ipqc:export')")
    @OperateLog(type = EXPORT)
    public void exportIpqcExcel(@Valid IpqcExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<IpqcDO> list = ipqcService.getIpqcList(exportReqVO);
        // 导出 Excel
        List<IpqcExcelVO> datas = IpqcConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "过程检验单.xls", "数据", IpqcExcelVO.class, datas);
    }

}
