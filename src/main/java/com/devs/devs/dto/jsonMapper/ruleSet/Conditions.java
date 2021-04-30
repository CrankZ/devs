package com.devs.devs.dto.jsonMapper.ruleSet;

import lombok.Data;

import java.util.List;

@Data
public class Conditions {
  private List<Condition> and;
  private List<Condition> or;
}
