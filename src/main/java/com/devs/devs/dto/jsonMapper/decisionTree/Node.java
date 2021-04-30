package com.devs.devs.dto.jsonMapper.decisionTree;

import com.devs.devs.consts.NodeEnum;
import com.devs.devs.dto.jsonMapper.ruleSet.Action;
import lombok.Data;

import java.util.List;

/**
 * 节点
 * 主要是字段的组合
 */
@Data
public class Node extends AbstractNode {
  /**
   * 多个字段组合可能会有新的含义
   * 但是如果是用原型的，表达式可能就放不下可能就需要起别名
   * TODO: 但是直线显示别名可能会让用户认为是一个字段，如果做区分？
   */
  private String name;
  /**
   * 支持多个字段组合
   */
  private String fields;
  private List<Edge> edges;


  private NodeEnum type;
  private List<Action> actions;
  private Boolean trigger;

}
