package com.devs.devs.dto.tree;

import com.devs.devs.valueobject.RuleInfoV;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QueryTreeResponse {

    /**
     * 列表
     */
    private List<RuleInfoV> list;

    /**
     * 总共多少条记录
     */
    private Long totalCount;

    /**
     * 总共页数
     */
    private Long totalPages;
}
