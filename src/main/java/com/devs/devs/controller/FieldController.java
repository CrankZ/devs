package com.devs.devs.controller;

import com.devs.devs.controller.params.FieldListParams;
import com.devs.devs.dao.model.FieldDO;
import com.devs.devs.dto.vo.FieldListVO;
import com.devs.devs.dto.vo.OperatorVO;
import com.devs.devs.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/field")
public class FieldController {

  @Autowired
  private FieldService fieldService;

  @GetMapping("/all")
  public List<FieldDO> allFiled() {
    Map<String, Object> map = new HashMap<>();

    List<FieldDO> list = fieldService.getAll();
    for (FieldDO fieldDO : list) {
      fieldDO.setField(fieldDO.getName());
      fieldDO.setLabel(fieldDO.getName());
      fieldDO.setValue(fieldDO.getName());
      fieldDO.setSuccess(true);
    }

    return list;
  }

  /**
   * 分页获取字段
   *
   * @param params
   * @return
   */
  @PostMapping("/list")
  public FieldListVO list(@RequestBody FieldListParams params) {
    return fieldService.getList(params);
  }

  @GetMapping("/operator/type/{type}")
  public List<OperatorVO> getOperatorByType(@PathVariable String type) {
    return fieldService.getOperatorByType(type);
  }

  @GetMapping("/operator/code/{code}")
  public List<OperatorVO> getOperatorByCode(@PathVariable String code) {
    return fieldService.getOperatorByCode(code);
  }

  @GetMapping("/{filedName}")
  public FieldDO get(@PathVariable String filedName) {
    return fieldService.get(filedName);
  }

  @PostMapping("/add")
  public void add(@RequestBody FieldDO fieldDO) {
    fieldDO.setCreateTime(new Date());
    fieldDO.setUpdateTime(new Date());
    fieldService.add(fieldDO);
  }

  @PostMapping("/update")
  public void update(@RequestBody FieldDO fieldDO) {
    fieldDO.setUpdateTime(new Date());
    fieldService.update(fieldDO);
  }

  @PostMapping("/delete")
  public void delete(@RequestBody List<String> codeList) {
    fieldService.delete(codeList);
  }

}
