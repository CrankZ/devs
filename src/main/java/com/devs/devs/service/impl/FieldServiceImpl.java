package com.devs.devs.service.impl;

import com.devs.devs.consts.FieldEnum;
import com.devs.devs.consts.OperatorEnum;
import com.devs.devs.controller.params.FieldListParams;
import com.devs.devs.dao.FieldMapper;
import com.devs.devs.dao.model.FieldDO;
import com.devs.devs.dto.vo.FieldListVO;
import com.devs.devs.dto.vo.OperatorVO;
import com.devs.devs.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字段
 */
@Service
public class FieldServiceImpl implements FieldService {


  @Autowired(required = false)
  private FieldMapper fieldMapper;


  @Override
  public FieldDO get(String name) {
    return fieldMapper.selectByName(name);
  }

  @Override
  public List<FieldDO> getAll() {
    return fieldMapper.selectAll();
  }

  @Override
  public FieldListVO getList(FieldListParams params) {
    List<FieldDO> list = fieldMapper.selectByParams(params);
    FieldListVO vo = new FieldListVO();
    vo.setCurrent(1);
    vo.setPageSize(100);
    vo.setTotal(100);
    vo.setSuccess(true);
    vo.setData(list);
    return vo;
  }

  /**
   * TODO: 想办法直接输出枚举
   *
   * @param type
   * @return
   */
  @Override
  public List<OperatorVO> getOperatorByType(String type) {
    List<OperatorEnum> opList = new ArrayList<>();

    if (FieldEnum.STRING.toString().equals(type)) {
      opList = Arrays.asList(OperatorEnum.IN, OperatorEnum.NOT_IN, OperatorEnum.CONTAIN,
              OperatorEnum.NOT_CONTAIN, OperatorEnum.STARTS_WITH);
    }

    List<OperatorVO> voList = new ArrayList<>();
    for (OperatorEnum operatorEnum : opList) {
      OperatorVO vo = new OperatorVO();
      vo.setCode(operatorEnum.name());
      vo.setName(operatorEnum.getName());
      voList.add(vo);
    }

    return voList;
  }

  @Override
  public List<OperatorVO> getOperatorByCode(String code) {
    FieldDO fieldDO = fieldMapper.selectByCode(code);
    return getOperatorByType(fieldDO.getType());
  }

  @Override
  public void add(FieldDO field) {
    int affectedCount = fieldMapper.insert(field);
    if (affectedCount < 1) {
      throw new RuntimeException("添加字段失败");
    }
  }

  @Override
  public void update(FieldDO field) {
    int affectedCount = fieldMapper.updateByCode(field);
    if (affectedCount < 1) {
      throw new RuntimeException("更新字段失败");
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(List<String> codeList) {
    for (String code : codeList) {
      int affectedCount = fieldMapper.deleteByCode(code);
      if (affectedCount < 1) {
        throw new RuntimeException("更新字段失败");
      }
    }
  }
}
