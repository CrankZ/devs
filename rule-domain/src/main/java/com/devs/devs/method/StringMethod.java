package com.devs.devs.method;

import com.googlecode.aviator.annotation.Import;
import com.googlecode.aviator.annotation.ImportScope;

import java.util.List;

/**
 * 如果要用aviator进行注册，这个注解是必须的
 */
@Import(ns = "StringMethod", scopes = {ImportScope.Static})
public class StringMethod {
    /**
     * 直接输出内容
     *
     * @return
     */
    public static Boolean print() {
        return null;
    }

    /**
     * 为空
     *
     * @param fact
     * @return
     */
    public static Boolean isEmpty(String fact) {
        return fact.isEmpty();
    }

    /**
     * 不为空
     *
     * @param fact
     * @return
     */
    public static Boolean isNotEmpty(String fact) {
        return !isEmpty(fact);
    }

    /**
     * 相等
     */
    public static Boolean equals(String expect, String fact) {
        return expect.equals(fact);
    }

    public static Boolean notEquals(String expect, String fact) {
        return !equals(expect, fact);
    }

    /**
     * 包含
     *
     * @param a
     * @param b
     * @return
     */
    public static Boolean contains(String a, String b) {
        return a.contains(b);
    }

    /**
     * 不包含
     *
     * @param a
     * @param b
     * @return
     */
    public static Boolean notContains(String a, String b) {
        return a.contains(b);
    }

    /**
     * 以...开始
     *
     * @param a
     * @param b
     * @return
     */
    public static Boolean startWith(String a, String b) {
        return a.startsWith(b);
    }

    /**
     * 不以...开始
     *
     * @param a
     * @param b
     * @return
     */
    public static Boolean notStartWith(String a, String b) {
        return !startWith(a, b);
    }

    /**
     * 在列表...中
     *
     * @param a
     * @param list
     * @return
     */
    public static Boolean inList(String a, List<String> list) {
        return list.contains(a);
    }

    /**
     * 不在列表...中
     *
     * @param a
     * @param list
     * @return
     */
    public static Boolean notInList(String a, List<String> list) {
        return list.contains(a);
    }
}
