package com.devs.devs.controller;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.rule.CreateRuleRequest;
import com.devs.devs.dto.rule.CreateRuleResponse;
import com.devs.devs.dto.rule.QueryRuleRequest;
import com.devs.devs.dto.rule.QueryRuleResponse;
import com.devs.devs.rule.serivce.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 规则集
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private IRuleService ruleSetService;

    @PostMapping("/create")
    public ResultEntity<CreateRuleResponse> create(@RequestBody CreateRuleRequest request) {
        return ruleSetService.createRule(request);
    }

    @GetMapping("/query")
    public ResultEntity<QueryRuleResponse> query(QueryRuleRequest request) {
        return ruleSetService.queryRule(request);
    }
}
