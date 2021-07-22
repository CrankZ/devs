package com.devs.devs.dto.tree;

import com.devs.devs.dto.base.BaseRequest;
import lombok.Data;
import net.sf.oval.constraint.Range;

import javax.validation.constraints.Size;

@Data
public class QueryTreeRequest extends BaseRequest {

    /**
     * 场景id
     */
    private Long sceneId;

    /**
     * 规则集名称
     */
    private String ruleName;

    private String ruleType;

    /**
     * 描述
     */
    private String desc;

    /**
     * 分页大小
     */
    @Range(min = 1, max = 100)
    private Integer pageSize = 10;

    /**
     * 当前页
     */
    @Size(min = 1)
    private Integer pageNo = 1;

}
