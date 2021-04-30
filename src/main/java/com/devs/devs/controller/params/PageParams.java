package com.devs.devs.controller.params;

import lombok.Data;

import java.util.Map;

@Data
public class PageParams {
  private Integer current;
  private Integer pageSize;
  private String updatedAt;

  private Map<String, Object> sorter;
  private Map<String, Object> filter;
}
