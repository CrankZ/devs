package com.devs.devs.jsonMapper.decisionTree;

import com.devs.devs.jsonMapper.TriggerResult;
import lombok.Data;

/**
 * 边
 */
@Data
public class Edge extends TriggerResult {
    /**
     * 操作符
     */
    private String operator;

    /**
     * 比较值
     */
    private Object value;

    /**
     * 节点
     */
    private Node node;

}
