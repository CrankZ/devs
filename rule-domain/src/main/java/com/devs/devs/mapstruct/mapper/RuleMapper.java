package com.devs.devs.mapstruct.mapper;

import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.valueobject.RuleInfoV;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RuleMapper {
    RuleMapper INSTANCE = Mappers.getMapper(RuleMapper.class);

    RuleInfoV do2v(RuleInfoDO fieldDO);

    List<RuleInfoV> do2vList(List<RuleInfoDO> doList);
}
