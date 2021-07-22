package com.devs.devs.exception;

import lombok.Data;

/**
 * 数据不存在
 *
 * @author 松梁
 * @date 2021/7/9
 */
@Data
public class NoRecordException extends RuntimeException {

    public NoRecordException() {
        super();
    }

    public NoRecordException(String message) {
        super(message);
    }

    public NoRecordException(String message, Throwable t) {
        super(message, t);
    }

    public NoRecordException(Throwable t) {
        super(t);
    }
}