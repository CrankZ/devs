package com.devs.devs.method2;

import com.devs.devs.method.StringMethod;
import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AviatorTest {


    /**
     * 导入 java 方法
     *
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @link {https://www.yuque.com/boyan-avfmj/aviatorscript/xbdgg2#KD1gm}
     */
    @Test
    public void qq() throws NoSuchMethodException, IllegalAccessException {
        AviatorEvaluator.addStaticFunctions("StringMethod", StringMethod.class);
        String script = "StringMethod.equals($a,$b)";
        Map<String, Object> map = new HashMap<>();
        map.put("$a", "hello");
        map.put("$b", "hello");
        System.out.println(AviatorEvaluator.execute(script, map));
    }

    /**
     * 批量导入方法和注解支持
     *
     * @link {https://www.yuque.com/boyan-avfmj/aviatorscript/xbdgg2#fb6945bc}
     * 目前看这个方法不错
     */
    @Test
    public void importFunctions() throws NoSuchMethodException, IllegalAccessException {
        AviatorEvaluator.importFunctions(StringMethod.class);
        String script = "StringMethod.equals(a,b)";
        Map<String, Object> map = new HashMap<>();
        map.put("a", "hello");
        map.put("b", "hello");
        System.out.println(AviatorEvaluator.execute(script, map));
    }

    /**
     * 使用 Java 自定义模块
     *
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @link {https://www.yuque.com/boyan-avfmj/aviatorscript/mf0e8o}
     */
    @Test
    public void addModule() throws NoSuchMethodException, IllegalAccessException {
        AviatorEvaluator.getInstance().addModule(StringMethod.class);
        String script = "let str = require('str'); StringMethod.equals($a,$b) ";
        Map<String, Object> map = new HashMap<>();
        map.put("$a", "hello");
        map.put("$b", "hello");
        Map<String, Object> map2 = AviatorEvaluator.newEnv("$a", "hello", "$b", "hello");
        System.out.println(AviatorEvaluator.execute(script, map));
    }
}
