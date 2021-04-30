package com.devs.devs.dto.jsonMapper.ruleSet;

import lombok.Data;

import java.util.List;

/**
 * 规则集
 */
@Data
public class RuleSet {
  private String name;
  private String modelId;
  private String desc;
  private List<Rule> rules;
}
