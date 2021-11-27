package com.devs.devs.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.devs.devs.exception.RemoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author 松梁
 * @date 2021/11/27
 */
@Component
public class SpringRestTemplate {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    public <T> T getForObject(String url, Map<String, Object> params, Class<T> clazz) {
        Map<String, Object> recvData;
        try {
            recvData = restTemplate.getForObject(url, Map.class, params);
        } catch (Exception e) {
            throw new RemoteException("网络异常");
        }
        String dataJson = JSON.toJSONString(recvData);
        return JSON.parseObject(dataJson, clazz);
    }

    public <T> T postForObject(String url, Map<String, Object> params, Class<T> clazz) {
        Map<String, Object> recvData;
        try {
            recvData = restTemplate.postForObject(url, params, Map.class);
        } catch (Exception e) {
            throw new RemoteException("网络异常", e);
        }
        String dataJson = JSON.toJSONString(recvData);
        return JSON.parseObject(dataJson, clazz);
    }

    public <T> List<T> postForObjectList(String url, Map<String, Object> params, Class<T> clazz) {
        Map<String, Object> recvData;
        try {
            recvData = restTemplate.postForObject(url, params, Map.class);
        } catch (Exception e) {
            throw new RemoteException("网络异常");
        }
        String dataJson = JSON.toJSONString(recvData);
        return JSON.parseArray(dataJson, clazz);
    }

    public JSONObject httpRequest(String url, String reqType, String... args) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.valueOf(reqType), entity, JSONObject.class, args);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return null;
    }
}
