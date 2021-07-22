package com.devs.devs.field.service;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.field.CreateFieldRequest;
import com.devs.devs.dto.field.QueryFieldRequest;
import com.devs.devs.dto.field.UpdateFieldRequest;
import com.devs.devs.dto.field.UpdateStatusFieldRequest;

public interface IFieldService {

    /**
     * 创建插件
     *
     * @param request
     * @return
     */
    public ResultEntity createField(CreateFieldRequest request);

    /**
     * 更新插件
     *
     * @param request
     * @return
     */
    public ResultEntity updateField(UpdateFieldRequest request);

    /**
     * 更新插件状态
     *
     * @param request
     * @return
     */
    public ResultEntity updateFieldStatus(UpdateStatusFieldRequest request);

    /**
     * 查询插件
     *
     * @param request
     * @return
     */
    public ResultEntity queryField(QueryFieldRequest request);
}