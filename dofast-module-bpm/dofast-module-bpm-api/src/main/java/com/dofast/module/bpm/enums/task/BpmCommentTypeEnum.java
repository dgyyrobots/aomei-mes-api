package com.dofast.module.bpm.enums.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程任务 -- comment类型枚举
 */
@Getter
@AllArgsConstructor
public enum BpmCommentTypeEnum {

    APPROVE(1, "通过", ""),
    REJECT(2, "不通过", ""),
    CANCEL(3, "已取消", ""),
    BACK(4, "退回", ""),
    DELEGATE(5, "委派", ""),
    ADD_SIGN(6, "加签", "[{}]{}给了[{}]，理由为：{}"),
    SUB_SIGN(7, "减签", "[{}]操作了【减签】,审批人[{}]的任务被取消"),
    ;

    /**
     * 结果
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String desc;
    /**
     * 模板信息
     */
    private final String templateComment;

}
