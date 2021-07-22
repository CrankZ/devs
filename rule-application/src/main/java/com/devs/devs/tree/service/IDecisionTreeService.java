package com.devs.devs.tree.service;

import com.devs.devs.ResultEntity;
import com.devs.devs.dto.tree.CreateTreeRequest;
import com.devs.devs.dto.tree.QueryTreeRequest;
import com.devs.devs.dto.tree.UpdateStatusTreeRequest;
import com.devs.devs.dto.tree.UpdateTreeRequest;

public interface IDecisionTreeService {

    /**
     * 创建插件
     *
     * @param request
     * @return
     */
    public ResultEntity createTree(CreateTreeRequest request);

    /**
     * 更新插件R
     *
     * @param request
     * @return
     */
    public ResultEntity updateTree(UpdateTreeRequest request);

    /**
     * 更新插件状态
     *
     * @param request
     * @return
     */
    public ResultEntity updateTreeStatus(UpdateStatusTreeRequest request);

    /**
     * 查询插件
     *
     * @param request
     * @return
     */
    public ResultEntity queryTree(QueryTreeRequest request);
}