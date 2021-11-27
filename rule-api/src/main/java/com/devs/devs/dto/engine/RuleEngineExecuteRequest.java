package com.devs.devs.dto.engine;

import lombok.Data;

import java.util.Map;

/**
 * 规则引擎执行入参
 *
 * @author 松梁
 * @date 2021/11/24
 */
@Data
public class RuleEngineExecuteRequest {

    private String requestId;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 接口参数
     */
    private Map<String, Object> params;
}
