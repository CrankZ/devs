package com.devs.devs.controller.params;

import lombok.Data;

import java.util.Date;

@Data
public class RuleSetListParams extends PageParams {
  private String code;
  private String name;
  private String type;
  private String modelId;

  private String status;
  private Date beginTime;
  private Date endTime;
}
