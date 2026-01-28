package com.dofast.module.mes.controller.admin.mditemcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 物料类别 Excel VO
 *
 * @author 惠智造
 */
@Data
public class MdItemCategoryExcelVO {

    @ExcelProperty("ID")
    private Integer id;

    @ExcelProperty("物料类别名称")
    private String itemCategoryName;

    @ExcelProperty("物料类别编码")
    private String itemCategoryCode;

    @ExcelProperty("物料工艺")
    private String itemRoute;

    @ExcelProperty("物料工艺编码")
    private String itemRouteCode;

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

}
