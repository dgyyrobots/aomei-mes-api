package com.dofast.module.qms.controller.admin.iqc;

import cn.hutool.core.collection.CollUtil;
import com.dofast.framework.security.core.LoginUser;
import com.dofast.framework.security.core.util.SecurityFrameworkUtils;
import com.dofast.module.mes.api.ItemApi.MdItemApi;
import com.dofast.module.mes.api.ItemApi.dto.MdItemDTO;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.qms.controller.admin.defectrecord.vo.DefectRecordBaseVO;
import com.dofast.module.qms.controller.admin.iqcline.vo.IqcLineCreateReqVO;
import com.dofast.module.qms.controller.admin.templateindex.vo.TemplateIndexListVO;
import com.dofast.module.qms.controller.admin.templateproduct.vo.TemplateProductListVO;
import com.dofast.module.qms.dal.dataobject.iqcline.IqcLineDO;
import com.dofast.module.qms.dal.dataobject.templateindex.TemplateIndexDO;
import com.dofast.module.qms.dal.dataobject.templateproduct.TemplateProductDO;
import com.dofast.module.qms.enums.ErrorCodeConstants;
import com.dofast.module.qms.service.defectrecord.DefectRecordService;
import com.dofast.module.qms.service.iqcline.IqcLineService;
import com.dofast.module.qms.service.templateindex.TemplateIndexService;
import com.dofast.module.qms.service.templateproduct.TemplateProductService;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
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

import com.dofast.module.qms.controller.admin.iqc.vo.*;
import com.dofast.module.qms.dal.dataobject.iqc.IqcDO;
import com.dofast.module.qms.convert.iqc.IqcConvert;
import com.dofast.module.qms.service.iqc.IqcService;

@Tag(name = "质量管理 - 来料检验单")
@RestController
@RequestMapping("/mes/qms/iqc")
@Validated
public class IqcController {

    @Resource
    private IqcService iqcService;
    @Resource
    private TemplateProductService templateProductService;
    @Resource
    private TemplateIndexService templateIndexService;
    @Resource
    private IqcLineService iqcLineService;
    @Resource
    private DefectRecordService defectRecordService;
    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private MdItemApi mdItemApi;

    @PostMapping("/create")
    @Operation(summary = "创建来料检验单")
    @PreAuthorize("@ss.hasPermission('qms:iqc:create')")
    public CommonResult<Long> createIqc(@Valid @RequestBody IqcCreateReqVO createReqVO) {
        if(Constant.NOT_UNIQUE.equals(iqcService.checkIqcCodeUnique(createReqVO))){
            return error(ErrorCodeConstants.IQC_CODE_EXISTS);
        }
        // 来料单基于采购单创建, 所以只能获取物料编码
        MdItemDTO mdItemDTO = mdItemApi.getMdItemByCode(createReqVO.getItemCode());
        TemplateProductListVO param = new TemplateProductListVO();
        param.setItemId(mdItemDTO.getId());
        List<TemplateProductDO> templates = templateProductService.getTemplateProductListNotContainProcessCode(param);
        if(CollUtil.isNotEmpty(templates)){
            createReqVO.setTemplateId(templates.get(0).getTemplateId());
        }else{
            return error(ErrorCodeConstants.IQC_PRODUCT_NOT_EXISTS);
        }
        createReqVO.setItemId(mdItemDTO.getId());
        //设置检测人
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(user.getId());
        createReqVO.setInspector(adminUserRespDTO.getNickname());
        Long id = iqcService.createIqc(createReqVO);
        createReqVO.setId(id);
        generateLine(createReqVO);
        return success(id);
    }

    /**
     * 根据头信息生成行信息
     * @param iqc
     */
    private void generateLine(IqcCreateReqVO iqc){
        TemplateIndexListVO param = new TemplateIndexListVO();
        param.setTemplateId(iqc.getTemplateId());
        List<TemplateIndexDO> indexs = templateIndexService.getTemplateIndexList(param);
        if(CollUtil.isNotEmpty(indexs)){
            for (TemplateIndexDO index:indexs
            ) {
                IqcLineCreateReqVO line = new IqcLineCreateReqVO();
                line.setIqcId(iqc.getId());
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
                line.setCrQuantity(BigDecimal.ONE);
                line.setMajQuantity(BigDecimal.ONE);
                line.setMajQuantity(BigDecimal.ONE);
                iqcLineService.createIqcLine(line);
            }
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新来料检验单")
    @PreAuthorize("@ss.hasPermission('qms:iqc:update')")
    public CommonResult<Boolean> updateIqc(@Valid @RequestBody IqcUpdateReqVO updateReqVO) {
        if(Constant.NOT_UNIQUE.equals(iqcService.checkIqcCodeUnique(updateReqVO))){
            return error(ErrorCodeConstants.IQC_CODE_EXISTS);
        }
        iqcService.updateIqc(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除来料检验单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('qms:iqc:delete')")
    public CommonResult<Boolean> deleteIqc(@RequestParam("id") Long id) {
        IqcDO iqc = iqcService.getIqc(id);
        if(!Constant.ORDER_STATUS_PREPARE.equals(iqc.getStatus())){
            return error(ErrorCodeConstants.CAN_NOT_DELETED);
        }
        iqcLineService.deleteByIqcId(id);
        DefectRecordBaseVO p2 = new DefectRecordBaseVO();
        p2.setQcId(id);
        p2.setQcType(Constant.QC_TYPE_IQC);
        defectRecordService.deleteByQcIdAndType(p2);//删除对应的缺陷记录
        iqcService.deleteIqc(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得来料检验单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('qms:iqc:query')")
    public CommonResult<IqcRespVO> getIqc(@RequestParam("id") Long id) {
        IqcDO iqc = iqcService.getIqc(id);
        return success(IqcConvert.INSTANCE.convert(iqc));
    }

    @GetMapping("/list")
    @Operation(summary = "获得来料检验单列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('qms:iqc:query')")
    public CommonResult<List<IqcRespVO>> getIqcList(@RequestParam("ids") Collection<Long> ids) {
        List<IqcDO> list = iqcService.getIqcList(ids);
        return success(IqcConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得来料检验单分页")
    @PreAuthorize("@ss.hasPermission('qms:iqc:query')")
    public CommonResult<PageResult<IqcRespVO>> getIqcPage(@Valid IqcPageReqVO pageVO) {
        PageResult<IqcDO> pageResult = iqcService.getIqcPage(pageVO);
        return success(IqcConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出来料检验单 Excel")
    @PreAuthorize("@ss.hasPermission('qms:iqc:export')")
    @OperateLog(type = EXPORT)
    public void exportIqcExcel(@Valid IqcExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<IqcDO> list = iqcService.getIqcList(exportReqVO);
        // 导出 Excel
        List<IqcExcelVO> datas = IqcConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "来料检验单.xls", "数据", IqcExcelVO.class, datas);
    }

    //
    @PutMapping("/updateIqcAdjuncts")
    @Operation(summary = "更新生产工单")
    @PreAuthorize("@ss.hasPermission('qms:iqc:update')")
    public CommonResult<Boolean> updateIqcAdjuncts( @RequestBody IqcUpdateReqVO updateReqVO) {
        IqcDO iqc = iqcService.getIqc(updateReqVO.getId());
        if(iqc == null){
            return success(true);
        }

        String url = updateReqVO.getAdjuncts();
        if(url==null){
            return success(true);
        }
        // 将url基于","进行拆分
        String[] urls = url.split(",");
        if (urls.length == 0) {
            return success(true);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < urls.length; i++) {
            String currentUrl = urls[i].trim();

            if (currentUrl.isEmpty()) {
                continue;
            }
            // 如果需要去掉路径部分，只保留文件名
            String[] parts = currentUrl.split("/");
            // 校验当前parts是否包含%, 若包含只保留%前信息
            if( parts[parts.length - 1].contains("%")) {
                parts[parts.length - 1] = parts[parts.length - 1].substring(0, parts[parts.length - 1].indexOf("%"));
                if(parts[parts.length - 1].contains("?")){
                    parts[parts.length - 1] = parts[parts.length - 1].substring(0, parts[parts.length - 1].indexOf("?"));
                }
            }
            String finName = parts[parts.length - 1];
            System.out.println(finName);
            sb.append(finName);
            // 校验当前url是否为最后一个
            if (i != urls.length - 1) {
                sb.append(",");
            }
        }
        String adjuncts = Optional.ofNullable(sb.toString()).orElse("");
        if( adjuncts!="") {
            iqc.setAdjuncts(adjuncts);
        }
        iqcService.updateIqc(IqcConvert.INSTANCE.convert01(iqc));
        return success(true);
    }

}
