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
 * 模板表
 * </p>
 *
 * @author 松梁
 * @since 2021-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rule_template")
public class RuleTemplateDO extends Model<RuleTemplateDO> {


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
     * 字段名
     */
    @TableField("template_name")
    private String templateName;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
