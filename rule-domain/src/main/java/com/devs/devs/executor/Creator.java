package com.devs.devs.executor;

import com.devs.devs.method.NumberMethod;
import com.devs.devs.method.StringMethod;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;

/**
 * 注册方法
 * 创建执行器
 *
 * @author 松梁
 * @date 2021/7/22
 */
public class Creator {
    private static AviatorEvaluatorInstance engine = AviatorEvaluator.newInstance();

    static {
        try {
            engine.importFunctions(StringMethod.class);
            engine.importFunctions(NumberMethod.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AviatorEvaluatorInstance getEngine() {
        return Creator.engine;
    }
}
