package com.dofast.module.pro.controller.admin.workorder.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 生产工单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkorderPageReqVO extends PageParam {

    @Schema(description = "工单编码")
    private String workorderCode;

    @Schema(description = "工单名称", example = "李四")
    private String workorderName;

    @Schema(description = "来源类型")
    private String orderSource;

    @Schema(description = "来源单据")
    private String sourceCode;

    @Schema(description = "产品ID", example = "21865")
    private Long productId;

    @Schema(description = "产品编号")
    private String productCode;

    @Schema(description = "产品名称", example = "李四")
    private String productName;

    @Schema(description = "规格型号")
    private String productSpc;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "生产数量")
    private Double quantity;

    @Schema(description = "已生产数量")
    private Double quantityProduced;

    @Schema(description = "调整数量")
    private Double quantityChanged;

    @Schema(description = "已排产数量")
    private Double quantityScheduled;

    @Schema(description = "客户ID", example = "19177")
    private Long clientId;

    @Schema(description = "客户编码")
    private String clientCode;

    @Schema(description = "客户名称", example = "赵六")
    private String clientName;

    @Schema(description = "批次号")
    private String batchCode;

    @Schema(description = "需求日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] requestDate;

    @Schema(description = "父工单", example = "17864")
    private Long parentId;

    @Schema(description = "所有父节点ID")
    private String ancestors;

    @Schema(description = "单据状态", example = "2")
    private String status;

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

    @Schema(description = "附件列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String adjuncts;

    @Schema(description = "是否外协", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean isOut;

    @Schema(description = "销售订单", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long mixinOrderId;

    @Schema(description = "结案标识")
    private String closeFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    // 虚拟字段
    @Schema(description = "任务单号")
    private String taskCode;


    @Schema(description = "工单列表")
    private List<String> workorderList;


}
