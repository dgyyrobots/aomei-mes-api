package com.dofast.module.mes.api.WorkShopApi.dto;


import lombok.Data;

@Data
public class MdWorkshopDTO {

    /**
     * 车间ID
     */
    private Long id;
    /**
     * 车间编码
     */
    private String workshopCode;
    /**
     * 车间名称
     */
    private String workshopName;
    /**
     * 面积
     */
    private Object area;
    /**
     * 负责人
     */
    private String charge;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预留字段1
     */
    private String attr1;
    /**
     * 预留字段2
     */
    private String attr2;
    /**
     * 预留字段3
     */
    private Integer attr3;
    /**
     * 预留字段4
     */
    private Integer attr4;
}
