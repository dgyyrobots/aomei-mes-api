package com.dofast.module.wms.controller.admin.rtissueline.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 生产退料单行 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RtIssueLineBaseVO {

    @Schema(description = "退料单ID", example = "11401")
    private Long rtId;

    @Schema(description = "库存ID", example = "5442")
    private Long materialStockId;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2670")
    @NotNull(message = "产品物料ID不能为空")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "芋艿")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "退料数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退料数量不能为空")
    private BigDecimal quantityRt;

    @Schema(description = "领料批次号")
    private String batchCode;

    @Schema(description = "仓库ID", example = "15709")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "赵六")
    private String warehouseName;

    @Schema(description = "库区ID", example = "27500")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "李四")
    private String locationName;

    @Schema(description = "库位ID", example = "27910")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "李四")
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

    @Schema(description = "供应商编码", example = "S1865")
    private String vendorCode;

    @Schema(description = "ERP项次")
    private Long sequence;

    @Schema(description = "ERP项次")
    private Long sequenceOrder;

    @Schema(description = "ERP批次")
    private String erpBatchCode;

    @Schema(description = "母批次")
    private String parentBatchCode;

    @Schema(description = "ERP状态")
    private String erpStatus;

}
