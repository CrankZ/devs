package com.devs.devs.utils;

import com.devs.devs.dao.model.FieldDO;
import com.devs.devs.dto.Field;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 临时方案
 */
public class PojoUtils {
  /**
   * 临时方案
   *
   * @param fieldDOList
   * @return
   */
  @Deprecated
  public static List<Field> getFiledList(List<FieldDO> fieldDOList) {
    List<Field> fieldList = new ArrayList<>();

    for (FieldDO fieldDO : fieldDOList) {
      if (fieldDO == null) {
        continue;
      }
      Field field = new Field();
      BeanUtils.copyProperties(fieldDO, field);
      fieldList.add(field);
    }
    return fieldList;
  }
}
