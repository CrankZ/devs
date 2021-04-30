package com.devs.devs.dto.jsonMapper.ruleSet;

import lombok.Data;

import java.util.List;

@Data
public class Rule {
  /**
   * 条件集
   * if
   * when
   */
  private Conditions conditions;
  /**
   * 触发成功
   * success
   * then
   */
  private List<Action> then;
  /**
   * 触发失败
   * else
   * failure
   * otherwise
   */
  private List<Action> otherwise;

  /**
   * 规则执行优先级
   * 有可能规则有上下级依赖关系
   */
  private Integer priority;

  /**
   * 执行异常
   */
  private String errorMsg;
  private String errorType;


  //这后面是执行才需要的值
  /**
   * 执行结果
   * TODO: trigger
   */
  private Boolean trigger;

}
