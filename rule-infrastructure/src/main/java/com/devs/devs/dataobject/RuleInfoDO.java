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
 * 规则信息
 * </p>
 *
 * @author 松梁
 * @since 2021-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rule_info")
public class RuleInfoDO extends Model<RuleInfoDO> {


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
     * 规则名
     */
    @TableField("rule_name")
    private String ruleName;

    /**
     * 条件组
     */
    private String conditions;

    /**
     * 规则备注
     */
    @TableField("rule_desc")
    private String ruleDesc;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
