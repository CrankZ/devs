package com.devs.devs.dto.field;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFieldResponse {
    /**
     * id
     */
    private Long fieldId;
}
