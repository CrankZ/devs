package com.devs.devs.dao;

import com.devs.devs.dao.model.RuleResultDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleResultMapper {
  int deleteByPrimaryKey(Long id);

  int insert(@Param("requestId") String requestId, @Param("modelId") String modelId,
             @Param("ruleType") String ruleType, @Param("ruleResult") String ruleResult);

  int insertSelective(RuleResultDO record);

  RuleResultDO selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(RuleResultDO record);

  int updateByPrimaryKey(RuleResultDO record);
}