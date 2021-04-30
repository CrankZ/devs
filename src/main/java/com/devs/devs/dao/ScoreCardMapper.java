package com.devs.devs.dao;

import com.devs.devs.dto.jsonMapper.decisionTree.DecisionTree;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreCardMapper {

  List<DecisionTree> getScoreCard(String application, String type, String name);

  int addScoreCard(String expression, Map<String, Object> env, String comment);
}
