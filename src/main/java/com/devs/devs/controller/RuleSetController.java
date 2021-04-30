package com.devs.devs.controller;

import com.devs.devs.controller.params.RuleSetBoolParams;
import com.devs.devs.controller.params.RuleSetListParams;
import com.devs.devs.controller.params.RuleSetParams;
import com.devs.devs.dto.vo.RuleSetListVO;
import com.devs.devs.service.RuleSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 规则集
 */
@RestController
@RequestMapping("/ruleSet")
public class RuleSetController {

  @Autowired
  private RuleSetService ruleSetService;

  @PostMapping("/add")
  public void add(@RequestBody RuleSetParams params) {
    ruleSetService.add(params);
  }

  @PostMapping("/addBool")
  public void addBool(@RequestBody RuleSetBoolParams params) {
    ruleSetService.addBool(params);
  }

  /**
   * 分页获取规则集
   *
   * @param params
   * @return
   * @see FieldController#list(com.devs.devs.controller.params.FieldListParams)
   */
  @PostMapping("/list")
  public RuleSetListVO list(@RequestBody RuleSetListParams params) {
    return ruleSetService.list(params);
  }
}
