package com.devs.devs.dto.tree;

import com.devs.devs.dto.base.BaseRequest;
import lombok.Data;

@Data
public class UpdateStatusTreeRequest extends BaseRequest {

    private Long decisionTreeId;

}
