package com.devs.devs.method;

import com.googlecode.aviator.annotation.Import;
import com.googlecode.aviator.annotation.ImportScope;

import java.math.BigDecimal;

/**
 * 数字
 * <p>
 * EQ 就是 EQUAL等于
 * NE就是 NOT EQUAL不等于
 * GT 就是 GREATER THAN大于
 * LT 就是 LESS THAN小于
 * GE 就是 GREATER THAN OR EQUAL 大于等于
 * LE 就是 LESS THAN OR EQUAL 小于等于
 */
@Import(ns = "NumberMethod", scopes = {ImportScope.Static})
public class NumberMethod {
    public static Boolean equals(Object expect, Object fact) {
        BigDecimal e = new BigDecimal(String.valueOf(expect));
        BigDecimal f = new BigDecimal(String.valueOf(fact));
        return f.compareTo(e) == 0;
    }

    public static Boolean notEquals(Object expect, Object fact) {
        BigDecimal e = new BigDecimal(String.valueOf(expect));
        BigDecimal f = new BigDecimal(String.valueOf(fact));
        return f.compareTo(e) != 0;
    }

    public static Boolean gt(Object expect, Object fact) {
        BigDecimal e = new BigDecimal(String.valueOf(expect));
        BigDecimal f = new BigDecimal(String.valueOf(fact));
        return f.compareTo(e) > 0;
    }

    public static Boolean lt(Object expect, Object fact) {
        BigDecimal e = new BigDecimal(String.valueOf(expect));
        BigDecimal f = new BigDecimal(String.valueOf(fact));
        return f.compareTo(e) < 0;
    }

    public static Boolean ge(Object expect, Object fact) {
        BigDecimal e = new BigDecimal(String.valueOf(expect));
        BigDecimal f = new BigDecimal(String.valueOf(fact));
        return f.compareTo(e) >= 0;
    }

    public static Boolean le(Object expect, Object fact) {
        BigDecimal e = new BigDecimal(String.valueOf(expect));
        BigDecimal f = new BigDecimal(String.valueOf(fact));
        return f.compareTo(e) <= 0;
    }
}
