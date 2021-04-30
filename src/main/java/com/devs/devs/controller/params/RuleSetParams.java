package com.devs.devs.controller.params;

import lombok.Data;

@Data
public class RuleSetParams {
  private String field;
  private String operator;
  private String value;
}
