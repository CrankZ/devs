package com.devs.devs.jsonMapper.action;

import lombok.Data;

import java.util.List;

/**
 * @author 松梁
 * @date 2021/7/16
 */
@Data
public class Conditions {
    private List<Condition> and;
    private List<Condition> or;
}
