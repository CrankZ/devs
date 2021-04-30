package com.devs.devs.service;

import com.devs.devs.controller.params.RuleSetBoolParams;
import com.devs.devs.controller.params.RuleSetListParams;
import com.devs.devs.controller.params.RuleSetParams;
import com.devs.devs.dto.vo.RuleSetListVO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface RuleSetService {
  void addRule(String expression, Map<String, Object> env, String comment);

  void add(RuleSetParams params);

  void addBool(RuleSetBoolParams params);

  RuleSetListVO list(RuleSetListParams params);
}
