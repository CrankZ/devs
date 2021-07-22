package com.devs.devs.valueobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 严格与数据库对应
 */
@Data
@Accessors(chain = true)
@TableName("rule_field")
public class RuleFieldDO extends Model<RuleFieldDO> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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