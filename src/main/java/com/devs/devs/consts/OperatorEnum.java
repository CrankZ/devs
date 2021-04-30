package com.devs.devs.consts;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
@JSONType(serializeEnumAsJavaBean = true)
public enum OperatorEnum {

  /**
   * EQ 就是 EQUAL等于
   * NE就是 NOT EQUAL不等于
   * GT 就是 GREATER THAN大于
   * LT 就是 LESS THAN小于
   * GE 就是 GREATER THAN OR EQUAL 大于等于
   * LE 就是 LESS THAN OR EQUAL 小于等于
   */
  EQ("等于", "=="),
  NE("不等于", "!="),
  GT("大于", ">"),
  LT("小于", "<"),
  GE("大于等于", ">="),
  LE("小于等于", "<="),

  IN("等于", "StringMethod.equals($1,$2)"),
  NOT_IN("不等于", "StringMethod.notEquals($1,$2)"),
  CONTAIN("包含", "StringMethod.contains($1,$2)"),
  NOT_CONTAIN("不包含", "StringMethod.notContains($1,$2)"),
  STARTS_WITH("以..开始", "StringMethod.startWith($1,$2)");

  private final String name;
  private final String method;

}
