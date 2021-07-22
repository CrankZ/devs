package com.devs.devs.jsonMapper.decisionTree;

import com.devs.devs.jsonMapper.TriggerResult;
import lombok.Data;

/**
 * 决策树执行结果
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
public class DecisionTree extends TriggerResult {
    /**
     * 场景
     */
    private Long sceneId;

    /**
     * 决策树名称
     */
    private String treeName;

    /**
     * 根节点
     */
    private Node root;
}
