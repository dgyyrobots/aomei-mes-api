package com.dofast.module.wms.controller.admin.allocatedheader.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 调拨单头 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AllocatedHeaderBaseVO {

    @Schema(description = "调拨单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "调拨单编号不能为空")
    private String allocatedCode;

    @Schema(description = "调拨单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    private String allocatedName;

    @Schema(description = "工作站ID", example = "2914")
    private Long workstationId;

    @Schema(description = "工作站编号")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "李四")
    private String workstationName;

    @Schema(description = "生产工单ID", example = "6339")
    private Long workorderId;

    @Schema(description = "生产工单编码")
    private String workorderCode;

    @Schema(description = "生产任务ID", example = "24663")
    private Long taskId;

    @Schema(description = "生产任务编码")
    private String taskCode;

    @Schema(description = "客户ID", example = "21386")
    private Long clientId;

    @Schema(description = "客户编码")
    private String clientCode;

    @Schema(description = "客户名称", example = "李四")
    private String clientName;

    @Schema(description = "客户简称")
    private String clientNick;

    @Schema(description = "仓库ID", example = "30770")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "赵六")
    private String warehouseName;

    @Schema(description = "库区ID", example = "10395")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "王五")
    private String locationName;

    @Schema(description = "库位ID", example = "4315")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "赵六")
    private String areaName;

    @Schema(description = "调拨日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime allocatedDate;

    @Schema(description = "单据状态", example = "2")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "生产任务名称", example = "张三")
    private String taskName;

    @Schema(description = "绑定工单标识", example = "Y")
    private String bindWorkorder;


}
