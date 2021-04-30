package com.devs.devs.service;

import com.devs.devs.controller.params.FieldListParams;
import com.devs.devs.dao.model.FieldDO;
import com.devs.devs.dto.vo.FieldListVO;
import com.devs.devs.dto.vo.OperatorVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字段
 */
@Service
public interface FieldService {
  FieldDO get(String name);

  List<FieldDO> getAll();

  List<OperatorVO> getOperatorByType(String type);

  List<OperatorVO> getOperatorByCode(String code);

  void add(FieldDO field);

  void update(FieldDO field);

  FieldListVO getList(FieldListParams params);

  void delete(List<String> codeList);
}
