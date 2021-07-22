package com.devs.devs.controller;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.rule.CreateRuleResponse;
import com.devs.devs.dto.rule.QueryRuleResponse;
import com.devs.devs.dto.tree.CreateTreeRequest;
import com.devs.devs.dto.tree.QueryTreeRequest;
import com.devs.devs.tree.service.IDecisionTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 决策树
 */
@RestController
@RequestMapping("/decisionTree")
public class DecisionTreeController {

    @Autowired
    private IDecisionTreeService decisionTreeService;

    @PostMapping("/create")
    public ResultEntity<CreateRuleResponse> create(@RequestBody CreateTreeRequest request) {
        return decisionTreeService.createTree(request);
    }

    @GetMapping("/query")
    public ResultEntity<QueryRuleResponse> query(QueryTreeRequest request) {
        return decisionTreeService.queryTree(request);
    }
}
