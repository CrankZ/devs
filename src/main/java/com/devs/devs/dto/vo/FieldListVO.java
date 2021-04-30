package com.devs.devs.dto.vo;

import com.devs.devs.dao.model.FieldDO;
import lombok.Data;

import java.util.List;

/**
 * 分页获取字段数据
 */
@Data
public class FieldListVO extends ListVO {
  private List<FieldDO> data;
}
