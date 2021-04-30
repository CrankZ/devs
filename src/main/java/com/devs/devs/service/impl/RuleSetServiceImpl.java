package com.devs.devs.service.impl;

import com.alibaba.fastjson.JSON;
import com.devs.devs.consts.ActionEnum;
import com.devs.devs.consts.LogicalOperatorsEnum;
import com.devs.devs.consts.RuleEnum;
import com.devs.devs.controller.params.RuleSetBoolParams;
import com.devs.devs.controller.params.RuleSetListParams;
import com.devs.devs.controller.params.RuleSetParams;
import com.devs.devs.dao.RuleInfoMapper;
import com.devs.devs.dao.RuleSetMapper;
import com.devs.devs.dao.model.FieldDO;
import com.devs.devs.dao.model.RuleInfoDO;
import com.devs.devs.dto.CustomResult;
import com.devs.devs.dto.Field;
import com.devs.devs.dto.RuleSetTemp;
import com.devs.devs.dto.jsonMapper.ruleSet.Action;
import com.devs.devs.dto.jsonMapper.ruleSet.Conditions;
import com.devs.devs.dto.jsonMapper.ruleSet.Rule;
import com.devs.devs.dto.jsonMapper.ruleSet.RuleSet;
import com.devs.devs.dto.vo.RuleResult;
import com.devs.devs.dto.vo.RuleSetListVO;
import com.devs.devs.service.RuleSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuleSetServiceImpl implements RuleSetService {
  @Autowired
  private RuleSetMapper ruleSetMapper;

  @Autowired
  private RuleInfoMapper ruleInfoMapper;


  /**
   * 直接字段组成规则
   * 但是保存的时候是否直接把变量保存到规则里面？
   *
   * @param expression 表达式、支持与或
   * @param env        参数
   * @param comment    注释
   */
  @Override
  public void addRule(String expression, Map<String, Object> env, String comment) {
    ruleSetMapper.addRuleSet(expression, env, comment);
  }

  @Override
  public void add(RuleSetParams params) {
    ruleInfoMapper.insert(null);
  }

  @Override
  public void addBool(RuleSetBoolParams params) {
    RuleInfoDO ruleInfo = new RuleInfoDO();
    ruleInfo.setCode("规则集编码");
    ruleInfo.setName(params.getRuleSetName());
    ruleInfo.setType(RuleEnum.RULE_SET.name());
    ruleInfo.setModelId("ROOT");
    ruleInfo.setDesc("规则集描述");
    ruleInfo.setCreateTime(new Date());
    ruleInfo.setUpdateTime(new Date());

    RuleSet ruleSet = new RuleSet();
    ruleSet.setName("规则集名称");
    ruleSet.setDesc("规则集描述");

    Rule rule = new Rule();

    // 如果
    Conditions conditions = new Conditions();
    if (LogicalOperatorsEnum.AND.getName().equals(params.getLogicalOperator())) {
      conditions.setAnd(params.getConditions());
    } else {
      conditions.setOr(params.getConditions());
    }

    // 那么
    Action oneThen = new Action();
    oneThen.setType(ActionEnum.BOOL);
    oneThen.setValue(true);

    // 否则
    Action oneOtherWise = new Action();
    oneOtherWise.setType(ActionEnum.BOOL);
    oneOtherWise.setValue(false);

    rule.setConditions(conditions);

    rule.setThen(Collections.singletonList(oneThen));
    rule.setOtherwise(Collections.singletonList(oneOtherWise));

    ruleSet.setRules(Collections.singletonList(rule));

    // 后期用来复原树的结构的时候能用到
    ruleInfo.setJson(JSON.toJSONString(ruleSet));

    ruleInfoMapper.insert(ruleInfo);
  }

  /**
   * 用户自定义的结构体，用来返回结果
   * 弄一个枚举库，或者说是结果库，用户自定义的结果只是从这里选择
   * 规则集肯定有一个结果，但是决策树可能都走不到叶子结点
   *
   * @param ruleSetTemp
   * @return
   */
  private RuleResult packCustomStruct(RuleSetTemp ruleSetTemp) {

    RuleResult ruleResult = new RuleResult();
    Boolean bool = ruleSetTemp.getResult();
    // 改成字符串是为了通用，因为虽然这里只是布尔类型，但是决策树会有多个结果
    CustomResult customResult = ruleSetTemp.getCustomResultMap().get(bool.toString());

//    ruleResult.setResultId(ruleSetTemp.getId());
//    ruleResult.setName(ruleSetTemp.getName());
//    ruleResult.setCustomResult(customResult);

    return ruleResult;
  }

  /**
   * 获取规则执行结果
   */
  private List<RuleResult> getRuleResultList() {
    return null;
  }

  /**
   * 通过反射取到相应的值
   *
   * @param fieldList
   * @return
   * @deprecated 参数直接从外部传进来
   */
  @Deprecated
  private Map<String, Object> getParamsByReflect(List<Field> fieldList) throws Exception {
    Map<String, Object> totalParams = new HashMap<>();
    for (Field field : fieldList) {
      String path = field.getPath();
      // 这个方法是先确定类，再确定方法
      Class<?> clazz = Class.forName("");
      Method method = clazz.getMethod(path, Map.class);
      // 约定必须返回Map类型，并且支持多个返回值
      Map<String, Object> singleParams = (Map<String, Object>) method.invoke(clazz.newInstance(), 22, "小明");
      if (singleParams != null) {
        field.setFact(singleParams);
        totalParams.putAll(singleParams);
      }
    }
    return totalParams;
  }

  /**
   * 通过HTTP取值
   *
   * @param fieldList
   */
  private void getParamsByHttp(List<FieldDO> fieldList) {

  }

  @Override
  public RuleSetListVO list(RuleSetListParams params) {
    List<RuleInfoDO> list = ruleInfoMapper.list(params);
    RuleSetListVO vo = new RuleSetListVO();
    vo.setCurrent(1);
    vo.setPageSize(100);
    vo.setTotal(100);
    vo.setSuccess(true);
    vo.setData(list);
    return vo;
  }

}
