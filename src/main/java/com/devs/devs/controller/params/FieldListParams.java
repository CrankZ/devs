package com.devs.devs.controller.params;

import lombok.Data;

import java.util.Date;

@Data
public class FieldListParams extends PageParams {
  private String code;
  private String name;
  private String type;
  private String source;
  private String status;
  private Date beginTime;
  private Date endTime;
}
