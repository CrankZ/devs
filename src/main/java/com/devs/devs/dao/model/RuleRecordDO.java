package com.devs.devs.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * rule_record
 * @author 
 */
@Data
public class RuleRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    private Date createTime;

    private Date updateTime;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 模型ID
     */
    private String modelId;

    /**
     * 入参
     */
    private Object param;

}