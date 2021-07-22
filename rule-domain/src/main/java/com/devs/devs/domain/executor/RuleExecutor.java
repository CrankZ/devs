package com.devs.devs.domain.executor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devs.devs.valueobject.RuleFieldDO;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.enums.LogicalOperatorsEnum;
import com.devs.devs.enums.RuleTypeEnum;
import com.devs.devs.dto.engine.ExecuteRuleResponse;
import com.devs.devs.jsonMapper.action.Action;
import com.devs.devs.jsonMapper.action.Condition;
import com.devs.devs.jsonMapper.simple.Rule;
import com.devs.devs.valueobject.RuleResult;
import com.devs.devs.exception.NoRuleException;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 规则执行器
 *
 * @author 松梁
 * @date 2021/7/16
 */
@SuperBuilder
public class RuleExecutor extends BaseExecutor {

    /**
     * 执行规则
     *
     * @return 所有字段的结果
     * 分支触发结果
     * 最终触发结果
     * 执行的动作
     */
    public ExecuteRuleResponse executeRule() {
        // 保存入参
        saveRecord();

        // 获取规则
        List<RuleInfoDO> ruleInfoDOList = getRuleInfoList(RuleTypeEnum.SIMPLE);
        if (ruleInfoDOList == null) {
            throw new NoRuleException();
        }

        // TODO: 这种转换不太好
        List<Rule> ruleVList = ruleInfoDOList.stream()
                .map(dto -> JSON.parseObject(dto.getRuleJson(), Rule.class))
                .collect(Collectors.toList());

        List<RuleResult> ruleResultList = executeRule(ruleVList);

        // 4.异步记录结果
        saveResult(ruleResultList);

        return ExecuteRuleResponse.builder()
                .sceneId(ruleRecordDO.getSceneId())
                .facts(JSON.parseObject(ruleRecordDO.getParam(), Map.class))
                .ruleResults(ruleResultList)
                .actions(getActions(ruleResultList))
                .build();
    }

    /**
     * 执行规则
     *
     * @param ruleVList
     * @return
     */
    private List<RuleResult> executeRule(List<Rule> ruleVList) {
        List<RuleResult> ruleResultList = new ArrayList<>();

        for (Rule ruleV : ruleVList) {
            // TODO: 对ruleV有修改
            executeRule(ruleV);

            RuleResult ruleResult = RuleResult.builder()
                    .facts(JSON.parseObject(ruleRecordDO.getParam(), Map.class))

                    .ruleV(ruleV)
                    .build();
            ruleResultList.add(ruleResult);
        }
        return ruleResultList;
    }

    private void executeRule(Rule ruleV) {
        List<Condition> and = ruleV.getConditions().getAnd();
        List<Condition> or = ruleV.getConditions().getOr();

        // 递归取出所有节点的字段
        Set<String> fieldCodeSet = getFieldCodeList(and, or);
        // 获得所有字段
        // TODO: 规则创建好之后其实就与字段没关系了，就算字段删除了也不影响执行规则
        // 如果字段被有引用，则不允许删除。必须删除正在引用它的所有的规则
        LambdaQueryWrapper<RuleFieldDO> fieldWrapper = new LambdaQueryWrapper<RuleFieldDO>()
                .eq(RuleFieldDO::getFieldCode, fieldCodeSet);

//            List<FieldDO> fieldDOList = fieldDO.selectList(fieldWrapper);
//            FieldMapper.INSTANCE.do2vList(fieldDOList);

        // 最终触发结果
        boolean ruleTrigger = executeConditions(and, or);
        ruleV.setTrigger(ruleTrigger);
        List<Action> actions;
        if (ruleTrigger) {
            actions = ruleV.getThen();
        } else {
            actions = ruleV.getOtherwise();
        }
        action(actions);
    }

    /**
     * 递归执行条件
     *
     * @param and
     * @param or
     * @return
     */
    private Boolean executeConditions(List<Condition> and, List<Condition> or) {
        LogicalOperatorsEnum logicalOperator;
        List<Condition> conditions;
        if (and == null) {
            conditions = or;
            logicalOperator = LogicalOperatorsEnum.OR;
        } else {
            conditions = and;
            logicalOperator = LogicalOperatorsEnum.AND;
        }

        // 每个condition里面还有子节点
        String exp = "";
        if (conditions == null) {
            return null;
        }
        for (int i = 0; i < conditions.size(); i++) {
            Condition condition = conditions.get(i);

            // 拼接成反射的格式
            String fieldCode = condition.getFieldCode();
            // TODO: 找下面
            if (fieldCode == null) {
                Boolean executeConditions = executeConditions(condition.getAnd(), condition.getOr());
                exp = executeConditions.toString();
                continue;
            }
            Map<String, Object> factsByParam = JSON.parseObject(ruleRecordDO.getParam(), Map.class);
            Object fact = factsByParam.get(fieldCode);
            String operator = condition.getOperator();
            Object value = condition.getValue();

            Map<String, Object> env = new HashMap<>();
            env.put("$1", fact);
            env.put("$2", value);

            boolean curTrigger = (Boolean) engine.execute(operator, env);
            condition.setTrigger(curTrigger);

            // 拼接成反射的格式
            Boolean insideBool = executeConditions(condition.getAnd(), condition.getOr());
            if (insideBool == null) {
                exp += String.format("%s", curTrigger);
            } else {
                exp += String.format("(%s)", insideBool);
            }

            if (i < conditions.size() - 1) {
                exp += String.format(" %s ", logicalOperator.getOperator());
            }
        }
        return (Boolean) engine.execute(exp);
    }

    /**
     * 递归取出所有节点的字段
     *
     * @param and
     * @param or
     * @return
     */
    private Set<String> getFieldCodeList(List<Condition> and, List<Condition> or) {
        List<Condition> conditions;
        if (and == null) {
            conditions = or;
        } else {
            conditions = and;
        }

        // 每个condition里面还有子节点
        if (conditions == null) {
            return null;
        }

        Set<String> fieldCodeList = new HashSet<>();
        for (int i = 0; i < conditions.size(); i++) {
            Condition condition = conditions.get(i);
            String fieldCode = condition.getFieldCode();
            fieldCodeList.add(fieldCode);
            Set<String> insideFieldList = getFieldCodeList(condition.getAnd(), condition.getOr());
            if (insideFieldList != null) {
                fieldCodeList.addAll(insideFieldList);
            }
        }

        return fieldCodeList;
    }

    /**
     * 执行结束后，获取action
     *
     * @param ruleResultList
     * @return
     */
    private List<Action> getActions(List<RuleResult> ruleResultList) {
        List<Action> actions = new ArrayList<>();
        for (RuleResult ruleResult : ruleResultList) {
            Rule ruleV = ruleResult.getRuleV();
            Boolean trigger = ruleV.getTrigger();
            if (trigger) {
                actions.addAll(ruleV.getThen());
            } else {
                actions.addAll(ruleV.getOtherwise());
            }
        }
        return actions;
    }

}
