package com.devs.devs.consts;

/**
 * 逻辑运算符
 */
public enum LogicalOperatorsEnum {
  AND("与", "&&"), OR("或", "||");

  private String name;

  private String operator;

  LogicalOperatorsEnum(String name, String operator) {
    this.name = name;
    this.operator = operator;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }
}
