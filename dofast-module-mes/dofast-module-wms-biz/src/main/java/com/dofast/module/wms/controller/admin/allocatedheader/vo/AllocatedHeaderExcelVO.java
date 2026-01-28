package com.dofast.module.wms.controller.admin.allocatedheader.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 调拨单头 Excel VO
 *
 * @author 惠智造
 */
@Data
public class AllocatedHeaderExcelVO {

    @ExcelProperty("调拨单ID")
    private Long id;

    @ExcelProperty("调拨单编号")
    private String allocatedCode;

    @ExcelProperty("调拨单名称")
    private String allocatedName;

    @ExcelProperty("工作站ID")
    private Long workstationId;

    @ExcelProperty("工作站编号")
    private String workstationCode;

    @ExcelProperty("工作站名称")
    private String workstationName;

    @ExcelProperty("生产工单ID")
    private Long workorderId;

    @ExcelProperty("生产工单编码")
    private String workorderCode;

    @ExcelProperty("生产任务ID")
    private Long taskId;

    @ExcelProperty("生产任务编码")
    private String taskCode;

    @ExcelProperty("客户ID")
    private Long clientId;

    @ExcelProperty("客户编码")
    private String clientCode;

    @ExcelProperty("客户名称")
    private String clientName;

    @ExcelProperty("客户简称")
    private String clientNick;

    @ExcelProperty("仓库ID")
    private Long warehouseId;

    @ExcelProperty("仓库编码")
    private String warehouseCode;

    @ExcelProperty("仓库名称")
    private String warehouseName;

    @ExcelProperty("库区ID")
    private Long locationId;

    @ExcelProperty("库区编码")
    private String locationCode;

    @ExcelProperty("库区名称")
    private String locationName;

    @ExcelProperty("库位ID")
    private Long areaId;

    @ExcelProperty("库位编码")
    private String areaCode;

    @ExcelProperty("库位名称")
    private String areaName;

    @ExcelProperty("调拨日期")
    private LocalDateTime allocatedDate;

    @ExcelProperty("单据状态")
    private String status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("生产任务名称")
    private String taskName;

    @ExcelProperty("绑定工单标识")
    private String bindWorkorder;

    @ExcelProperty("完成者")
    private String completer;


}
