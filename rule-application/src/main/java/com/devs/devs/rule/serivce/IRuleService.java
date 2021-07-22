package com.devs.devs.rule.serivce;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.rule.CreateRuleRequest;
import com.devs.devs.dto.rule.QueryRuleRequest;
import com.devs.devs.dto.rule.UpdateRuleRequest;
import com.devs.devs.dto.rule.UpdateStatusRuleRequest;

public interface IRuleService {

    /**
     * 创建插件
     *
     * @param request
     * @return
     */
    public ResultEntity createRule(CreateRuleRequest request);

    /**
     * 更新插件R
     *
     * @param request
     * @return
     */
    public ResultEntity updateRule(UpdateRuleRequest request);

    /**
     * 更新插件状态
     *
     * @param request
     * @return
     */
    public ResultEntity updateRuleStatus(UpdateStatusRuleRequest request);

    /**
     * 查询插件
     *
     * @param request
     * @return
     */
    public ResultEntity queryRule(QueryRuleRequest request);
}