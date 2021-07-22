package com.devs.devs.jsonMapper;

import lombok.Data;

/**
 * 规则执行后的结果
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
public class TriggerResult {
    /**
     * 是否触发
     */
    private Boolean trigger;
}
