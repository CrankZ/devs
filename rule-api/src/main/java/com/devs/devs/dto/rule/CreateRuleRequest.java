package com.devs.devs.dto.rule;

import com.devs.devs.dto.base.BaseRequest;
import com.devs.devs.jsonMapper.action.Action;
import com.devs.devs.jsonMapper.action.Conditions;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateRuleRequest extends BaseRequest {

    /**
     * 场景id
     */
    @NotNull
    private Long sceneId;

    @NotBlank
    private String ruleName;

    /**
     * 条件集
     * if
     * when
     */
    @NotNull
    private Conditions conditions;

    /**
     * 触发成功
     * success
     * then
     */
    @NotNull
    private List<Action> then;

    /**
     * 触发失败
     * else
     * failure
     * otherwise
     */
    @NotNull
    private List<Action> otherwise;

    /**
     * 备注
     */
    @NotBlank
    private String ruleDesc;

}
