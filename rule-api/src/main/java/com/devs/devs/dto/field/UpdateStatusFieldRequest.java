package com.devs.devs.dto.field;

import com.devs.devs.dto.base.BaseRequest;
import lombok.Data;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Range;

@Data
public class UpdateStatusFieldRequest extends BaseRequest {
    /**
     * 插件id
     */
    @NotNull
    @Range(min = 0)
    private Long id;

    /**
     * 插件状态
     */
    @NotNull
    @Range(min = 0, max = 1)
    private Integer pluginStatus;
}
