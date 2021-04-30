package com.devs.devs.executor;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.Map;

/**
 * 规则执行器
 * TODO: 线程安全的问题，不过本身应该就是单例的，所以应该不存在线程安全的问题
 */
public class Executor {
  /**
   * @param expression eg: "StringMethod.equals(a,b)"
   * @param env        eg: map.put("a", "hello"); map.put("b", "hello");
   * @return TODO: 可能需要手动捕获异常
   */
  public static Object execute(String expression, Map<String, Object> env) {
    return AviatorEvaluator.execute(expression, env);
  }
}
