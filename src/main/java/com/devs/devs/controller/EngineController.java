package com.devs.devs.controller;

import com.devs.devs.dto.ExecuteParam;
import com.devs.devs.dto.vo.ModelResult;
import com.devs.devs.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EngineController {

  @Autowired
  private EngineService engineService;

  @PostMapping("/execute")
  public ModelResult execute(@RequestBody ExecuteParam executeParam) {
    return engineService.executeRuleSet(executeParam);
  }

  @PostMapping("/executeDecisionTree")
  public ModelResult executeDecisionTree(@RequestBody ExecuteParam executeParam) {
    return engineService.executeDecisionTree(executeParam);
  }

}
