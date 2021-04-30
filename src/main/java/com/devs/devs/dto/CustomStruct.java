package com.devs.devs.dto;

import lombok.Data;

@Data
public class CustomStruct {
  /**
   * 编码
   * 直接中文就行了，不需要code
   */
  @Deprecated
  private String code;
  /**
   * 名称
   */
  private String name;
  /**
   * 类型
   */
  private String clazz;
}