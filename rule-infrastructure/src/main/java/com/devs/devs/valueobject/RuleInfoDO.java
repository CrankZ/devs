package com.devs.devs.valueobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * rule_info
 *
 * @author
 */
@Data
@Accessors(chain = true)
@TableName("rule_info")
public class RuleInfoDO extends Model<RuleInfoDO> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 规则名
     */
    private String ruleName;

    /**
     * 规则类型
     * RULE_SET:规则组
     * DECISION_TREE:决策树
     * SCORE_CAR:评分卡
     */
    private String ruleType;

    /**
     * 规则所属场景
     * 用来给规则分类
     */
    private Long sceneId;

    /**
     * 结构体
     */
    private String ruleJson;

    /**
     * 规则描述
     */
    private String ruleDesc;

}