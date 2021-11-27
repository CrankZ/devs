package com.devs.devs.engine.service;

import com.devs.devs.ResultEntity;
import com.devs.devs.domain.executor.RuleExecutor;
import com.devs.devs.dto.engine.RuleEngineExecuteRequest;
import com.devs.devs.dto.engine.RuleEngineExecuteResponse;
import com.devs.devs.factor.EngineFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 松梁
 * @date 2021/7/16
 */
@Slf4j
@Service
public class EngineServiceImpl implements IEngineService {

    @Autowired
    private EngineFactory engineFactory;

    @Override
    public ResultEntity<RuleEngineExecuteResponse> executeRule(RuleEngineExecuteRequest request) {
        RuleExecutor ruleExecutor = engineFactory.create(request);
        RuleEngineExecuteResponse response = ruleExecutor.executeRule();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public Map<String, Object> testReflect(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 10);
        map.putAll(params);
        return map;
    }

}