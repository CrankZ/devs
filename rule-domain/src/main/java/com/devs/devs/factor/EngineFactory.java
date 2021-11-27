package com.devs.devs.factor;

import com.devs.devs.domain.executor.RuleExecutor;
import com.devs.devs.dto.engine.RuleEngineExecuteRequest;
import com.devs.devs.executor.Creator;
import com.devs.devs.util.SpringRestTemplate;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author 松梁
 * @date 2021/6/27
 */
@Component
public class EngineFactory {
    @Autowired
    private SpringRestTemplate springRestTemplate;

    @Autowired
    private ApplicationContext applicationContext;
    public static final AviatorEvaluatorInstance engine = Creator.getEngine();

    public RuleExecutor create(RuleEngineExecuteRequest request) {
        return RuleExecutor.builder()
                .templateId(request.getTemplateId())
                .params(request.getParams())
                .applicationContext(applicationContext)
                .springRestTemplate(springRestTemplate)
                .engine(engine)
                .build();
    }

}
