package com.devs.devs.dao;

import com.devs.devs.dao.model.RuleRecordDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRecordMapper {
  int deleteByPrimaryKey(Long id);

  int insert(@Param("requestId") String requestId, @Param("modelId") String modelId, @Param("param") String param);

  int insertSelective(RuleRecordDO record);

  RuleRecordDO selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(RuleRecordDO record);

  int updateByPrimaryKey(RuleRecordDO record);
}