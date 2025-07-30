package com.dofast.module.wms.controller.admin.feedline.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 上料详情分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedLinePageReqVO extends PageParam {

    @Schema(description = "领料单ID", example = "12992")
    private Long issueId;

    @Schema(description = "库存ID", example = "9685")
    private Long materialStockId;

    @Schema(description = "产品物料ID", example = "29929")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "张三")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "上料数量")
    private Double quantity;

    @Schema(description = "上料批次号")
    private String batchCode;

    @Schema(description = "仓库ID", example = "30593")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "李四")
    private String warehouseName;

    @Schema(description = "库区ID", example = "3730")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "赵六")
    private String locationName;

    @Schema(description = "库位ID", example = "11218")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "赵六")
    private String areaName;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "工单号")
    private String workorderCode;

    @Schema(description = "任务编号")
    private String taskCode;

    @Schema(description = "任务名称", example = "李四")
    private String taskName;

    @Schema(description = "工作站编码")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "李四")
    private String workstationName;

    @Schema(description = "上料状态", example = "2")
    private String status;

    @Schema(description = "供应商编码", example = "S1865")
    private String vendorCode;

    @Schema(description = "报工状态", example = "Y")
    private String feedbackStatus;

    @Schema(description = "报工单号")
    private String feedbackCode;

    @Schema(description = "生产设备名称")
    private String machineryName;

    @Schema(description = "生产设备编码")
    private String machineryCode;

    @Schema(description = "生产设备ID")
    private String machineryId;

    @Schema(description = "条码编号")
    private Long barcodeNumber;

    @Schema(description = "ERP项次")
    private Long sequence;

    @Schema(description = "ERP项次")
    private Long sequenceOrder;

    @Schema(description = "ERP批次")
    private String erpBatchCode;

    @Schema(description = "母批次")
    private String parentBatchCode;
}
