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
 * 规则触发器
 * </p>
 *
 * @author 松梁
 * @since 2021-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rule_trigger")
public class RuleTriggerDO extends Model<RuleTriggerDO> {


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
     * 规则ID
     */
    @TableField("rule_id")
    private Long ruleId;

    /**
     * 规则触发成功 执行的动作类型（HTTP，邮件，企业微信）
     */
    @TableField("success_type")
    private String successType;

    /**
     * 规则触发成功 内容
     */
    @TableField("success_value")
    private String successValue;

    /**
     * 规则触发失败 执行的动作类型（HTTP，邮件，企业微信）
     */
    @TableField("failure_type")
    private String failureType;

    /**
     * 规则触发失败 内容
     */
    @TableField("failure_value")
    private String failureValue;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
