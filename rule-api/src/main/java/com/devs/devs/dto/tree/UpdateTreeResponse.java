package com.devs.devs.dto.tree;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTreeResponse {
    /**
     * id
     */
    private Long decisionTreeId;
}
