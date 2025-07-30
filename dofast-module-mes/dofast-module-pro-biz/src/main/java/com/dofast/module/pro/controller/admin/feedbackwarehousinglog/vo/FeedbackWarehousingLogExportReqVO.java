package com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报工入库日志 Excel 导出 Request VO，参数和 FeedbackWarehousingLogPageReqVO 是一致的")
@Data
public class FeedbackWarehousingLogExportReqVO {

    @Schema(description = "报工单编号")
    private String feedbackCode;

    @Schema(description = "工作站ID", example = "1341")
    private Long workstationId;

    @Schema(description = "工作站编号")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "李四")
    private String workstationName;

    @Schema(description = "生产工单ID", example = "13440")
    private Long workorderId;

    @Schema(description = "生产工单编号")
    private String workorderCode;

    @Schema(description = "生产工单名称", example = "芋艿")
    private String workorderName;

    @Schema(description = "工序ID", example = "16867")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "赵六")
    private String processName;

    @Schema(description = "生产任务ID", example = "8069")
    private Long taskId;

    @Schema(description = "生产任务编号")
    private String taskCode;

    @Schema(description = "产品物料ID", example = "21578")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "王五")
    private String itemName;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "本次报工数量")
    private Double quantityFeedback;

    @Schema(description = "合格品数量")
    private Double quantityQualified;

    @Schema(description = "不良品数量")
    private Double quantityUnquanlified;

    @Schema(description = "报工用户名", example = "赵六")
    private String userName;

    @Schema(description = "昵称", example = "芋艿")
    private String nickName;

    @Schema(description = "批次号")
    private String batchCode;

    @Schema(description = "ERP批次号")
    private String erpBatchCode;

    @Schema(description = "生产设备名称", example = "张三")
    private String machineryName;

    @Schema(description = "生产设备编码")
    private String machineryCode;

    @Schema(description = "生产设备ID", example = "12620")
    private Long machineryId;

    @Schema(description = "ERP报工单(用于撤销)")
    private String erpFeedback;

    @Schema(description = "ERP报工标识", example = "2")
    private String erpFeedbackStatus;

    @Schema(description = "ERP入库标识", example = "1")
    private String erpWarehousingStatus;

    @Schema(description = "转换数量")
    private BigDecimal conversionQuantity;

    @Schema(description = "转换单位")
    private String conversionUnit;

    @Schema(description = "转换不合格数量")
    private BigDecimal conversionQuantityUnquanlified;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27965")
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


    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "创建人", example = "赵六")
    private Long creator;

}
