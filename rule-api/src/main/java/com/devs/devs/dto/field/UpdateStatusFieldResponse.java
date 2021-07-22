package com.devs.devs.dto.field;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStatusFieldResponse {
    /**
     * id
     */
    private Long fieldId;
}
