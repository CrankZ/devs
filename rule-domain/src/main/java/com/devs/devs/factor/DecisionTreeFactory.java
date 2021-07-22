package com.devs.devs.factor;

import com.alibaba.fastjson.JSON;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.domain.DecisionTree;
import com.devs.devs.enums.RuleTypeEnum;
import com.devs.devs.dto.tree.CreateTreeRequest;
import com.devs.devs.dto.tree.QueryTreeRequest;
import com.devs.devs.dto.tree.UpdateStatusTreeRequest;
import com.devs.devs.dto.tree.UpdateTreeRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 松梁
 * @date 2021/6/27
 */
@Component
public class DecisionTreeFactory {

    public DecisionTree create(CreateTreeRequest request) {
        RuleInfoDO ruleInfoDO = new RuleInfoDO()
                .setSceneId(request.getSceneId())
                .setRuleName(request.getTreeName())
                .setRuleType(RuleTypeEnum.DECISION_TREE.name())
                .setRuleJson(JSON.toJSONString(request))
                .setRuleDesc(request.getDesc())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());

        return DecisionTree.builder()
                .ruleInfoDO(ruleInfoDO)
                .build();
    }

    public DecisionTree create(UpdateTreeRequest request) {
        return DecisionTree.builder()
                .build();
    }

    public DecisionTree create(UpdateStatusTreeRequest request) {
        return DecisionTree.builder()
                .build();
    }

    public DecisionTree create(QueryTreeRequest request) {
        RuleInfoDO ruleInfoDO = new RuleInfoDO();
        return DecisionTree.builder()
                .ruleInfoDO(ruleInfoDO)
                .build();
    }

}
