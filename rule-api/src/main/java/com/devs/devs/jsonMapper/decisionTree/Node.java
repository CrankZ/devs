package com.devs.devs.jsonMapper.decisionTree;

import com.devs.devs.jsonMapper.TriggerResult;
import com.devs.devs.jsonMapper.action.Action;
import lombok.Data;

import java.util.List;

/**
 * 节点
 * 主要是字段的组合
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
public class Node extends TriggerResult {
    /**
     * 多个字段组合可能会有新的含义
     * 如果是urule那种长方形的样式，直接展示逻辑运算就行了，不用起别名
     * 但是如果是用原型的，表达式可能就放不下可能就需要起别名
     * TODO: 但是直线显示别名可能会让用户认为是一个字段，如果做区分？
     */
    private String name;

    /**
     * 支持多个字段组合
     */
    private String fields;

    /**
     * 边
     */
    private List<Edge> edges;

    /**
     * 子节点
     */
    private List<Action> actions;

}
