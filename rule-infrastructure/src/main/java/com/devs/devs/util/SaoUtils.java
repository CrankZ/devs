package com.devs.devs.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.devs.devs.exception.RemoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Configuration
public class SaoUtils {

    @Autowired
    @Qualifier("commonRestTemplate")
    private RestTemplate commonRestTemplate;

    public <T> T getForObject(String url, Map<String, Object> params, Class<T> clazz) {
        Map<String, Object> recvData;
        try {
            recvData = commonRestTemplate.getForObject(url, Map.class, params);
        } catch (Exception e) {
            throw new RemoteException("网络异常");
        }
        String dataJson = JSON.toJSONString(recvData);
        return JSON.parseObject(dataJson, clazz);
    }

    public <T> T postForObject(String url, Map<String, Object> params, Class<T> clazz) {
        Map<String, Object> recvData;
        try {
            recvData = commonRestTemplate.postForObject(url, params, Map.class);
        } catch (Exception e) {
            throw new RemoteException("网络异常", e);
        }
        String dataJson = JSON.toJSONString(recvData);
        return JSON.parseObject(dataJson, clazz);
    }

    public <T> List<T> postForObjectList(String url, Map<String, Object> params, Class<T> clazz) {
        Map<String, Object> recvData;
        try {
            recvData = commonRestTemplate.postForObject(url, params, Map.class);
        } catch (Exception e) {
            throw new RemoteException("网络异常");
        }
        String dataJson = JSON.toJSONString(recvData);
        return JSON.parseArray(dataJson, clazz);
    }

    public JSONObject httpRequest(String url, String reqType, String... args) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONObject> responseEntity = commonRestTemplate.exchange(url, HttpMethod.valueOf(reqType), entity, JSONObject.class, args);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return null;
    }
}
