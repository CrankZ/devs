package com.devs.devs.service.impl;

import com.alibaba.fastjson.JSON;
import com.devs.devs.consts.ActionEnum;
import com.devs.devs.consts.FieldSourceEnum;
import com.devs.devs.consts.LogicalOperatorsEnum;
import com.devs.devs.consts.NodeEnum;
import com.devs.devs.consts.RuleEnum;
import com.devs.devs.dao.DecisionTreeMapper;
import com.devs.devs.dao.FieldMapper;
import com.devs.devs.dao.RuleInfoMapper;
import com.devs.devs.dao.RuleRecordMapper;
import com.devs.devs.dao.RuleResultMapper;
import com.devs.devs.dao.RuleSetMapper;
import com.devs.devs.dao.model.FieldDO;
import com.devs.devs.dao.model.RuleInfoDO;
import com.devs.devs.dto.ExecuteParam;
import com.devs.devs.dto.Field;
import com.devs.devs.dto.jsonMapper.decisionTree.DecisionTree;
import com.devs.devs.dto.jsonMapper.decisionTree.Edge;
import com.devs.devs.dto.jsonMapper.decisionTree.Node;
import com.devs.devs.dto.jsonMapper.ruleSet.Action;
import com.devs.devs.dto.jsonMapper.ruleSet.ActionHttp;
import com.devs.devs.dto.jsonMapper.ruleSet.Condition;
import com.devs.devs.dto.jsonMapper.ruleSet.Rule;
import com.devs.devs.dto.jsonMapper.ruleSet.RuleSet;
import com.devs.devs.dto.vo.ModelResult;
import com.devs.devs.dto.vo.RuleResult;
import com.devs.devs.dto.vo.RuleSetResult;
import com.devs.devs.executor.Creator;
import com.devs.devs.service.EngineService;
import com.devs.devs.utils.PojoUtils;
import com.devs.devs.utils.SaoUtils;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class EngineServiceImpl implements EngineService {

  private static final AviatorEvaluatorInstance engine = Creator.getEngine();
  @Autowired
  private RuleSetMapper ruleSetMapper;
  @Autowired
  private DecisionTreeMapper decisionTreeMapper;
  @Autowired
  private RuleResultMapper ruleResultMapper;
  @Autowired
  private FieldMapper fieldMapper;
  @Autowired
  private SaoUtils saoUtils;
  @Autowired
  @Qualifier("asyncExecutor")
  private ThreadPoolExecutor asyncExecutor;


  @Autowired
  private RuleRecordMapper ruleRecordMapper;

  @Autowired
  private RuleInfoMapper ruleInfoMapper;

  private static Map<String, Object> FACTS;

  /**
   * 执行规则
   * 执行的维度应该规则组
   * TODO: 返回的应该是用户自定义的结构体
   * 这里应该是规则集
   *
   * @param executeParam
   * @return 所有字段的结果
   * 分支触发结果
   * 最终触发结果
   * 执行的动作
   */
  @Override
  public ModelResult executeRuleSet(ExecuteParam executeParam) {
    // 异步记录执行结果
    String requestId = executeParam.getRequestId();
    String modelId = executeParam.getModel();
    Map<String, Object> factsByParam = executeParam.getFacts();
    asyncExecutor.execute(() -> ruleRecordMapper.insert(requestId, modelId, JSON.toJSONString(factsByParam)));
    ModelResult modelResult = new ModelResult();
    modelResult.setModel(modelId);

    Map<FieldSourceEnum, Map<String, Object>> allFacts = new HashMap<>();
    allFacts.put(FieldSourceEnum.PARAM, factsByParam);
    modelResult.setFacts(allFacts);

    // 先取到对应分类下所有的规则
    List<RuleInfoDO> ruleInfoDOList = ruleInfoMapper.selectByModelId(modelId, RuleEnum.RULE_SET.toString());

    if (ruleInfoDOList == null) {
      return null;
    }

    List<RuleSet> ruleSetList = new ArrayList<>();
    for (RuleInfoDO ruleInfoDO : ruleInfoDOList) {
      String json = ruleInfoDO.getJson();
      RuleSet ruleSet = JSON.parseObject(json, RuleSet.class);
      ruleSetList.add(ruleSet);
    }

    List<RuleSetResult> ruleSetResultList = new ArrayList<>();

    // TODO: 这两个统一成一个
    FACTS = factsByParam;
    Map<String, Object> factsByHttpAll = new HashMap<>();
    // 当前场景下可能有多个规则集
    for (RuleSet ruleSet : ruleSetList) {
      RuleSetResult ruleSetResult = new RuleSetResult();
      ruleSetResult.setName(ruleSet.getName());

      List<Rule> rules = ruleSet.getRules();
      List<RuleResult> ruleResultList = new ArrayList<>();

      for (Rule rule : rules) {
        RuleResult ruleResult = new RuleResult();
        List<Condition> and = rule.getConditions().getAnd();
        List<Condition> or = rule.getConditions().getOr();

        Set<String> fieldCodeList = getFieldCodeList(and, or);
        // 在service层获取，并且在service转变类型
        List<FieldDO> fieldDOList = fieldMapper.getFieldList(new ArrayList<>(fieldCodeList));
        List<Field> fieldList = PojoUtils.getFiledList(fieldDOList);
        List<Field> fieldByHttpList = fieldList.stream().filter(fieldDO -> FieldSourceEnum.HTTP.equals(fieldDO.getSource())).collect(Collectors.toList());
        // 字段取值，暂时只支持GET方式，所以就不需要参数
        Map<String, Object> factsByHttp = getParamsByHttp(fieldByHttpList, null);
        factsByHttpAll.putAll(factsByHttp);

        FACTS.putAll(factsByHttp);
        // factsByParam是放在这里面吗？
        FACTS.putAll(factsByParam);

        // 最终触发结果
        boolean ruleTrigger = executeConditions(and, or);
        rule.setTrigger(ruleTrigger);
        List<Action> actions;
        if (ruleTrigger) {
          actions = rule.getThen();
        } else {
          actions = rule.getOtherwise();
        }
        action(actions);


        Map<FieldSourceEnum, Map<String, Object>> facts = new HashMap<>();
        facts.put(FieldSourceEnum.PARAM, factsByParam);
        facts.put(FieldSourceEnum.HTTP, factsByHttpAll);
        ruleResult.setFacts(facts);
        ruleResult.setRule(rule);
        ruleResultList.add(ruleResult);
//        FACTS.clear();
      }
      ruleSetResult.setRuleResults(ruleResultList);
      ruleSetResultList.add(ruleSetResult);
    }

    Map<RuleEnum, List<RuleSetResult>> ruleResults = new HashMap<>();
    ruleResults.put(RuleEnum.RULE_SET, ruleSetResultList);
    modelResult.setRuleResults(ruleResults);

    Map<FieldSourceEnum, Map<String, Object>> facts = new HashMap<>();
    facts.put(FieldSourceEnum.PARAM, factsByParam);
    facts.put(FieldSourceEnum.HTTP, factsByHttpAll);
    modelResult.setFacts(facts);
    // 4.异步记录结果
    // TODO: 结果保存分为两类，结构体和结果，也就是快照版本
    // 1.当时真实的结构体，2.当时的字段（入参的字段、获取的字段）+执行的结果+触发记录
    asyncExecutor.execute(() -> saveRuleResultList(requestId, modelId, ruleResults));

    return modelResult;
  }

  private void action(List<Action> actions) {
    for (Action action : actions) {
      ActionEnum type = action.getType();
      Object value = action.getValue();
      action.setTrigger(true);

      if (ActionEnum.PRINT.equals(type)) {
        action.setResult(value);
      } else {
        // TODO: 结构体应该可以支持根据规则执行后的结果
        ActionHttp actionHttp = JSON.parseObject(JSON.toJSONString(action.getValue()), ActionHttp.class);
        String method = actionHttp.getMethod();
        String url = actionHttp.getUrl();
        Map<String, Object> params = actionHttp.getParams();

        Map<String, Object> httpResult;
        if (HttpMethod.GET.equals(method)) {
          httpResult = saoUtils.getForObject(url, params, Map.class);
        } else {
          httpResult = saoUtils.postForObject(url, params, Map.class);
        }
        action.setResult(httpResult);
      }
    }
  }

  @Override
  public ModelResult executeDecisionTree(ExecuteParam executeParam) {
    // 异步记录执行结果
    String requestId = executeParam.getRequestId();
    String modelId = executeParam.getModel();
    Map<String, Object> factsByParam = executeParam.getFacts();
//    asyncExecutor.execute(() -> ruleRecordMapper.insert(requestId, modelId, JSON.toJSONString(factsByParam)));
    ModelResult modelResult = new ModelResult();
    modelResult.setModel(modelId);

    Map<FieldSourceEnum, Map<String, Object>> allFacts = new HashMap<>();
    allFacts.put(FieldSourceEnum.PARAM, factsByParam);
    modelResult.setFacts(allFacts);

    // 先取到对应分类下所有的规则
    List<RuleInfoDO> ruleInfoDOList = ruleInfoMapper.selectByModelId(modelId, RuleEnum.DECISION_TREE.toString());

    if (ruleInfoDOList == null) {
      return null;
    }

    List<DecisionTree> DecisionTreeList = new ArrayList<>();
    for (RuleInfoDO ruleInfoDO : ruleInfoDOList) {
      String json = ruleInfoDO.getJson();
      DecisionTree decisionTree = JSON.parseObject(json, DecisionTree.class);
      DecisionTreeList.add(decisionTree);
    }

    List<RuleSetResult> ruleSetResultList = new ArrayList<>();

    FACTS = factsByParam;
    Map<String, Object> factsByHttpAll = new HashMap<>();
    // 当前场景下可能有多个规则集
    for (DecisionTree decisionTree : DecisionTreeList) {
      RuleSetResult ruleSetResult = new RuleSetResult();
      ruleSetResult.setName(decisionTree.getName());
      executeTree(decisionTree, true, true);
      FACTS.clear();
    }

    Map<RuleEnum, List<RuleSetResult>> ruleResults = new HashMap<>();
    ruleResults.put(RuleEnum.RULE_SET, ruleSetResultList);
    modelResult.setRuleResults(ruleResults);

    Map<FieldSourceEnum, Map<String, Object>> facts = new HashMap<>();
    facts.put(FieldSourceEnum.PARAM, factsByParam);
    facts.put(FieldSourceEnum.HTTP, factsByHttpAll);
    modelResult.setFacts(facts);
    // 4.异步记录结果
    // 1.当时真实的结构体，2.当时的字段（入参的字段、获取的字段）+执行的结果+触发记录
//    asyncExecutor.execute(() -> saveRuleResultList(requestId, modelId, ruleResults));

    return modelResult;

  }

  public Object executeTree(DecisionTree decisionTree, boolean isAllExecute, boolean isMutex) {
    Node parentNode = decisionTree.getRoot();
    Queue<Node> nodeQueue = new ArrayDeque<>();
    if (parentNode != null) {
      nodeQueue.add(parentNode);
    }
    while (!nodeQueue.isEmpty()) {
      parentNode = nodeQueue.remove();
      List<Edge> edges = parentNode.getEdges();
      if (edges == null) {
        continue;
      }

      for (int i = 0; i < edges.size(); i++) {
        Edge edge = edges.get(i);
        Node subNode = edge.getNode();
        // 到这一步，肯定执行了，执行就一定是true或false
//        FACTS.put(parentNode.getFields(), edge.getValue());
        boolean edgeTrigger = executeEdge(parentNode.getFields(), edge.getValue(), edge.getOperator());
        // 如果是叶子节点
        if (subNode == null) {
          continue;
        }
        if (NodeEnum.END.equals(subNode.getType())) {
          decisionTree.setTrigger(edgeTrigger);
          subNode.setTrigger(edgeTrigger);

          // 执行叶子结点的动作
          List<Action> actions = subNode.getActions();
          action(actions);
        }
        if (isAllExecute || edgeTrigger) {
          if (NodeEnum.NORMAL.equals(subNode.getType()) && subNode != null) {
            nodeQueue.add(subNode);
            if (isMutex) {
              break;
            }
          }
        }
      }
    }
    return null;
  }

  private Map<String, Object> getParamsByHttp(List<Field> fieldList, Map<String, Object> params) {
    Map<String, Object> factsByHttp = new HashMap<>();

    Set<String> urlSet = new HashSet<>();
    for (Field field : fieldList) {
      String url = field.getPath();
      urlSet.add(url);
    }

    // 相同url去重，只取一次
    Map<String, Map<String, Object>> urlMap = new HashMap<>();
    for (String url : urlSet) {
      Map<String, Object> map = saoUtils.getForObject(url, null, Map.class);
      urlMap.put(url, map);
    }

    for (Field field : fieldList) {
      Object fact = urlMap.get(field.getPath()).get(field.getFieldCode());
      field.setFact(fact);
      factsByHttp.put(field.getFieldCode(), fact);
    }
    return factsByHttp;
  }

  public List<DecisionTree> executeDecisionTree(String application, Map<String, Object> facts) {
    // 先取到对应分类下所有的规则
    List<DecisionTree> decisionTreeList = decisionTreeMapper.getDecisionTree(application, null, null);

    FACTS = facts;

    // 决策树的执行应该是广度优先的
    for (DecisionTree decisionTree : decisionTreeList) {
      List<Edge> edges = decisionTree.getRoot().getEdges();
      for (Edge edge : edges) {
        edge.getOperator();
      }

    }


    // 4.异步记录结果
//    asyncExecutor.execute(() -> saveRuleResultList(null));

    return decisionTreeList;
  }

  /**
   * 执行action
   *
   * @param actions
   */
  private void runActions(List<Action> actions) {
    for (Action action : actions) {
      action.getType();
      action.getValue();
    }

  }

  /**
   * @param and
   * @param or
   * @return
   */
  private static Boolean executeConditions(List<Condition> and, List<Condition> or) {
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
        executeConditions(condition.getAnd(), condition.getOr());
        continue;
      }
      String operator = condition.getOperator();
      Object value = condition.getValue();
      Object fact = FACTS.get(fieldCode);

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
    boolean trigger = (Boolean) engine.execute(exp);
    return trigger;
  }

  private static Boolean executeEdge(String fieldCode, Object value, String operator) {
    Object fact = FACTS.get(fieldCode);

    Map<String, Object> env = new HashMap<>();
    env.put("$1", fact);
    env.put("$2", value);

    boolean trigger = (Boolean) engine.execute(operator, env);
    return trigger;
  }

  private Map<String, Object> getAllFacts(List<Condition> and, List<Condition> or) {
    List<Condition> conditions;
    if (and == null) {
      conditions = or;
    } else {
      conditions = and;
    }

    // 每个condition里面还有子节点
    Map<String, Object> map = new HashMap<>();
    if (conditions == null) {
      return null;
    }
    for (int i = 0; i < conditions.size(); i++) {
      Condition condition = conditions.get(i);
      String field = condition.getFieldCode();
      Object fact = FACTS.get(field);
      map.put(field, fact);

      Map<String, Object> insideFacts = getAllFacts(condition.getAnd(), condition.getOr());
      if (insideFacts != null) {
        map.putAll(insideFacts);
      }
    }

    return map;
  }

  private Set<String> getFieldCodeList(List<Condition> and, List<Condition> or) {
    List<Condition> conditions;
    if (and == null) {
      conditions = or;
    } else {
      conditions = and;
    }

    // 每个condition里面还有子节点
    Map<String, Object> map = new HashMap<>();
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
   * 预演
   * 用历史的案件跑规则，看看规则的触发率
   */
  private void rehearsal() {

  }

  /**
   * 结构体和字段值区分开
   * 结构体直接带上执行结果 true false
   *
   * @param requestId
   * @param modelId
   * @param ruleResults
   */
  private void saveRuleResultList(String requestId, String modelId, Map<RuleEnum, List<RuleSetResult>> ruleResults) {
    for (Map.Entry<RuleEnum, List<RuleSetResult>> entry : ruleResults.entrySet()) {
      RuleEnum ruleEnum = entry.getKey();
      List<RuleSetResult> ruleSetResults = entry.getValue();
      for (RuleSetResult ruleSetResult : ruleSetResults) {
        ruleResultMapper.insert(requestId, modelId, ruleEnum.toString(), JSON.toJSONString(ruleSetResult));
      }
    }
  }

}
