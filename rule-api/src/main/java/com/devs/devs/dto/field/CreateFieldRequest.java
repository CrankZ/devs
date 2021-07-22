package com.devs.devs.dto.field;

import com.devs.devs.dto.base.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateFieldRequest extends BaseRequest {

    /**
     * 字段编码
     */
    @NotBlank
    private String code;

    /**
     * 字段名
     */
    @NotBlank
    private String name;

    /**
     * 字段类型，1.字符串2.数字；STRING,NUMBER
     */
    @NotBlank
    private String type;

    /**
     * 字段来源1.入参 2.HTTP取数
     */
    @NotBlank
    private String source;

    /**
     * 取数路径
     */
    private String path;

    /**
     * 描述
     */
    private String desc;
}
