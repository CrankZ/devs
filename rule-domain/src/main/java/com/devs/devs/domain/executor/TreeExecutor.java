package com.devs.devs.domain.executor;

import com.alibaba.fastjson.JSON;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.enums.RuleTypeEnum;
import com.devs.devs.dto.engine.ExecuteTreeResponse;
import com.devs.devs.jsonMapper.action.Action;
import com.devs.devs.jsonMapper.decisionTree.DecisionTree;
import com.devs.devs.jsonMapper.decisionTree.Edge;
import com.devs.devs.jsonMapper.decisionTree.Node;
import com.devs.devs.valueobject.DecisionTreeResult;
import com.devs.devs.exception.NoRuleException;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author 松梁
 * @date 2021/7/16
 */
@SuperBuilder
public class TreeExecutor extends BaseExecutor {

    /**
     * 执行决策树
     *
     * @return
     */
    public ExecuteTreeResponse executeDecisionTree() {
        // 保存入参
        saveRecord();

        // 先取到对应场景下所有的规则
        List<RuleInfoDO> ruleInfoDOList = getRuleInfoList(RuleTypeEnum.DECISION_TREE);

        if (CollectionUtils.isEmpty(ruleInfoDOList)) {
            throw new NoRuleException();
        }

        List<DecisionTree> decisionTreeVList = ruleInfoDOList.stream()
                .map(dto -> JSON.parseObject(dto.getRuleJson(), DecisionTree.class))
                .collect(Collectors.toList());
        // 需要转getRuleJson
//        DecisionTreeMapper.INSTANCE.do2vList(ruleInfoDOList);

        List<DecisionTreeResult> ruleResultList = getRuleResultList(decisionTreeVList);

        // 4.异步记录结果
        // 这里只保存直接结果，规则结构在上面入参表保存
        saveResult(ruleResultList);

        return ExecuteTreeResponse.builder()
                .sceneId(ruleRecordDO.getSceneId())
                .facts(JSON.parseObject(ruleRecordDO.getParam()))
                .ruleResults(ruleResultList)
                .build();
    }

    /**
     * 获取执行接口
     *
     * @param decisionTreeVList
     * @return
     */
    private List<DecisionTreeResult> getRuleResultList(List<DecisionTree> decisionTreeVList) {
        List<DecisionTreeResult> ruleResultList = new ArrayList<>();

        // 当前场景下可能有多个决策树
        for (DecisionTree decisionTreeV : decisionTreeVList) {
            // TODO:  对decisionTreeV有修改
            executeTree(decisionTreeV, true, true);

            // TODO: facts
            DecisionTreeResult decisionTreeResult = DecisionTreeResult.builder()
                    .facts(JSON.parseObject(ruleRecordDO.getParam(), Map.class))

                    .decisionTreeV(decisionTreeV)
                    .build();
            ruleResultList.add(decisionTreeResult);
        }
        return ruleResultList;
    }

    /**
     * 执行决策树
     *
     * @param decisionTreeV
     * @param isAllExecute  执行全部分支
     * @param isMutex       触发一个分支后，是否继续执行其他分支
     * @return
     */
    public Object executeTree(DecisionTree decisionTreeV, boolean isAllExecute, boolean isMutex) {
        Node parentNode = decisionTreeV.getRoot();
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

                boolean normalNode = subNode.getActions() == null;
                // 如果有actions就当做是叶子节点
                if (!normalNode) {
                    decisionTreeV.setTrigger(edgeTrigger);
                    subNode.setTrigger(edgeTrigger);

                    // 执行叶子结点的动作
                    List<Action> actions = subNode.getActions();
                    super.action(actions);
                }

                if (isAllExecute || edgeTrigger) {
                    if (normalNode && subNode != null) {
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

    /**
     * 执行边
     *
     * @param fieldCode 字段编码
     * @param value     比较值
     * @param operator  比较逻辑
     * @return
     */
    private Boolean executeEdge(String fieldCode, Object value, String operator) {
        Map<String, Object> factsByParam = JSON.parseObject(ruleRecordDO.getParam(), Map.class);

        Object fact = factsByParam.get(fieldCode);

        Map<String, Object> env = new HashMap<>();
        env.put("$1", fact);
        env.put("$2", value);

        return (Boolean) engine.execute(operator, env);
    }

}
