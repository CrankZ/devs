package com.devs.devs.dto.tree;

import com.devs.devs.dto.base.BaseRequest;
import com.devs.devs.jsonMapper.decisionTree.Node;
import lombok.Data;

/**
 * @author 松梁
 * @date 2021/7/16
 */
@Data
public class CreateTreeRequest extends BaseRequest {
    /**
     * 场景
     */
    private Long sceneId;

    /**
     * 决策树名称
     */
    private String treeName;

    /**
     * 描述
     */
    private String desc;

    /**
     * 根节点
     */
    private Node root;
}
