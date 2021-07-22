package com.devs.devs.valueobject;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 规则执行结果基础类
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
@SuperBuilder
public class BaseResult {
    /**
     * 规则执行值
     */
    private Map<String, Object> facts;
}
