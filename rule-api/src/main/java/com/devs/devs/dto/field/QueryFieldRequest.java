package com.devs.devs.dto.field;

import com.devs.devs.dto.base.BaseRequest;
import com.devs.devs.enums.FieldEnum;
import com.devs.devs.enums.FieldSourceEnum;
import lombok.Data;
import net.sf.oval.constraint.Range;

import javax.validation.constraints.Size;

@Data
public class QueryFieldRequest extends BaseRequest {

    /**
     * 字段编码
     */
    private String code;

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段类型，1.字符串2.数字；STRING,NUMBER
     *
     * @see FieldEnum
     */
    private String type;

    /**
     * 字段来源1.入参 2.HTTP取数
     *
     * @see FieldSourceEnum
     */
    private String source;

    /**
     * 取数路径
     */
    private String path;

    /**
     * 描述
     */
    private String desc;

    /**
     * 分页大小
     */
    @Range(min = 1, max = 100)
    private Integer pageSize = 10;

    /**
     * 当前页
     */
    @Size(min = 1)
    private Integer pageNo = 1;
}
