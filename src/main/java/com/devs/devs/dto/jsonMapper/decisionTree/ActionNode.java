package com.devs.devs.dto.jsonMapper.decisionTree;

import com.devs.devs.dto.jsonMapper.ruleSet.Action;
import lombok.Data;

import java.util.List;

/**
 * 叶子结点
 * 功能是执行action
 * 不直接叫actionNode是为了避免让用户认为这个在其他地方也可以配置
 * 还是叫actionNode吧，如果叫leafNode就相当于引入新的概念了，没必要
 * external node (also known as an outer node, leaf node, or terminal node)
 */
@Data
public class ActionNode extends AbstractNode {
  private List<Action> actions;
  private Boolean trigger;
}
