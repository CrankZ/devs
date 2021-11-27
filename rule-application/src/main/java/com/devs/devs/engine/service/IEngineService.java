package com.devs.devs.engine.service;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.engine.RuleEngineExecuteRequest;
import com.devs.devs.dto.engine.RuleEngineExecuteResponse;

import java.util.Map;

public interface IEngineService {

    ResultEntity<RuleEngineExecuteResponse> executeRule(RuleEngineExecuteRequest request);

    Map<String, Object> testReflect(Map<String, Object> properties);
}