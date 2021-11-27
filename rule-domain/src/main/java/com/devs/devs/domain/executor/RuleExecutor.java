package com.devs.devs.domain.executor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devs.devs.dataobject.RuleConditionDO;
import com.devs.devs.dataobject.RuleFieldDO;
import com.devs.devs.dataobject.RuleInfoDO;
import com.devs.devs.dataobject.RuleTemplateMapDO;
import com.devs.devs.dto.engine.RuleEngineExecuteResponse;
import com.devs.devs.dto.engine.RuleResultV;
import com.devs.devs.enums.FieldSourceTypeEnum;
import com.devs.devs.enums.ValueTypeEnum;
import com.devs.devs.util.SpringRestTemplate;
import com.devs.devs.utils.CommonUtil;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 规则引擎执行
 *
 * @author 松梁
 * @date 2021/11/25
 */
@Builder
public class RuleExecutor {
    /**
     * 模板主键id
     */
    private Long templateId;
    /**
     * 入参（通过接口传入）
     */
    private Map<String, Object> params;

    private ApplicationContext applicationContext;

    private SpringRestTemplate springRestTemplate;

    private AviatorEvaluatorInstance engine;

    /**
     * 执行规则
     *
     * @return
     */
    public RuleEngineExecuteResponse executeRule() {
        // 模板
        List<RuleTemplateMapDO> mapList = getRuleTemplateMapList(templateId);
        // 规则
        List<RuleInfoDO> ruleInfoList = getRuleInfoList(mapList);
        // 条件
        List<RuleConditionDO> conditionList = getConditionList(ruleInfoList);
        // 字段
        List<RuleFieldDO> fieldList = getFieldList(conditionList);
        // 实际取值
        Map<String, Object> facts = getFacts(fieldList, params);

        // 执行规则并获取执行结果
        List<RuleResultV> ruleResultList = executeRuleList(ruleInfoList, facts);

        return RuleEngineExecuteResponse.builder()
                .templateId(templateId)
                .params(params)
                .ruleResult(ruleResultList)
                .build();
    }

    /**
     * 根据规则模板ID取映射表
     *
     * @param templateId
     * @return
     */
    private List<RuleTemplateMapDO> getRuleTemplateMapList(Long templateId) {
        LambdaQueryWrapper<RuleTemplateMapDO> wrapper = new LambdaQueryWrapper<RuleTemplateMapDO>()
                .eq(RuleTemplateMapDO::getTemplateId, templateId);
        RuleTemplateMapDO ruleTemplateMapDO = new RuleTemplateMapDO();
        return ruleTemplateMapDO.selectList(wrapper);
    }

    /**
     * 根据映射表取规则
     *
     * @param mapList
     * @return
     */
    private List<RuleInfoDO> getRuleInfoList(List<RuleTemplateMapDO> mapList) {
        List<Long> ruleIdList = mapList.stream().map(RuleTemplateMapDO::getRuleId).collect(Collectors.toList());
        RuleInfoDO ruleInfoDO = new RuleInfoDO();
        LambdaQueryWrapper<RuleInfoDO> wrapper = new LambdaQueryWrapper<RuleInfoDO>()
                .in(RuleInfoDO::getId, ruleIdList);
        return ruleInfoDO.selectList(wrapper);
    }

    /**
     * 根据规则取条件
     *
     * @param ruleInfoList
     * @return
     */
    private List<RuleConditionDO> getConditionList(List<RuleInfoDO> ruleInfoList) {
        List<String> allConditionList = ruleInfoList.stream().map(r -> r.getConditions()).collect(Collectors.toList());
        String conditions = CommonUtil.list2Str(allConditionList);
        if (StringUtils.isBlank(conditions)) {
            return Collections.EMPTY_LIST;
        }

        List<String> conditionCodeList = getConditionCodeList(conditions);

        LambdaQueryWrapper<RuleConditionDO> wrapper = new LambdaQueryWrapper<RuleConditionDO>()
                .in(RuleConditionDO::getConditionCode, conditionCodeList);
        RuleConditionDO ruleConditionDO = new RuleConditionDO();
        List<RuleConditionDO> conditionList = ruleConditionDO.selectList(wrapper);
        return conditionList;
    }

    /**
     * 根据条件取字段
     *
     * @param conditionList
     * @return
     */
    private List<RuleFieldDO> getFieldList(List<RuleConditionDO> conditionList) {
        Set<String> fieldCodeSet = conditionList.stream().map(c -> c.getFieldCode()).collect(Collectors.toSet());
        LambdaQueryWrapper<RuleFieldDO> wrapper = new LambdaQueryWrapper<RuleFieldDO>()
                .in(RuleFieldDO::getFieldCode, fieldCodeSet);
        RuleFieldDO ruleFieldDO = new RuleFieldDO();
        return ruleFieldDO.selectList(wrapper);
    }

    /**
     * 获取所有的实际取值
     * 相同数据源只取一次
     *
     * @param fieldList
     * @param params    入参（通过浏览器传过来）
     * @return
     */
    private Map<String, Object> getFacts(List<RuleFieldDO> fieldList, Map<String, Object> params) {
        Map<String, Map<String, Object>> pathMap = getPathFactMap(fieldList, params);

        // key: 字段名 value: 真实取值
        Map<String, Object> facts = new HashMap<>();
        for (RuleFieldDO ruleFieldDO : fieldList) {
            String fieldCode = ruleFieldDO.getFieldCode();
            String fieldSourceType = ruleFieldDO.getFieldSourceType();
            String fieldSource = ruleFieldDO.getFieldSource();

            if (FieldSourceTypeEnum.REFLECT.name().equals(fieldSourceType)) {
                Map<String, Object> reflectMap = pathMap.get(fieldSource);
                Object fact = reflectMap.get(fieldCode);
                facts.put(fieldCode, fact);
            }
        }
        return facts;
    }

    /**
     * 获取当前规则下的字段的实际取值
     *
     * @param ruleInfo 字段
     * @param facts    实际值（key:fieldCode,value:fact）
     * @return
     */
    private Map<String, Object> getFacts(RuleInfoDO ruleInfo, Map<String, Object> facts) {
        List<RuleConditionDO> conditionList = getConditionList(Collections.singletonList(ruleInfo));
        List<RuleFieldDO> fieldList = getFieldList(conditionList);
        Map<String, Object> myFacts = new HashMap<>();
        for (RuleFieldDO field : fieldList) {
            String fieldName = field.getFieldName();
            if (myFacts.get(fieldName) == null) {
                myFacts.put(fieldName, facts.get(field.getFieldCode()));
            }
        }
        return myFacts;
    }

    /**
     * 获取条件中文名
     *
     * @param ruleInfo
     * @return
     */
    private String getConditionsName(RuleInfoDO ruleInfo) {
        String conditions = ruleInfo.getConditions();
        List<RuleConditionDO> conditionList = getConditionList(Collections.singletonList(ruleInfo));
        Map<String, String> conditionMap = conditionList.stream().collect(Collectors.toMap(RuleConditionDO::getConditionCode, RuleConditionDO::getConditionName));
        for (Map.Entry<String, String> entry : conditionMap.entrySet()) {
            conditions = conditions.replace(entry.getKey(), entry.getValue());
        }
        return conditions;
    }

    /**
     * 批量执行规则
     *
     * @param ruleInfoList
     * @param facts
     * @return
     */
    private List<RuleResultV> executeRuleList(List<RuleInfoDO> ruleInfoList, Map<String, Object> facts) {
        List<RuleResultV> ruleResultList = new ArrayList<>();
        for (RuleInfoDO ruleInfo : ruleInfoList) {
            Boolean ruleTrigger = executeSingleRule(ruleInfo, facts);
            Map<String, Object> myFacts = getFacts(ruleInfo, facts);
            String conditionsName = getConditionsName(ruleInfo);
            RuleResultV form = RuleResultV.builder()
                    .ruleName(ruleInfo.getRuleName())
                    .trigger(ruleTrigger)
                    .conditions(conditionsName)
                    .facts(myFacts)
                    .build();
            ruleResultList.add(form);
        }
        return ruleResultList;
    }

    /**
     * 执行单个规则
     *
     * @param ruleInfo 规则
     * @param facts    实际值
     * @return
     */
    public Boolean executeSingleRule(RuleInfoDO ruleInfo, Map<String, Object> facts) {
        String conditions = ruleInfo.getConditions();
        List<RuleConditionDO> conditionList = getConditionList(Collections.singletonList(ruleInfo));

        // 所有条件执行的结果
        // key是conditionCode, value是条件执行结果
        Map<String, Object> conditionTriggerMap = executeConditions(conditionList, facts);

        Object o = engine.execute(conditions, conditionTriggerMap);
        Boolean ruleTrigger = o == null ? null : (Boolean) o;
        return ruleTrigger;
    }

    /**
     * 执行条件
     *
     * @param conditionList 条件
     * @param facts         实际取值（key:fieldCode,value:fact）
     * @return key:conditionCode,value:conditionTrigger
     */
    private Map<String, Object> executeConditions(List<RuleConditionDO> conditionList, Map<String, Object> facts) {
        Map<String, Object> conditionTriggerMap = new HashMap<>();
        for (RuleConditionDO condition : conditionList) {
            String conditionCode = condition.getConditionCode();
            String fieldCode = condition.getFieldCode();
            String operator = condition.getOperator();
            String expect = condition.getExpect();

            Object fact = facts.get(fieldCode);
            // 取值为null直接算未触发
            if (fact == null) {
                conditionTriggerMap.put(conditionCode, false);
                continue;
            }
            Map<String, Object> env = new HashMap<>();
            env.put(ValueTypeEnum.FACT.getPlaceholder(), fact);
            env.put(ValueTypeEnum.EXPECT.getPlaceholder(), expect);
            Object o = engine.execute(operator, env);
            Boolean conditionTrigger = o == null ? null : (Boolean) o;
            conditionTriggerMap.put(conditionCode, conditionTrigger);
        }
        return conditionTriggerMap;
    }

    /**
     * 获取所有的实际取值
     * 相同数据源只取一次
     *
     * @param fieldList
     * @param params    入参（通过浏览器传过来）
     * @return
     */
    private Map<String, Map<String, Object>> getPathFactMap(List<RuleFieldDO> fieldList, Map<String, Object> params) {
        Map<String, Set<String>> sourceMap = getEmptySourceMap();

        for (RuleFieldDO ruleFieldDO : fieldList) {
            String fieldSourceType = ruleFieldDO.getFieldSourceType();
            String fieldSource = ruleFieldDO.getFieldSource();

            Set<String> set = sourceMap.get(fieldSourceType);
            set.add(fieldSource);
        }

        Map<String, Map<String, Object>> pathFactMap = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : sourceMap.entrySet()) {
            String fieldSourceType = entry.getKey();
            Set<String> pathSet = entry.getValue();
            for (String fieldSource : pathSet) {
                if (pathFactMap.get(fieldSource) != null) {
                    continue;
                }
                Map<String, Object> factMap = null;
                if (FieldSourceTypeEnum.REFLECT.name().equals(fieldSourceType)) {
                    factMap = getReflect(fieldSource, params);
                } else if (FieldSourceTypeEnum.PARAM.name().equals(fieldSourceType)) {
                    factMap = params;
                } else if (FieldSourceTypeEnum.HTTP.name().equals(fieldSourceType)) {
                    factMap = getHttp(fieldSource, params);
                }
                pathFactMap.put(fieldSource, factMap);
            }
        }
        return pathFactMap;
    }

    /**
     * 初始化数据源
     *
     * @return key: fieldSourceTypeType,value:fieldSource
     */
    private Map<String, Set<String>> getEmptySourceMap() {
        Map<String, Set<String>> sourceMap = new HashMap<>();
        for (FieldSourceTypeEnum value : FieldSourceTypeEnum.values()) {
            sourceMap.put(value.name(), new HashSet<>());
        }
        return sourceMap;
    }

    /**
     * 通过反射取值
     * 方法类必须在spring容器中
     *
     * @param path   反射地址
     * @param params 入参（通过浏览器传过来）
     * @return
     */
    private Map<String, Object> getReflect(String path, Map<String, Object> params) {
        String[] split = path.split("#");
        String classPath = split[0];
        String methodPath = split[1];

        Object bean = applicationContext.getBean(classPath);
        Map<String, Object> map = Collections.EMPTY_MAP;
        try {
            Class<?> clazz = bean.getClass();
            Method method = clazz.getDeclaredMethod(methodPath, Map.class);
            Object invoke = method.invoke(bean, params);
            map = JSON.parseObject(JSON.toJSONString(invoke), Map.class);
        } catch (Exception e) {
            // TODO: 取值失败怎么处理？
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 通过反射取值
     * 方法类必须在spring容器中
     *
     * @param path   取数地址
     * @param params 入参（通过浏览器传过来）
     * @return
     */
    private Map<String, Object> getHttp(String path, Map<String, Object> params) {
        SpringRestTemplate springRestTemplate = new SpringRestTemplate();
        return springRestTemplate.getForObject(path, params, Map.class);
    }

    /**
     * 获取所有条件编码
     *
     * @param conditions
     * @return
     */
    private List<String> getConditionCodeList(String conditions) {
        if (StringUtils.isBlank(conditions)) {
            return Collections.EMPTY_LIST;
        }

        conditions = conditions.replace(" ", "");
        conditions = conditions.replace("(", "");
        conditions = conditions.replace(")", "");
        conditions = conditions.replace("&&", ",");
        conditions = conditions.replace("||", ",");
        return Arrays.asList(conditions.split(","));
    }

}
