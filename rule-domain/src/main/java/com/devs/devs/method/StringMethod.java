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
     * 为空
     *
     * @param a
     * @return
     */
    public static boolean empty(String a) {
        return a.isEmpty();
    }

    /**
     * 不为空
     *
     * @param a
     * @return
     */
    public static boolean notEmpty(String a) {
        return !empty(a);
    }

    /**
     * 相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        return a.equals(b);
    }

    public static boolean notEquals(String a, String b) {
        return !equals(a, b);
    }

    /**
     * 包含
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean contains(String a, String b) {
        return a.contains(b);
    }

    /**
     * 不包含
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean notContains(String a, String b) {
        return a.contains(b);
    }

    /**
     * 以...开始
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean startWith(String a, String b) {
        return a.startsWith(b);
    }

    /**
     * 不以...开始
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean notStartWith(String a, String b) {
        return !startWith(a, b);
    }

    /**
     * 在列表...中
     *
     * @param a
     * @param list
     * @return
     */
    public static boolean inList(String a, List<String> list) {
        return list.contains(a);
    }

    /**
     * 不在列表...中
     *
     * @param a
     * @param list
     * @return
     */
    public static boolean notInList(String a, List<String> list) {
        return list.contains(a);
    }
}
