package com.devs.devs.service;

import com.devs.devs.dto.ExecuteParam;
import com.devs.devs.dto.vo.ModelResult;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public interface EngineService {

  ModelResult executeRuleSet(ExecuteParam executeParam);

  ModelResult executeDecisionTree(ExecuteParam executeParam);
}
