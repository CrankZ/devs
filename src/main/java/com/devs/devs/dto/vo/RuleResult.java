package com.devs.devs.dto.vo;

import com.devs.devs.consts.FieldSourceEnum;
import com.devs.devs.dto.jsonMapper.ruleSet.Rule;
import lombok.Data;

import java.util.Map;

/**
 *
 */
@Data
public class RuleResult {
  private Map<FieldSourceEnum, Map<String, Object>> facts;
  private Rule rule;
}
