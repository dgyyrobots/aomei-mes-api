package com.dofast.module.wms.controller.admin.allocatedrecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 调拨单身记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AllocatedRecordPageReqVO extends PageParam {

    @Schema(description = "调拨单ID", example = "3803")
    private Long allocatedId;

    @Schema(description = "库存ID", example = "10050")
    private Long materialStockId;

    @Schema(description = "产品物料ID", example = "223")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "赵六")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "调拨数量")
    private Double quantityAllocated;

    @Schema(description = "调拨批次号")
    private String batchCode;

    @Schema(description = "仓库ID", example = "10957")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "张三")
    private String warehouseName;

    @Schema(description = "库区ID", example = "6900")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "赵六")
    private String locationName;

    @Schema(description = "库位ID", example = "885")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "张三")
    private String areaName;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "调拨标识", example = "Y")
    private String allocatedFlag;

    @Schema(description = "供应商编码", example = "S1865")
    private String vendorCode;

    @Schema(description = "母批次")
    private String parentBatchCode;

    @Schema(description = "ERP状态")
    private String erpStatus;


}
