package com.devs.devs.dto;

import com.devs.devs.consts.FieldEnum;
import com.devs.devs.consts.FieldSourceEnum;
import lombok.Data;

/**
 * 字段
 */
@Data
public class Field {
  private String id;
  /**
   * 用户自定义，推荐使用驼峰风格
   */
  private String fieldCode;
  /**
   * 字段名称
   */
  private String fieldName;
  /**
   * 字段描述
   */
  private String desc;

  /**
   * 取数类型,get名称冲突
   * 入参，调用接口的时候直接传进来
   * HTTP
   */
  private FieldSourceEnum source;
  /**
   * 取数地址
   * 如果取数方法为HTTP，这个才有用
   */
  private String path;
  /**
   * TODO: 字段分类
   * 字符串或者数字
   */
  private FieldEnum type;

  /**
   * 阈值
   */
  private Object value;

  /**
   * 真实的值
   */
  private Object fact;


}
