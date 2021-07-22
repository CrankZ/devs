package com.devs.devs.dto.rule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStatusRuleResponse {
    /**
     * id
     */
    private Long ruleId;
}
