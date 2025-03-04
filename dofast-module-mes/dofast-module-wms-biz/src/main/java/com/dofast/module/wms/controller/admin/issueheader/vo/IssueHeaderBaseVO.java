package com.dofast.module.wms.controller.admin.issueheader.vo;

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
 * 生产领料单头 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class IssueHeaderBaseVO {

    @Schema(description = "领料单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "领料单编号不能为空")
    private String issueCode;

    @Schema(description = "领料单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    private String issueName;

    @Schema(description = "工作站ID", example = "27847")
    private Long workstationId;

    @Schema(description = "工作站编号")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "李四")
    private String workstationName;

    @Schema(description = "生产工单ID", example = "30105")
    private Long workorderId;

    @Schema(description = "生产工单编码")
    private String workorderCode;

    @Schema(description = "生产任务ID", example = "24103")
    private Long taskId;

    @Schema(description = "生产任务编码")
    private String taskCode;

    @Schema(description = "客户ID", example = "27172")
    private Long clientId;

    @Schema(description = "客户编码")
    private String clientCode;

    @Schema(description = "客户名称", example = "张三")
    private String clientName;

    @Schema(description = "客户简称")
    private String clientNick;

    @Schema(description = "仓库ID", example = "27496")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "张三")
    private String warehouseName;

    @Schema(description = "库区ID", example = "24323")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "张三")
    private String locationName;

    @Schema(description = "库位ID", example = "3839")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "芋艿")
    private String areaName;

    @Schema(description = "领料日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime issueDate;

    @Schema(description = "单据状态", example = "1")
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

    @Schema(description = "设备名称")
    private String machineryName;

    @Schema(description = "设备编码")
    private String machineryCode;

    @Schema(description = "设备ID")
    private Long machineryId;

    @Schema(description = "工序编号")
    private String processCode;

    @Schema(description = "工序名称")
    private String processName;
    public Long getId(){
        return null;
    }
}
