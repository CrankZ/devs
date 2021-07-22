package com.devs.devs.dto.rule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRuleResponse {
    /**
     * id
     */
    private Long ruleId;
}
