package com.devs.devs;

import com.devs.devs.enums.ResultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultEntity<T> {

    public static final ResultEntity NULL = null;
    /**
     * 请求流水号
     */
    private String requestId;
    /**
     * 全链路traceId
     */
    private String traceId;
    /**
     * 返回码
     * 0000-成功
     * 其他-失败
     */
    private String code;
    /**
     * 返回处理结果
     */
    private String msg;
    /**
     * 时间戳
     */
    private Long timestamp = System.currentTimeMillis();
    /**
     * 返回数据
     */
    private T data;

    public static ResultEntity buildSuccess(String requestId) {
        return new ResultEntity()
                .withRequestId(requestId)
                .withCode(ResultStatusEnum.SUCCESS.getCode())
                .withMsg(ResultStatusEnum.SUCCESS.getMessage());
    }

    public static ResultEntity of(String requestId, String code, String message) {
        return new ResultEntity()
                .withRequestId(requestId)
                .withCode(code)
                .withMsg(message);
    }

    public static ResultEntity of(String requestId, ResultStatusEnum resultStatusEnum) {
        return new ResultEntity()
                .withRequestId(requestId)
                .withCode(resultStatusEnum.getCode())
                .withMsg(resultStatusEnum.getMessage());
    }

    public static ResultEntity buildParameterMissing(String requestId, String parameterName) {
        return new ResultEntity()
                .withRequestId(requestId)
                .withCode(ResultStatusEnum.PARAMETER_MISSING.getCode())
                .withMsg(ResultStatusEnum.PARAMETER_MISSING.getFullMessage(parameterName));
    }
}