package com.devs.devs.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 基础字段
 * </p>
 *
 * @author 松梁
 * @since 2021-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rule_field")
public class RuleFieldDO extends Model<RuleFieldDO> {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime mtime;

    /**
     * 是否删除 0-未删除; 1-已删除
     */
    private Integer deleted;

    /**
     * 字段编码
     */
    @TableField("field_code")
    private String fieldCode;

    /**
     * 字段名
     */
    @TableField("field_name")
    private String fieldName;

    /**
     * 字段类型 字符串STRING,NUMBER数字
     */
    @TableField("field_type")
    private String fieldType;

    /**
     * 字段来源 入参PARAM；接口HTTP；反射REFLECT
     */
    @TableField("field_source_type")
    private String fieldSourceType;

    /**
     * 取数地址
     */
    @TableField("field_source")
    private String fieldSource;

    /**
     * 描述
     */
    @TableField("field_desc")
    private String fieldDesc;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
