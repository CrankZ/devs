package com.devs.devs.exception;

import lombok.Data;

/**
 * 数据重复
 *
 * @author 松梁
 * @date 2021/7/9
 */
@Data
public class DuplicateException extends RuntimeException {

    public DuplicateException() {
        super();
    }

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable t) {
        super(message, t);
    }

    public DuplicateException(Throwable t) {
        super(t);
    }
}