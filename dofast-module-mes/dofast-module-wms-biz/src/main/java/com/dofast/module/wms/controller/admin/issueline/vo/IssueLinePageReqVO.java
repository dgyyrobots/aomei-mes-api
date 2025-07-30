package com.dofast.module.wms.controller.admin.issueline.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 生产领料单行分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IssueLinePageReqVO extends PageParam {

    @Schema(description = "领料单ID", example = "9138")
    private Long issueId;

    @Schema(description = "库存ID", example = "13860")
    private Long materialStockId;

    @Schema(description = "产品物料ID", example = "12707")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "李四")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "领料数量")
    private BigDecimal quantityIssued;

    @Schema(description = "领料批次号")
    private String batchCode;

    @Schema(description = "仓库ID", example = "31301")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "赵六")
    private String warehouseName;

    @Schema(description = "库区ID", example = "20171")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "赵六")
    private String locationName;

    @Schema(description = "库位ID", example = "27981")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "王五")
    private String areaName;

    @Schema(description = "备注", example = "你说的对")
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

    @Schema(description = "领料状态(Y/N)", example = "1")
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

    @Schema(description = "启用标识")
    private String enableFlag;

    @Schema(description = "ERP项次")
    private Long sequence;

    @Schema(description = "ERP项次")
    private Long sequenceOrder;

    @Schema(description = "ERP批次")
    private String erpBatchCode;

    @Schema(description = "ERP调用标识")
    private String erpEnable;

    @Schema(description = "母批次")
    private String parentBatchCode;

    @Schema(description = "创建人")
    private Long creator;
}
