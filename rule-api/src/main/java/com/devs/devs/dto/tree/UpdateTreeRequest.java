package com.devs.devs.dto.tree;

import com.devs.devs.enums.LogicalOperatorsEnum;
import com.devs.devs.dto.base.BaseRequest;
import com.devs.devs.jsonMapper.action.Condition;
import lombok.Data;

import java.util.List;

@Data
public class UpdateTreeRequest extends BaseRequest {

    /**
     * 场景id
     */
    private Long sceneId;

    /**
     * 规则编码
     */
    private String ruleSetCode;

    /**
     * 规则集名称
     */
    private String ruleSetName;

    /**
     * 逻辑操作符，用来组合conditions的关系
     * 与、或
     *
     * @see LogicalOperatorsEnum
     */
    private String logicalOperator;

    /**
     * 条件组
     */
    private List<Condition> conditions;
    /**
     * 成功的结果
     */
    private String then;
    /**
     * 失败的结果
     */
    private String otherwise;

    /**
     * 描述
     */
    private String desc;

}
