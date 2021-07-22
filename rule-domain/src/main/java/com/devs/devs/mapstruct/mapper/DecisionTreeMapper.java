package com.devs.devs.mapstruct.mapper;

import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.jsonMapper.decisionTree.DecisionTree;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DecisionTreeMapper {
    DecisionTreeMapper INSTANCE = Mappers.getMapper(DecisionTreeMapper.class);

    DecisionTree do2v(RuleInfoDO ruleInfoDO);

    List<DecisionTree> do2vList(List<RuleInfoDO> doList);
}
