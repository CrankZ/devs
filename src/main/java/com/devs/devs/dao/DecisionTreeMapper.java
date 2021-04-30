package com.devs.devs.dao;

import com.devs.devs.dto.jsonMapper.decisionTree.DecisionTree;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DecisionTreeMapper {

  List<DecisionTree> getDecisionTree(String application, String type, String name);

  int addDecisionTree(String expression, Map<String, Object> env, String comment);
}
