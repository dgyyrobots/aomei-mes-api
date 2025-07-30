package com.dofast.module.cal.api.team.dto;


import lombok.Data;

@Data
public class TeamMemberDTO {

    /**
     * 班组成员ID
     */
    private Long id;
    /**
     * 班组ID
     */
    private Long teamId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 电话
     */
    private String tel;
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
     * 班次
     */
    private String shiftInfo;

}
