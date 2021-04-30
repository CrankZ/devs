package com.devs.devs.executor;

import com.devs.devs.method.StringMethod;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;

/**
 * 注册方法
 * 创建执行器
 * TODO: 执行用这种方法可能会有线程安全的问题，也许需要手动创建实例？但是执行器需要单例吗？
 */
public class Creator {

  private static AviatorEvaluatorInstance engine = AviatorEvaluator.newInstance();

  static {
    try {
      engine.importFunctions(StringMethod.class);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  public static AviatorEvaluatorInstance getEngine() {
    return Creator.engine;
  }
}
