package com.dofast.module.wms.controller.admin.transaction.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 库存事务 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TransactionBaseVO {
    /**
     * 是否检查库存量
     * 如果设置为True则库存不允许为负
     */
    private boolean storageCheckFlag;
    @Schema(description = "事务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "事务类型不能为空")
    private String transactionType;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11817")
    @NotNull(message = "产品物料ID不能为空")
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

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16585")
    @NotNull(message = "仓库ID不能为空")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "王五")
    private String warehouseName;

    @Schema(description = "库区ID", example = "13439")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "赵六")
    private String locationName;

    @Schema(description = "库位ID", example = "8135")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "王五")
    private String areaName;

    @Schema(description = "供应商ID", example = "28868")
    private Long vendorId;

    @Schema(description = "供应商编号")
    private String vendorCode;

    @Schema(description = "供应商名称", example = "芋艿")
    private String vendorName;

    @Schema(description = "供应商简称")
    private String vendorNick;

    @Schema(description = "单据类型", example = "2")
    private String sourceDocType;

    @Schema(description = "单据ID", example = "4120")
    private Long sourceDocId;

    @Schema(description = "单据编号")
    private String sourceDocCode;

    @Schema(description = "单据行ID", example = "29669")
    private Long sourceDocLineId;

    @Schema(description = "库存记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28284")
    @NotNull(message = "库存记录ID不能为空")
    private Long materialStockId;

    @Schema(description = "库存方向")
    private Integer transactionFlag;

    @Schema(description = "事务数量")
    private BigDecimal transactionQuantity;

    @Schema(description = "事务日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime transactionDate;

    @Schema(description = "关联的事务ID", example = "178")
    private Long relatedTransactionId;

    @Schema(description = "ERP账期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime erpDate;

    @Schema(description = "生产工单ID", example = "24755")
    private Long workorderId;

    @Schema(description = "生产工单编号")
    private String workorderCode;

    @Schema(description = "接收日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime recptDate;

    @Schema(description = "库存有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime expireDate;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

    @Schema(description = "入库标识")
    private String recptStatus;

    @Schema(description = "入库母批次号")
    private String parentBatchCode;

    @Schema(description = "仓库确认状态")
    private String confirmStatus;

    @Schema(description = "来源单据ID")
    private Long originId;


    public Long getId(){
        return null;
    }
}
