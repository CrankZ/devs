package com.devs.devs.dao.model;

import com.devs.devs.consts.RuleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * rule_info
 *
 * @author
 */
@Data
public class RuleInfoDO implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private Long id;

  private Date createTime;

  private Date updateTime;

  /**
   * 规则编码
   */
  private String code;

  /**
   * 规则名
   */
  private String name;

  /**
   * 规则类型
   * RULE_SET:规则组
   * DECISION_TREE:决策树
   * SCORE_CAR:评分卡
   *
   * @see RuleEnum
   */
  private String type;

  /**
   * 规则所属模型
   * 用来给规则分类
   */
  private String modelId;

  /**
   * 结构体
   */
  private String json;

  /**
   * 规则描述
   */
  private String desc;

}