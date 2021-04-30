package com.devs.devs.dto.vo;

import com.devs.devs.consts.FieldSourceEnum;
import com.devs.devs.consts.RuleEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 可以弄个快照版本，用来存储当时执行的结果
 * 单次执行后，返回给前端的结果
 * TODO: 这个能不能叫模型？
 */
@Data
public class ModelResult {
  /**
   * 模型
   */
  private String model;
  /**
   * 字段真实的值
   * TODO: 通过参数 OR 通过取数
   * eg:
   * {
   *   "facts":{
   *     "param": {},
   *     "http": {}
   *   }
   * }
   */
  private Map<FieldSourceEnum, Map<String, Object>> facts;
  /**
   * 规则集列表，一个模型下面可能会有多个规则集
   */
  private Map<RuleEnum, List<RuleSetResult>> ruleResults;
}
