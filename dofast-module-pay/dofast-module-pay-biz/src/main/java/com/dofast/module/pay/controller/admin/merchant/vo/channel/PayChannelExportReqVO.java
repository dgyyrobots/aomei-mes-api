package com.dofast.module.pay.controller.admin.merchant.vo.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 支付渠道 Excel 导出 Request VO,参数和 PayChannelPageReqVO 是一致的")
@Data
public class PayChannelExportReqVO {

    @Schema(description = "渠道编码")
    private String code;

    @Schema(description = "开启状态")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "渠道费率，单位：百分比")
    private Double feeRate;

    @Schema(description = "商户编号")
    private Long merchantId;

    @Schema(description = "应用编号")
    private Long appId;

    @Schema(description = "支付渠道配置")
    private String config;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
