package com.dofast.module.pay.controller.admin.merchant.vo.merchant;

import com.dofast.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 支付商户信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayMerchantPageReqVO extends PageParam {

    @Schema(description = "商户号")
    private String no;

    @Schema(description = "商户全称")
    private String name;

    @Schema(description = "商户简称")
    private String shortName;

    @Schema(description = "开启状态")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
