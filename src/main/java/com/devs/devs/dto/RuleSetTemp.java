package com.devs.devs.dto;

import com.devs.devs.dao.model.FieldDO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 规则集
 */
@Data
public class RuleSetTemp {

  private String id;
  @Deprecated
  private String code;
  private String name;

  /**
   * 表达式
   * 如果是规则集，那么就只是与或组成的以为结构  String.equals(a,b) || c > d && Integer.max(e,f) > 100
   */
  private String expression;
  /**
   * 当前规则集下所有的规则
   * 规则的组合只有与或
   */
  private List<FieldDO> fieldList;

  /**
   * 用户自定义结构体Id
   */
  private String customResultId;

  /**
   * 执行结果
   */
  private Boolean result;

  private Map<String, CustomResult> customResultMap;

}
