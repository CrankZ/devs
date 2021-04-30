package com.devs.devs.dao;


import com.devs.devs.controller.params.FieldListParams;
import com.devs.devs.dao.model.FieldDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldMapper {
  int deleteByPrimaryKey(Long id);

  int insert(FieldDO record);

  int insertSelective(FieldDO record);

  FieldDO selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(FieldDO record);

  int updateByPrimaryKey(FieldDO record);

  int updateByCode(FieldDO record);

  List<FieldDO> getFieldList(List<String> codeList);

  FieldDO selectByName(String name);

  List<FieldDO> selectAll();

  FieldDO selectByCode(String code);

  List<FieldDO> selectByParams(FieldListParams params);

  int deleteByCode(String code);
}
