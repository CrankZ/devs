package com.devs.devs.jsonMapper.simple;

import com.devs.devs.jsonMapper.TriggerResult;
import com.devs.devs.jsonMapper.action.Action;
import com.devs.devs.jsonMapper.action.Conditions;
import lombok.Data;

import java.util.List;

/**
 * 简单规则
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
public class Rule extends TriggerResult {

    /**
     * 场景id
     */
    private String sceneId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 描述
     */
    private String desc;

    /**
     * 条件集
     * if
     * when
     */
    private Conditions conditions;

    /**
     * 触发成功
     * success
     * then
     */
    private List<Action> then;

    /**
     * 触发失败
     * else
     * failure
     * otherwise
     */
    private List<Action> otherwise;

    /**
     * 规则执行优先级
     * 有可能规则有上下级依赖关系
     */
    private Integer priority;

    /**
     * 执行异常
     */
    private String errorMsg;
    private String errorType;

}
