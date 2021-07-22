package com.devs.devs.engine.service;

import com.devs.devs.domain.executor.RuleExecutor;
import com.devs.devs.domain.executor.TreeExecutor;
import com.devs.devs.ResultEntity;
import com.devs.devs.dto.engine.ExecuteRequest;
import com.devs.devs.dto.engine.ExecuteRuleResponse;
import com.devs.devs.dto.engine.ExecuteTreeResponse;
import com.devs.devs.factor.EngineFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 松梁
 * @date 2021/7/16
 */
@Slf4j
@Service
public class EngineServiceImpl implements IEngineService {

    @Autowired
    private EngineFactory engineFactory;

    /**
     * 创建插件
     *
     * @param request
     * @return
     */
    @Override
    public ResultEntity executeRule(ExecuteRequest request) {
        RuleExecutor ruleExecutor = engineFactory.create(request);
        ExecuteRuleResponse response = ruleExecutor.executeRule();
//        ExecuteRuleSetResponse response = ExecuteRuleSetResponse.builder().engineId(engineId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity executeDecisionTree(ExecuteRequest request) {
        TreeExecutor engine = engineFactory.createTree(request);
        ExecuteTreeResponse response = engine.executeDecisionTree();
//        ExecuteRuleSetResponse response = ExecuteRuleSetResponse.builder().engineId(engineId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }
}