package com.devs.devs.method;

import com.googlecode.aviator.annotation.Import;
import com.googlecode.aviator.annotation.ImportScope;

/**
 * 如果要用aviator进行注册，这个注解是必须的
 */
@Import(ns = "ListMethod", scopes = {ImportScope.Static})
public class ListMethod {
  /**
   *
   */
  public static void in() {

  }

  public static void notIn() {

  }
}
