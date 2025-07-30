package com.dofast.module.pro.controller.admin.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.poi.hpsf.Decimal;

/**
 * 生产报工记录 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class FeedbackExcelVO {

    @ExcelProperty("记录ID")
    private Long id;

    @ExcelProperty("报工类型")
    private String feedbackType;

    @ExcelProperty("报工单编号")
    private String feedbackCode;

    @ExcelProperty("工作站ID")
    private Long workstationId;

    @ExcelProperty("工作站编号")
    private String workstationCode;

    @ExcelProperty("工作站名称")
    private String workstationName;

    @ExcelProperty("生产工单ID")
    private Long workorderId;

    @ExcelProperty("生产工单编号")
    private String workorderCode;

    @ExcelProperty("生产工单名称")
    private String workorderName;

    @ExcelProperty("工序ID")
    private Long processId;

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("生产任务ID")
    private Long taskId;

    @ExcelProperty("生产任务编号")
    private String taskCode;

    @ExcelProperty("产品物料ID")
    private Long itemId;

    @ExcelProperty("产品物料编码")
    private String itemCode;

    @ExcelProperty("产品物料名称")
    private String itemName;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("排产数量")
    private Double quantity;

    @ExcelProperty("本次报工数量")
    private Double quantityFeedback;

    @ExcelProperty("合格品数量")
    private Double quantityQualified;

    @ExcelProperty("不良品数量")
    private Double quantityUnquanlified;

    @ExcelProperty("报工用户名")
    private String userName;

    @ExcelProperty("昵称")
    private String nickName;

    @ExcelProperty("报工途径")
    private String feedbackChannel;

    @ExcelProperty("报工时间")
    private LocalDateTime feedbackTime;

    @ExcelProperty("记录人")
    private String recordUser;

    @ExcelProperty("记录人名称")
    private String recordNick;

    @ExcelProperty("状态")
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

    @ExcelProperty("报工价格")
    private Double reportFee;

    @ExcelProperty("实付价格")
    private Double payFee;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("批次号")
    private String batchCode;

    @ExcelProperty("ERP批次号")
    private String erpBatchCode;

    @ExcelProperty("班组编码")
    private String teamCode;

    @ExcelProperty("缺陷Id")
    private String defectId;

    @ExcelProperty("班组负责人")
    private String principalName;

    @ExcelProperty("班组负责人Id")
    private Long principalId;

    @ExcelProperty("班组类型")
    private String shiftInfo;

    @ExcelProperty("来源编码")
    private String originCode;

    @ExcelProperty("设备Id")
    private Long machineryId;

    @ExcelProperty("设备名称")
    private String machineryName;

    @ExcelProperty("设备编码")
    private String machineryCode;

    @ExcelProperty("ERP报工单")
    private String erpFeedback;

    @ExcelProperty("任务单状态")
    private String taskStatus;

    @ExcelProperty("ERP报工状态")
    private String erpFeedbackStatus;

    @ExcelProperty("ERP入库状态")
    private String erpWarehousingStatus;

    @ExcelProperty("合并需求")
    private String mergeStatus;

    @ExcelProperty("转换数量")
    private BigDecimal conversionQuantity;

    @ExcelProperty("转换单位")
    private String conversionUnit;

    @ExcelProperty("转换不合格数量")
    private BigDecimal conversionQuantityUnquanlified;

    @ExcelProperty("卷数编号")
    private String volumesNumber;

    @ExcelProperty("数采标识")
    private String iotFlag;
}
