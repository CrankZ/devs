package com.devs.devs.factor;

import com.devs.devs.valueobject.RuleFieldDO;
import com.devs.devs.domain.Field;
import com.devs.devs.enums.FieldSourceEnum;
import com.devs.devs.dto.field.CreateFieldRequest;
import com.devs.devs.dto.field.QueryFieldRequest;
import com.devs.devs.dto.field.UpdateFieldRequest;
import com.devs.devs.dto.field.UpdateStatusFieldRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 松梁
 * @date 2021/6/27
 */
@Component
public class FieldFactory {

    public Field create(CreateFieldRequest request) {
        RuleFieldDO ruleFieldDO = new RuleFieldDO()
                .setFieldCode(request.getCode())
                .setFieldName(request.getName())
                .setFieldType(request.getType())
                .setFieldSource(FieldSourceEnum.PARAM.name())
                .setFieldPath(null)
                .setFieldDesc(request.getDesc())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());

        return Field.builder()
                .ruleFieldDO(ruleFieldDO)
                .build();
    }

    public Field create(UpdateFieldRequest request) {

        RuleFieldDO ruleFieldDO = new RuleFieldDO()
                .setFieldCode(request.getCode())
                .setFieldName(request.getName())
                .setFieldType(request.getType())
                .setFieldSource(FieldSourceEnum.PARAM.name())
                .setFieldDesc(request.getDesc());

        return Field.builder()
                .ruleFieldDO(ruleFieldDO)
                .build();
    }

    public Field create(UpdateStatusFieldRequest request) {

        RuleFieldDO ruleFieldDO = new RuleFieldDO()
                .setId(request.getId());

        return Field.builder()
                .ruleFieldDO(ruleFieldDO)
                .build();
    }

    public Field create(QueryFieldRequest request) {

        RuleFieldDO ruleFieldDO = new RuleFieldDO()
                .setFieldCode(request.getCode())
                .setFieldName(request.getName())
                .setFieldType(request.getType())
                .setFieldSource(request.getSource())
                .setFieldDesc(request.getDesc());

        return Field.builder()
                .ruleFieldDO(ruleFieldDO)
                .build();
    }

}
