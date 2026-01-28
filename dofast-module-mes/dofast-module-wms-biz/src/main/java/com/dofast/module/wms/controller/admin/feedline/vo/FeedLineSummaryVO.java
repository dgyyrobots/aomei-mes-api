package com.dofast.module.wms.controller.admin.feedline.vo;

import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FeedLineSummaryVO extends FeedLineBaseVO {

    //组合id
    private String id;

    private String workorderCode;

    private String workorderName;

    private String taskCode;

    private Double planQuantity;

    private Double usagePercentage;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private BigDecimal actualQuantity;
}
