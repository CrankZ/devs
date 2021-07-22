package com.devs.devs.dto.engine;

import com.devs.devs.valueobject.DecisionTreeResult;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 单次执行后，返回给前端的结果
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
@Builder
public class ExecuteTreeResponse {
    /**
     * 场景ID
     */
    private Long sceneId;

    /**
     * 字段真实的值
     */
    private Map<String, Object> facts;

    /**
     * 规则集列表，一个场景下面可能会有多个规则集
     */
    private List<DecisionTreeResult> ruleResults;
}
