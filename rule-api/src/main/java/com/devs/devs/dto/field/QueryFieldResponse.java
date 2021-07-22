package com.devs.devs.dto.field;

import com.devs.devs.valueobject.FieldV;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QueryFieldResponse {

    /**
     * 列表
     */
    private List<FieldV> list;

    /**
     * 总共多少条记录
     */
    private Long totalCount;

    /**
     * 总共页数
     */
    private Long totalPages;
}
