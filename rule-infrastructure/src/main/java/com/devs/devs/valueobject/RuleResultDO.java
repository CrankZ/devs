package com.devs.devs.valueobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * rule_result
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
@Accessors(chain = true)
@TableName("rule_result")
public class RuleResultDO extends Model<RuleResultDO> {
    /**
     * 主键
     */
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 场景ID
     */
    private Long sceneId;

    /**
     * 执行结果类型分开保存，规则类型，1.规则组2.规则集3.决策树4.评分卡
     */
    private String ruleType;

    /**
     * 当时场景下所有规则的结构体，包括执行结果
     */
    private String ruleResult;

}