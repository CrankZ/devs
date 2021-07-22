package com.devs.devs.jsonMapper.action;

import lombok.Data;

import java.util.Map;

@Data
public class ActionHttp {
    private String url;
    private String method;
    private Map<String, Object> params;
}
