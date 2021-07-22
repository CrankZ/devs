package com.devs.devs.field.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devs.devs.valueobject.RuleFieldDO;
import com.devs.devs.valueobject.FieldV;
import com.devs.devs.domain.Field;
import com.devs.devs.ResultEntity;
import com.devs.devs.dto.field.CreateFieldRequest;
import com.devs.devs.dto.field.CreateFieldResponse;
import com.devs.devs.dto.field.QueryFieldRequest;
import com.devs.devs.dto.field.QueryFieldResponse;
import com.devs.devs.dto.field.UpdateFieldRequest;
import com.devs.devs.dto.field.UpdateFieldResponse;
import com.devs.devs.dto.field.UpdateStatusFieldRequest;
import com.devs.devs.dto.field.UpdateStatusFieldResponse;
import com.devs.devs.factor.FieldFactory;
import com.devs.devs.mapstruct.mapper.FieldMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class FieldServiceImpl implements IFieldService {

    @Autowired
    private FieldFactory fieldFactory;

    /**
     * 创建插件
     *
     * @param request
     * @return
     */
    @Override
    public ResultEntity createField(CreateFieldRequest request) {
        Field field = fieldFactory.create(request);
        long fieldId = field.save();
        CreateFieldResponse response = CreateFieldResponse.builder().fieldId(fieldId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity updateField(UpdateFieldRequest request) {
        Field field = fieldFactory.create(request);
        long fieldId = field.update();
        UpdateFieldResponse response = UpdateFieldResponse.builder().fieldId(fieldId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity updateFieldStatus(UpdateStatusFieldRequest request) {
        Field field = fieldFactory.create(request);

        long fieldId = field.updateStatus();
        UpdateStatusFieldResponse response = UpdateStatusFieldResponse.builder().fieldId(fieldId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity queryField(QueryFieldRequest request) {
        Field field = fieldFactory.create(request);

        IPage<RuleFieldDO> page = field.query(request.getPageNo(), request.getPageSize());
        List<RuleFieldDO> records = page.getRecords();
        List<FieldV> vList = FieldMapper.INSTANCE.do2vList(records);
        QueryFieldResponse response = QueryFieldResponse.builder()
                .list(vList)
                .totalCount(page.getTotal())
                .totalPages(page.getPages())
                .build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }
}