package com.devs.devs.engine.service;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.engine.ExecuteRequest;

public interface IEngineService {

    public ResultEntity executeRule(ExecuteRequest request);

    public ResultEntity executeDecisionTree(ExecuteRequest request);
}