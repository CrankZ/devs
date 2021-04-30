package com.devs.devs.dto.jsonMapper.ruleSet;

import lombok.Data;

import java.util.Map;

@Data
public class ActionHttp {
  private String url;
  private String method;
  private Map<String, Object> params;
}
