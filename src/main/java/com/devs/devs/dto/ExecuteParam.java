package com.devs.devs.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ExecuteParam {
  /**
   * 请求id
   */
  private String requestId;
  /**
   * 场景
   * TODO: 改名为模型
   */
  private String model;
  /**
   * 请求人
   * 调用方
   */
  private String userId;
  /**
   * 字段值
   */
  private Map<String, Object> facts;

  /**
   * 额外的参数
   * TODO: 通过http取数的参数，这是通过接口调用传进来，所以也需要一个map来保存参数
   */
  @Deprecated
  private Map<String, Object> extendParams;
}
