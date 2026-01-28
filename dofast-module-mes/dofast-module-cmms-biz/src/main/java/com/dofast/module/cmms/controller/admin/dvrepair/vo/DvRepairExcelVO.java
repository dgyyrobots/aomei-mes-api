package com.dofast.module.cmms.controller.admin.dvrepair.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 设备维修单 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class DvRepairExcelVO {

    @ExcelProperty("维修单ID")
    private Long id;

    @ExcelProperty("维修单编号")
    private String repairCode;

    @ExcelProperty("维修单名称")
    private String repairName;

    @ExcelProperty("设备ID")
    private Long machineryId;

    @ExcelProperty("设备编码")
    private String machineryCode;

    @ExcelProperty("设备名称")
    private String machineryName;

    @ExcelProperty("品牌")
    private String machineryBrand;

    @ExcelProperty("规格型号")
    private String machinerySpec;

    @ExcelProperty("设备类型ID")
    private Long machineryTypeId;

    @ExcelProperty("报修日期")
    private LocalDateTime requireDate;

    @ExcelProperty("维修完成日期")
    private LocalDateTime finishDate;

    @ExcelProperty("验收日期")
    private LocalDateTime confirmDate;

    @ExcelProperty("维修结果")
    private String repairResult;

    @ExcelProperty("维修人员")
    private String acceptedBy;

    @ExcelProperty("验收人员")
    private String confirmBy;

    @ExcelProperty("单据状态")
    private String status;

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

    @ExcelProperty("维修类型")
    private String repairType;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
