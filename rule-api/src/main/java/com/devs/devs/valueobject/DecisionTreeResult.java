package com.devs.devs.valueobject;

import com.devs.devs.jsonMapper.decisionTree.DecisionTree;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 决策树执行结果
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
@SuperBuilder
public class DecisionTreeResult extends BaseResult {
    /**
     * 决策树结构体（包括是否触发）
     */
    private DecisionTree decisionTreeV;
}
