package com.devs.devs.method;

/**
 * 规则引擎的方法
 */
public interface Method {
  Object eval(Object left, Object right);
}
