package com.devs.devs.dto.vo;

import com.devs.devs.dao.model.RuleInfoDO;
import lombok.Data;

import java.util.List;

/**
 * 分页获取规则集数据
 */
@Data
public class RuleSetListVO extends ListVO {
  private List<RuleInfoDO> data;
}
