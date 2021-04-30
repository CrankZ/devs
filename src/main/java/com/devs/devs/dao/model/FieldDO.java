package com.devs.devs.dao.model;

import com.devs.devs.consts.FieldEnum;
import com.devs.devs.consts.FieldSourceEnum;
import lombok.Data;

import java.util.Date;

/**
 * 严格与数据库对应
 */
@Data
public class FieldDO {

  private String field;

  private Boolean success;

  private String label;

  private Object value;

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private Long id;

  private Date createTime;

  private Date updateTime;

  /**
   * 字段编码
   */
  private String code;

  /**
   * 字段名
   */
  private String name;

  /**
   * 字段类型，1.字符串2.数字；STRING,NUMBER
   *
   * @see FieldEnum
   */
  private String type;

  /**
   * 字段来源1.入参 2.HTTP取数
   *
   * @see FieldSourceEnum
   */
  private String source;

  /**
   * 取数路径
   */
  private String path;

  /**
   * 描述
   */
  private String desc;

}