package com.dofast.module.cal.controller.admin.team.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 班组 Excel VO
 *
 * @author 惠智造
 */
@Data
public class TeamExcelVO {

    @ExcelProperty("班组ID")
    private Long id;

    @ExcelProperty("班组编号")
    private String teamCode;

    @ExcelProperty("班组名称")
    private String teamName;

    @ExcelProperty("班组类型")
    private String calendarType;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("预留字段1")
    private String attr1;

    @ExcelProperty("预留字段2")
    private String attr2;

    @ExcelProperty("预留字段3")
    private Integer attr3;

    @ExcelProperty("预留字段4")
    private Integer attr4;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("负责人Id")
    private Long principalId;

    @ExcelProperty("负责人名称")
    private String principalName;

    @ExcelProperty("班组人数")
    private Long personCount;

    @ExcelProperty("生产设备名称")
    private String machineryName;

    @ExcelProperty("生产设备编码")
    private String machineryCode;

    @ExcelProperty("生产设备ID")
    private Long machineryId;

    @ExcelProperty("夜班负责人Id")
    private Long nightPrincipalId;

    @ExcelProperty("夜班负责人名称")
    private String nightPrincipalName;

}
