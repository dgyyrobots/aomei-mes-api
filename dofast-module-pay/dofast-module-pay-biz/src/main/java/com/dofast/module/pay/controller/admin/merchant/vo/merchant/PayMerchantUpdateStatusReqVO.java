package com.dofast.module.pay.controller.admin.merchant.vo.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 商户更新状态 Request VO")
@Data
public class PayMerchantUpdateStatusReqVO {

    @Schema(description = "商户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "商户编号不能为空")
    private Long id;

    @Schema(description = "状态,见 SysCommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
