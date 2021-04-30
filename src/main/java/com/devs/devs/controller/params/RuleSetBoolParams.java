package com.devs.devs.controller.params;

import com.devs.devs.consts.LogicalOperatorsEnum;
import com.devs.devs.dto.jsonMapper.ruleSet.Condition;
import lombok.Data;

import java.util.List;

/**
 * 添加规则集（布尔类型）的入参
 */
@Data
public class RuleSetBoolParams {
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
}
