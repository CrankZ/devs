package com.devs.devs.factor;

import com.alibaba.fastjson.JSON;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.valueobject.RuleRecordDO;
import com.devs.devs.valueobject.RuleResultDO;
import com.devs.devs.domain.executor.RuleExecutor;
import com.devs.devs.domain.executor.TreeExecutor;
import com.devs.devs.enums.RuleTypeEnum;
import com.devs.devs.dto.engine.ExecuteRequest;
import com.devs.devs.util.SaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 松梁
 * @date 2021/6/27
 */
@Component
public class EngineFactory {
    @Autowired
    private SaoUtils saoUtils;

    @Autowired
    @Qualifier("asyncExecutor")
    private ThreadPoolExecutor asyncExecutor;

    public RuleExecutor create(ExecuteRequest request) {
        String requestId = request.getRequestId();
        Long sceneId = request.getSceneId();

        RuleRecordDO ruleRecordDO = new RuleRecordDO()
                .setRequestId(requestId)
                .setSceneId(sceneId)
                .setParam(JSON.toJSONString(request.getFacts()));

        RuleResultDO ruleResultDO = new RuleResultDO()
                .setRequestId(requestId)
                .setSceneId(sceneId)
                .setRuleType(RuleTypeEnum.SIMPLE.name());

        RuleInfoDO ruleInfoDO = new RuleInfoDO();

        return RuleExecutor.builder()
                .ruleRecordDO(ruleRecordDO)
                .ruleResultDO(ruleResultDO)
                .ruleInfoDO(ruleInfoDO)

                .saoUtils(saoUtils)
                .asyncExecutor(asyncExecutor)
                .build();
    }

    public TreeExecutor createTree(ExecuteRequest request) {
        String requestId = request.getRequestId();
        Long sceneId = request.getSceneId();

        RuleRecordDO ruleRecordDO = new RuleRecordDO()
                .setRequestId(requestId)
                .setSceneId(sceneId)
                .setParam(JSON.toJSONString(request.getFacts()));

        RuleResultDO ruleResultDO = new RuleResultDO()
                .setRequestId(requestId)
                .setSceneId(sceneId)
                .setRuleType(RuleTypeEnum.DECISION_TREE.name());

        RuleInfoDO ruleInfoDO = new RuleInfoDO();

        return TreeExecutor.builder()
                .ruleRecordDO(ruleRecordDO)
                .ruleResultDO(ruleResultDO)
                .ruleInfoDO(ruleInfoDO)

                .saoUtils(saoUtils)
                .asyncExecutor(asyncExecutor)
                .build();
    }
}
