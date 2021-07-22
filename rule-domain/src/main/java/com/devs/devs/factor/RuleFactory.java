package com.devs.devs.factor;

import com.alibaba.fastjson.JSON;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.domain.Rule;
import com.devs.devs.enums.RuleTypeEnum;
import com.devs.devs.dto.rule.CreateRuleRequest;
import com.devs.devs.dto.rule.QueryRuleRequest;
import com.devs.devs.dto.rule.UpdateRuleRequest;
import com.devs.devs.dto.rule.UpdateStatusRuleRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 松梁
 * @date 2021/6/27
 */
@Component
public class RuleFactory {

    public Rule create(CreateRuleRequest request) {
        RuleInfoDO ruleInfoDO = new RuleInfoDO()
                .setSceneId(request.getSceneId())
                .setRuleName(request.getRuleName())
                .setRuleType(RuleTypeEnum.SIMPLE.name())
                .setRuleJson(JSON.toJSONString(request))
                .setRuleDesc(request.getRuleDesc())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());

        return Rule.builder()
                .ruleInfoDO(ruleInfoDO)
                .build();
    }

    public Rule create(UpdateRuleRequest request) {
        return Rule.builder()
                .build();
    }

    public Rule create(UpdateStatusRuleRequest request) {
        return Rule.builder()
                .build();
    }

    public Rule create(QueryRuleRequest request) {
        RuleInfoDO ruleInfoDO = new RuleInfoDO();
        return Rule.builder()
                .ruleInfoDO(ruleInfoDO)
                .build();
    }

}
