package com.devs.devs.controller;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.engine.RuleEngineExecuteRequest;
import com.devs.devs.dto.engine.RuleEngineExecuteResponse;
import com.devs.devs.engine.service.IEngineService;
import com.devs.devs.enums.ResultStatusEnum;
import com.devs.devs.exception.RemoteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 规则执行接口
 *
 * @author 松梁
 * @date 2021/7/22
 */
@Slf4j
@RestController
@RequestMapping("/engine")
public class ExecuteController {

    @Autowired
    private IEngineService engineService;

    /**
     * 执行规则
     *
     * @param request
     * @return
     */
    @PostMapping("/execute")
    public ResultEntity<RuleEngineExecuteResponse> execute(@RequestBody RuleEngineExecuteRequest request) {
        try {
            return engineService.executeRule(request);
        } catch (RemoteException e) {
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.SAO_NETWORK_FAIL);
        } catch (Throwable e) {
            e.printStackTrace();
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.SYSTEM_INTERNAL_ERROR);
        }
    }

}
