package com.devs.devs.mapstruct;


import com.devs.devs.dao.model.FieldDO;
import com.devs.devs.dto.Field;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapping extends BaseMapping<FieldDO, Field> {

}
