package com.devs.devs.dto.vo;

import lombok.Data;

import java.util.List;

@Data
public class RuleSetResult {
  private String code;
  private String name;

  private List<RuleResult> ruleResults;
}
