package com.devs.devs.dto.tree;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTreeResponse {
    /**
     * id
     */
    private Long decisionTreeId;
}
