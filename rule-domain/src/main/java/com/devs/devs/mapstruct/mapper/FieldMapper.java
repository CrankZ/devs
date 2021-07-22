package com.devs.devs.mapstruct.mapper;

import com.devs.devs.valueobject.RuleFieldDO;
import com.devs.devs.valueobject.FieldV;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FieldMapper {
    FieldMapper INSTANCE = Mappers.getMapper(FieldMapper.class);

    FieldV do2v(RuleFieldDO ruleFieldDO);

    List<FieldV> do2vList(List<RuleFieldDO> doList);
}
