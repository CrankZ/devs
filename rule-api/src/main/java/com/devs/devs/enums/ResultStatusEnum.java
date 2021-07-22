package com.devs.devs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 松梁
 * @date 2021/7/22
 */
@Getter
@AllArgsConstructor
public enum ResultStatusEnum {

    /**
     * 成功
     */
    SUCCESS("0000", "success"),

    /**
     * 参数缺失
     */
    PARAMETER_MISSING("2001", "参数缺失[%s]"),

    /**
     * 重复提交
     */
    REPEATED_SUBMISSION("2002", "重复提交"),

    /**
     * 账号未登录
     */
    ACCOUNT_NOT_LOGIN("4101", "登录已失效，请重新登录"),

    /**
     * 账户不存在
     */
    ACCOUNT_NOT_FOUND("4102", "账户不存在"),

    /**
     * 数据不存在
     */
    RECORD_NOT_FOUND("4103", "数据不存在"),

    /**
     * 重复数据
     */
    CODE_VERSION_DUPLICATE("4104", "code重复"),

    /**
     *
     */
    SAO_NETWORK_FAIL("4105", "SAO网络调用异常"),

    /**
     * 系统内部错误
     */
    SYSTEM_INTERNAL_ERROR("5000", "系统内部错误"),

    /**
     * 其它原因
     */
    OTHER("", "");

    /**
     * 状态码
     */
    @Setter
    private String code;

    /**
     * 状态消息
     */
    @Setter
    private String message;

    /**
     * @param parameterName 参数名称
     * @return
     */
    public String getFullMessage(String parameterName) {
        return String.format(message, parameterName);
    }

}