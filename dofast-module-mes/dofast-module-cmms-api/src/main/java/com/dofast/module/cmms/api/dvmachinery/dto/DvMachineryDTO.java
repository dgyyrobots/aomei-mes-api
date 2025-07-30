package com.dofast.module.cmms.api.dvmachinery.dto;
import lombok.Data;

@Data
public class DvMachineryDTO {
    /**
     * 设备类型ID
     */
    private Long id;
    /**
     * 设备类型编码
     */
    private String machineryCode;
    /**
     * 设备类型名称
     */
    private String machineryName;
    /**
     * 品牌
     */
    private String machineryBrand;
    /**
     * 规格型号
     */
    private String machinerySpec;
    /**
     * 设备类型ID
     */
    private Long machineryTypeId;
    /**
     * 设备类型编码
     */
    private String machineryTypeCode;
    /**
     * 设备类型名称
     */
    private String machineryTypeName;
    /**
     * 所属车间ID
     */
    private Long workshopId;
    /**
     * 所属车间编码
     */
    private String workshopCode;
    /**
     * 所属车间名称
     */
    private String workshopName;
    /**
     * 设备状态
     */
    private String status;
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

    /**
     * ERP设备编码
     */
    private String erpMachineryCode;

    /**
     * 产品ID
     */
    private Long productId;

}
