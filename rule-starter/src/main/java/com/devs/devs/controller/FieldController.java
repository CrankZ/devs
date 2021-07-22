package com.devs.devs.controller;

import com.alibaba.fastjson.JSON;
import com.devs.devs.ResultEntity;
import com.devs.devs.enums.ResultStatusEnum;
import com.devs.devs.dto.field.CreateFieldRequest;
import com.devs.devs.dto.field.CreateFieldResponse;
import com.devs.devs.dto.field.QueryFieldRequest;
import com.devs.devs.dto.field.QueryFieldResponse;
import com.devs.devs.dto.field.UpdateFieldRequest;
import com.devs.devs.dto.field.UpdateFieldResponse;
import com.devs.devs.dto.field.UpdateStatusFieldRequest;
import com.devs.devs.dto.field.UpdateStatusFieldResponse;
import com.devs.devs.exception.DuplicateException;
import com.devs.devs.exception.NoRecordException;
import com.devs.devs.field.service.IFieldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/field")
public class FieldController {

    @Autowired
    private IFieldService fieldService;

    @PostMapping("/create")
    public ResultEntity<CreateFieldResponse> create(@RequestBody CreateFieldRequest request) {
        try {
            return fieldService.createField(request);
        } catch (DuplicateException e) {
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.CODE_VERSION_DUPLICATE);
        } catch (Throwable e) {
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.SYSTEM_INTERNAL_ERROR);
        }
    }

    @PostMapping("/update")
    public ResultEntity<UpdateFieldResponse> update(@RequestBody UpdateFieldRequest request) {
        try {
            return fieldService.updateField(request);
        } catch (DuplicateException e) {
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.CODE_VERSION_DUPLICATE);
        } catch (Throwable e) {
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.SYSTEM_INTERNAL_ERROR);
        }
    }

    /**
     * 更新field状态
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/updateStatus")
    public ResultEntity<UpdateStatusFieldResponse> updateStatus(@RequestBody UpdateStatusFieldRequest request) {
        try {
            return fieldService.updateFieldStatus(request);
        } catch (NoRecordException e) {
            log.error("<<< updateFieldStatus error on params:[{}]", JSON.toJSONString(request), e);
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.RECORD_NOT_FOUND);
        } catch (DuplicateException e) {
            log.error("<<< updateFieldStatus error on params:[{}]", JSON.toJSONString(request), e);
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.CODE_VERSION_DUPLICATE);
        } catch (Throwable e) {
            log.error("<<< updateFieldStatus error on params:[{}]", JSON.toJSONString(request), e);
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.SYSTEM_INTERNAL_ERROR);
        }
    }

    /**
     * 查询field
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/query")
    public ResultEntity<QueryFieldResponse> query(QueryFieldRequest request) {
        try {
            return fieldService.queryField(request);
        } catch (NoRecordException e) {
            log.error("<<< queryField error on params:[{}]", JSON.toJSONString(request), e);
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.RECORD_NOT_FOUND);
        } catch (Throwable e) {
            log.error("<<< queryField error on params:[{}]", JSON.toJSONString(request), e);
            return ResultEntity.of(request.getRequestId(), ResultStatusEnum.SYSTEM_INTERNAL_ERROR);
        }
    }

}
