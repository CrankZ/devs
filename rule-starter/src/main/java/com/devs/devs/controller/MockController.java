package com.devs.devs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockController {

    @PostMapping("/action1")
    public Map<String, Object> action1() {
        Map<String, Object> map = new HashMap<>();
        map.put("结果", "成功");
        return map;
    }

    @GetMapping("/action2")
    public Map<String, Object> action1(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.putAll(params);
        map.put("结果", "成功");
        return map;
    }

    @GetMapping("/fakeProvince")
    public String fakeProvince() {
        return "[{\"nameX\":\"北京市\",\"idX\":\"bj\"},{\"nameX\":\"天津市\",\"idX\":\"tj\"}]";
    }

    @GetMapping("/fakeField")
    public String fakeField() {
        return "[{\"fieldCode\":\"1\",\"fieldName\":\"年龄\"},{\"fieldCode\":\"2\",\"fieldName\":\"姓名\"}]";
    }

}
