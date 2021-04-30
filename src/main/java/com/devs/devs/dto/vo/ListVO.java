package com.devs.devs.dto.vo;

import lombok.Data;

/**
 * 分页公共类
 */
@Data
public abstract class ListVO {
  /**
   * 当前页
   */
  private Integer current;
  /**
   * 分页大小
   */
  private Integer pageSize;
  /**
   * 总数量
   */
  private Integer total;
  /**
   * 是否成功
   */
  private Boolean success;
}
