package com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.validation.constraints.NotNull;

/**
 * 报工入库日志 Excel VO
 *
 * @author 惠智造
 */
@Data
public class FeedbackWarehousingLogExcelVO {

    @ExcelProperty("记录ID")
    private Long id;

    @ExcelProperty("报工单编号")
    private String feedbackCode;

    @ExcelProperty("工作站ID")
    private Long workstationId;

    @ExcelProperty("工作站编号")
    private String workstationCode;

    @ExcelProperty("工作站名称")
    private String workstationName;

    @ExcelProperty("生产工单ID")
    private Long workorderId;

    @ExcelProperty("生产工单编号")
    private String workorderCode;

    @ExcelProperty("生产工单名称")
    private String workorderName;

    @ExcelProperty("工序ID")
    private Long processId;

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("生产任务ID")
    private Long taskId;

    @ExcelProperty("生产任务编号")
    private String taskCode;

    @ExcelProperty("产品物料ID")
    private Long itemId;

    @ExcelProperty("产品物料编码")
    private String itemCode;

    @ExcelProperty("产品物料名称")
    private String itemName;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("本次报工数量")
    private Double quantityFeedback;

    @ExcelProperty("合格品数量")
    private Double quantityQualified;

    @ExcelProperty("不良品数量")
    private Double quantityUnquanlified;

    @ExcelProperty("报工用户名")
    private String userName;

    @ExcelProperty("昵称")
    private String nickName;

    @ExcelProperty("批次号")
    private String batchCode;

    @ExcelProperty("ERP批次号")
    private String erpBatchCode;

    @ExcelProperty("生产设备名称")
    private String machineryName;

    @ExcelProperty("生产设备编码")
    private String machineryCode;

    @ExcelProperty("生产设备ID")
    private Long machineryId;

    @ExcelProperty("ERP报工单(用于撤销)")
    private String erpFeedback;

    @ExcelProperty("ERP报工标识")
    private String erpFeedbackStatus;

    @ExcelProperty("ERP入库标识")
    private String erpWarehousingStatus;

    @ExcelProperty("转换数量")
    private BigDecimal conversionQuantity;

    @ExcelProperty("转换单位")
    private String conversionUnit;

    @ExcelProperty("转换不合格数量")
    private BigDecimal conversionQuantityUnquanlified;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("预留字段1")
    private String attr1;

    @ExcelProperty("预留字段2")
    private String attr2;

    @ExcelProperty("预留字段3")
    private Integer attr3;

    @ExcelProperty("预留字段4")
    private Integer attr4;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("仓库ID不能为空")
    private Long warehouseId;

    @ExcelProperty("仓库编码")
    private String warehouseCode;

    @ExcelProperty("仓库名称")
    private String warehouseName;

    @ExcelProperty("库区ID")
    private Long locationId;

    @ExcelProperty("库区编码")
    private String locationCode;

    @ExcelProperty("库区名称")
    private String locationName;

    @ExcelProperty("库位ID")
    private Long areaId;

    @ExcelProperty("库位编码")
    private String areaCode;

    @ExcelProperty("库位名称")
    private String areaName;


}
