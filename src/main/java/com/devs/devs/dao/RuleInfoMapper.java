package com.devs.devs.dao;

import com.devs.devs.controller.params.RuleSetListParams;
import com.devs.devs.dao.model.RuleInfoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleInfoMapper {
  int deleteByPrimaryKey(Long id);

  int insert(RuleInfoDO record);

  int insertSelective(RuleInfoDO record);

  RuleInfoDO selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(RuleInfoDO record);

  int updateByPrimaryKey(RuleInfoDO record);

  List<RuleInfoDO> selectByModelId(@Param("modelId") String modelId, @Param("ruleType") String ruleType);

  List<RuleInfoDO> list(RuleSetListParams params);
}