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
 * 规则条件
 * </p>
 *
 * @author 松梁
 * @since 2021-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rule_condition")
public class RuleConditionDO extends Model<RuleConditionDO> {


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
     * 条件名称
     */
    @TableField("condition_name")
    private String conditionName;

    /**
     * 条件编码
     */
    @TableField("condition_code")
    private String conditionCode;

    /**
     * 字段编码
     */
    @TableField("field_code")
    private String fieldCode;

    /**
     * 比较符号
     */
    private String operator;

    /**
     * 期望值
     */
    private String expect;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
