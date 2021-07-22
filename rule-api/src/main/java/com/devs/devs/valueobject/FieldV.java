package com.devs.devs.valueobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FieldV {
    /**
     * 主键
     */
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 字段编码
     */
    private String fieldCode;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型，1.字符串2.数字；STRING,NUMBER
     */
    private String fieldType;

    /**
     * 字段来源1.入参 2.HTTP取数
     */
    private String fieldSource;

    /**
     * 取数路径
     */
    private String fieldPath;

    /**
     * 描述
     */
    private String fieldDesc;

}