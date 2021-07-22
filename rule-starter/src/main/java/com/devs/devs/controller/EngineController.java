package com.devs.devs.controller;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.engine.ExecuteRequest;
import com.devs.devs.dto.engine.ExecuteRuleResponse;
import com.devs.devs.engine.service.IEngineService;
import com.devs.devs.enums.ResultStatusEnum;
import com.devs.devs.exception.RemoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 规则执行接口
 *
 * @author 松梁
 * @date 2021/7/22
 */
@RestController
public class EngineController {

    @Autowired
    private IEngineService engineService;

    @PostMapping("/execute")
    public ResultEntity<ExecuteRuleResponse> execute(@RequestBody ExecuteRequest request) {
        try {
            return engineService.executeRule(request);
        } catch (RemoteException e) {
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.SAO_NETWORK_FAIL);
        } catch (Throwable e) {
            e.printStackTrace();
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.SYSTEM_INTERNAL_ERROR);
        }
    }

    @PostMapping("/executeDecisionTree")
    public ResultEntity<ExecuteRuleResponse> executeDecisionTree(@RequestBody ExecuteRequest request) {
        return engineService.executeDecisionTree(request);
    }

}
