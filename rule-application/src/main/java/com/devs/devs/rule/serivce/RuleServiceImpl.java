package com.devs.devs.rule.serivce;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.valueobject.RuleInfoV;
import com.devs.devs.domain.Rule;
import com.devs.devs.ResultEntity;
import com.devs.devs.dto.rule.CreateRuleRequest;
import com.devs.devs.dto.rule.CreateRuleResponse;
import com.devs.devs.dto.rule.QueryRuleRequest;
import com.devs.devs.dto.rule.QueryRuleResponse;
import com.devs.devs.dto.rule.UpdateRuleRequest;
import com.devs.devs.dto.rule.UpdateRuleResponse;
import com.devs.devs.dto.rule.UpdateStatusRuleRequest;
import com.devs.devs.dto.rule.UpdateStatusRuleResponse;
import com.devs.devs.factor.RuleFactory;
import com.devs.devs.mapstruct.mapper.RuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RuleServiceImpl implements IRuleService {

    @Autowired
    private RuleFactory ruleFactory;

    @Override
    public ResultEntity createRule(CreateRuleRequest request) {
        Rule rule = ruleFactory.create(request);
        long ruleId = rule.save();
        CreateRuleResponse response = CreateRuleResponse.builder().ruleId(ruleId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity updateRule(UpdateRuleRequest request) {
        Rule rule = ruleFactory.create(request);
        long ruleId = rule.update();
        UpdateRuleResponse response = UpdateRuleResponse.builder().ruleId(ruleId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity updateRuleStatus(UpdateStatusRuleRequest request) {
        Rule rule = ruleFactory.create(request);

        long ruleId = rule.updateStatus();
        UpdateStatusRuleResponse response = UpdateStatusRuleResponse.builder().ruleId(ruleId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity queryRule(QueryRuleRequest request) {
        Rule rule = ruleFactory.create(request);

        IPage<RuleInfoDO> page = rule.query(request.getPageNo(), request.getPageSize());
        List<RuleInfoDO> records = page.getRecords();
        List<RuleInfoV> vList = RuleMapper.INSTANCE.do2vList(records);
        QueryRuleResponse response = QueryRuleResponse.builder()
                .list(vList)
                .totalCount(page.getTotal())
                .totalPages(page.getPages())
                .build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }
}