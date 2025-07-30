package com.dofast.module.wms.controller.admin.materialstock.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 库存记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialStockPageReqVO extends PageParam {

    @Schema(description = "物料类型ID", example = "27423")
    private Long itemTypeId;

    @Schema(description = "产品物料ID", example = "9819")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "赵六")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "入库批次号")
    private String batchCode;

    @Schema(description = "仓库ID", example = "27965")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "张三")
    private String warehouseName;

    @Schema(description = "库区ID", example = "23466")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "王五")
    private String locationName;

    @Schema(description = "库位ID", example = "14049")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "赵六")
    private String areaName;

    @Schema(description = "供应商ID", example = "19658")
    private Long vendorId;

    @Schema(description = "供应商编号")
    private String vendorCode;

    @Schema(description = "供应商名称", example = "芋艿")
    private String vendorName;

    @Schema(description = "供应商简称")
    private String vendorNick;

    @Schema(description = "在库数量")
    private BigDecimal quantityOnhand;

    @Schema(description = "生产工单ID", example = "26985")
    private Long workorderId;

    @Schema(description = "生产工单编号")
    private String workorderCode;

    @Schema(description = "入库时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] recptDate;

    @Schema(description = "库存有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expireDate;

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

    @Schema(description = "入库状态(Y/N)", example = "2")
    private String recptStatus;

    @Schema(description = "ERP批次")
    private String erpBatchCode;

    @Schema(description = "来源Id")
    private Long originId;

    @Schema(description = "母批次号")
    private String parentBatchCode;

    @Schema(description = "仓库确认状态")
    private String confirmStatus;
}
