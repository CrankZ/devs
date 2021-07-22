package com.devs.devs.exception;

import lombok.Data;

/**
 * 当前场景下无规则
 *
 * @author 松梁
 * @date 2021/7/9
 */
@Data
public class NoRuleException extends RuntimeException {

    public NoRuleException() {
        super();
    }

    public NoRuleException(String message) {
        super(message);
    }

    public NoRuleException(String message, Throwable t) {
        super(message, t);
    }

    public NoRuleException(Throwable t) {
        super(t);
    }

}