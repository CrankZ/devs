package com.devs.devs.dto.jsonMapper.decisionTree;

import lombok.Data;

@Data
public class DecisionTree {
  private String application;
  private String name;
  private Node root;

  private Boolean trigger;
}
