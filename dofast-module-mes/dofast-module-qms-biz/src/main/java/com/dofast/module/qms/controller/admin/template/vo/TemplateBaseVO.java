package com.dofast.module.qms.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 检测模板 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TemplateBaseVO {

    /**
     * 物料ID
     */
    private Long itemId;
    @Schema(description = "检测模板编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检测模板编号不能为空")
    private String templateCode;

    @Schema(description = "检测模板名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "检测模板名称不能为空")
    private String templateName;

    /**
     * 用来传递检测种类参数
     */
    private String[] qcTypesParam;


    @Schema(description = "检测种类", requiredMode = Schema.RequiredMode.REQUIRED)
    private String qcTypes;

    @Schema(description = "是否启用")
    private String enableFlag;

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

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称")
    private String processName;

    public Long getId(){return null;}
}
