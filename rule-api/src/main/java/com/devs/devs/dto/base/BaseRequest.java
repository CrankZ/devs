package com.devs.devs.dto.base;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BaseRequest {

    /**
     * 请求流水号
     */
    @NotNull
    @NotBlank
    private String requestId;

}