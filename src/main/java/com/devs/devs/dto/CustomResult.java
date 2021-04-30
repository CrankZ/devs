package com.devs.devs.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户自定义结果结构体 or 枚举
 * 投保人
 * 直接json保存就行了
 */
@Data
public class CustomResult {
  private String id;
  /**
   * 名称
   */
  private String name;
  /**
   * 用户自定义字段
   * 投保人具体细节
   */
  private List<CustomStruct> customStructList;


}
