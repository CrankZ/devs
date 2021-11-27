package com.devs.devs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 松梁
 * @date 2021/9/1
 */
@Getter
@AllArgsConstructor
public enum DeletedEnum {

    /**
     * 未删除
     */
    NOT_DELETED(0),

    /**
     * 已删除
     */
    DELETED(1);


    private Integer code;
}