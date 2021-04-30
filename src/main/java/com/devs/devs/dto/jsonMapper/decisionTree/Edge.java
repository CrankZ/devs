package com.devs.devs.dto.jsonMapper.decisionTree;

import lombok.Data;

@Data
public class Edge {
  private String operator;
  private Object value;
  private Node node;

  private Boolean trigger;
}
