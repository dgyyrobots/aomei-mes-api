package com.dofast.module.wms.controller.admin.feedline.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 上料详情 Excel VO
 *
 * @author 惠智造
 */
@Data
public class FeedLineExcelVO {

    @ExcelProperty("行ID")
    private Long id;

    @ExcelProperty("领料单ID")
    private Long issueId;

    @ExcelProperty("库存ID")
    private Long materialStockId;

    @ExcelProperty("产品物料ID")
    private Long itemId;

    @ExcelProperty("产品物料编码")
    private String itemCode;

    @ExcelProperty("产品物料名称")
    private String itemName;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("上料数量")
    private Double quantity;

    @ExcelProperty("上料批次号")
    private String batchCode;

    @ExcelProperty("仓库ID")
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

    @ExcelProperty("工单号")
    private String workorderCode;

    @ExcelProperty("任务编号")
    private String taskCode;

    @ExcelProperty("任务名称")
    private String taskName;

    @ExcelProperty("工作站编码")
    private String workstationCode;

    @ExcelProperty("工作站名称")
    private String workstationName;

    @ExcelProperty("上料状态")
    private String status;

    @ExcelProperty("供应商编码")
    private String vendorCode;

    @ExcelProperty("报工状态")
    private String feedbackStatus;

    @ExcelProperty("报工单号")
    private String feedbackCode;

    @ExcelProperty("生产设备名称")
    private String machineryName;

    @ExcelProperty("生产设备编码")
    private String machineryCode;

    @ExcelProperty("生产设备ID")
    private String machineryId;

    @ExcelProperty("条码编号")
    private Long barcodeNumber;

    @ExcelProperty("ERP项次")
    private Long sequence;

    @ExcelProperty("ERP项次")
    private Long sequenceOrder;

    @ExcelProperty("ERP批次")
    private String erpBatchCode;

    @ExcelProperty("母批次")
    private String parentBatchCode;

}
