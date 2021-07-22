package com.devs.devs.domain.executor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.valueobject.RuleRecordDO;
import com.devs.devs.valueobject.RuleResultDO;
import com.devs.devs.enums.ActionEnum;
import com.devs.devs.enums.RuleTypeEnum;
import com.devs.devs.jsonMapper.action.Action;
import com.devs.devs.jsonMapper.action.ActionHttp;
import com.devs.devs.exception.RemoteException;
import com.devs.devs.executor.Creator;
import com.devs.devs.util.SaoUtils;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author 松梁
 * @date 2021/7/16
 */
@SuperBuilder
public class BaseExecutor {

    public static final AviatorEvaluatorInstance engine = Creator.getEngine();
    public RuleRecordDO ruleRecordDO;

    public RuleResultDO ruleResultDO;
    private ThreadPoolExecutor asyncExecutor;
    private RuleInfoDO ruleInfoDO;
    private SaoUtils saoUtils;

    /**
     * 保存入参
     */
    public void saveRecord() {
        asyncExecutor.execute(() -> ruleRecordDO.insert());
    }

    /**
     * 保存结果
     *
     * @param ruleResultList
     */
    public void saveResult(Object ruleResultList) {
        ruleResultDO.setRuleResult(JSON.toJSONString(ruleResultList));
        asyncExecutor.execute(() -> ruleResultDO.insert());
    }

    /**
     * 获得当前场景下的所有规则
     *
     * @return
     */
    public List<RuleInfoDO> getRuleInfoList(RuleTypeEnum ruleTypeEnum) {
        LambdaQueryWrapper<RuleInfoDO> wrapper = new LambdaQueryWrapper<RuleInfoDO>()
                .eq(RuleInfoDO::getSceneId, ruleRecordDO.getSceneId())
                .eq(RuleInfoDO::getRuleType, ruleTypeEnum.name());

        return ruleInfoDO.selectList(wrapper);
    }

    /**
     * 触发器
     *
     * @param actions
     */
    public void action(List<Action> actions) {
        for (Action action : actions) {
            String type = action.getType();
            Object value = action.getValue();
            action.setTrigger(true);

            if (ActionEnum.PRINT.name().equals(type)) {
                action.setResult(value);
            } else {
                ActionHttp actionHttp = JSON.parseObject(JSON.toJSONString(action.getValue()), ActionHttp.class);
                String method = actionHttp.getMethod();
                String url = actionHttp.getUrl();
                Map<String, Object> params = actionHttp.getParams();

                Map<String, Object> httpResult;
                try {
                    if (HttpMethod.GET.equals(method)) {
                        httpResult = saoUtils.getForObject(url, params, Map.class);
                    } else {
                        httpResult = saoUtils.postForObject(url, params, Map.class);
                    }
                } catch (Exception e) {
                    action.setTrigger(false);
                    httpResult = null;
                    throw new RemoteException();
                }

                action.setResult(httpResult);
            }
        }
    }
}
