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
 * 规则执行结果
 * </p>
 *
 * @author 松梁
 * @since 2021-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rule_result")
public class RuleResultDO extends Model<RuleResultDO> {


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
     * 执行入参
     */
    private String request;

    /**
     * 执行出参
     */
    private String response;

    /**
     * 规则是否触发 0-未触发; 1-触发
     */
    @TableField("is_trigger")
    private Integer isTrigger;

    /**
     * 规则执行结果
     */
    private String facts;

    /**
     * 规则执行时的快照
     */
    private String snapshot;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
