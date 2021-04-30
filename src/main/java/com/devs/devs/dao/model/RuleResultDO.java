package com.devs.devs.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * rule_result
 * @author 
 */
@Data
public class RuleResultDO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    private Date createTime;

    private Date updateTime;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 模型ID
     */
    private String modelId;

    /**
     * 执行结果类型分开保存，规则类型，1.规则组2.规则集3.决策树4.评分卡
     */
    private String ruleType;

    /**
     * 当时模型下所有规则的结构体，包括执行结果
     */
    private Object ruleResult;

    private static final long serialVersionUID = 1L;
}