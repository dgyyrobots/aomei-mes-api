package com.dofast.module.cmms.controller.admin.dvmachinery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 设备台账 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class DvMachineryExcelVO {

    @ExcelProperty("设备类型ID")
    private Long machineryId;

    @ExcelProperty("设备类型编码")
    private String machineryCode;

    @ExcelProperty("设备类型名称")
    private String machineryName;

    @ExcelProperty("品牌")
    private String machineryBrand;

    @ExcelProperty("规格型号")
    private String machinerySpec;

    @ExcelProperty("设备类型ID")
    private Long machineryTypeId;

    @ExcelProperty("设备类型编码")
    private String machineryTypeCode;

    @ExcelProperty("设备类型名称")
    private String machineryTypeName;

    @ExcelProperty("所属车间ID")
    private Long workshopId;

    @ExcelProperty("所属车间编码")
    private String workshopCode;

    @ExcelProperty("所属车间名称")
    private String workshopName;

    @ExcelProperty("设备状态")
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

    @ExcelProperty("ERP设备编码")
    private String erpMachineryCode;

    @ExcelProperty("产品ID")
    private Long productId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
