package com.devs.devs.exception;

/**
 * 远程HTTP调用异常
 *
 * @author 松梁
 * @date 2021/7/16
 */
public class RemoteException extends RuntimeException {

    public RemoteException() {
        super();
    }

    public RemoteException(String message) {
        super(message);
    }

    public RemoteException(String message, Throwable t) {
        super(message, t);
    }

    public RemoteException(Throwable t) {
        super(t);
    }

}
