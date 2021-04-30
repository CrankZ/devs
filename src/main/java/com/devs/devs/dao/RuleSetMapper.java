package com.devs.devs.dao;

import com.devs.devs.dto.jsonMapper.ruleSet.RuleSet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RuleSetMapper {

  /**
   * 规则集类型
   * TODO: 规则该如何分类？
   *
   * @param type
   */
  List<RuleSet> getRuleSet(String model, String type, String name);

  int addRuleSet(String expression, Map<String, Object> env, String comment);

}
