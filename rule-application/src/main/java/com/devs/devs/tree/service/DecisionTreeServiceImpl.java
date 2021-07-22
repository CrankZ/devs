package com.devs.devs.tree.service;

import com.devs.devs.domain.DecisionTree;
import com.devs.devs.ResultEntity;
import com.devs.devs.dto.tree.CreateTreeRequest;
import com.devs.devs.dto.tree.CreateTreeResponse;
import com.devs.devs.dto.tree.QueryTreeRequest;
import com.devs.devs.dto.tree.UpdateStatusTreeRequest;
import com.devs.devs.dto.tree.UpdateStatusTreeResponse;
import com.devs.devs.dto.tree.UpdateTreeRequest;
import com.devs.devs.dto.tree.UpdateTreeResponse;
import com.devs.devs.factor.DecisionTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DecisionTreeServiceImpl implements IDecisionTreeService {

    @Autowired
    private DecisionTreeFactory decisionTreeFactory;

    @Override
    public ResultEntity createTree(CreateTreeRequest request) {
        DecisionTree decisionTree = decisionTreeFactory.create(request);
        long decisionTreeId = decisionTree.save();
        CreateTreeResponse response = CreateTreeResponse.builder().decisionTreeId(decisionTreeId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity updateTree(UpdateTreeRequest request) {
        DecisionTree decisionTree = decisionTreeFactory.create(request);
        long decisionTreeId = decisionTree.update();
        UpdateTreeResponse response = UpdateTreeResponse.builder().decisionTreeId(decisionTreeId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity updateTreeStatus(UpdateStatusTreeRequest request) {
        DecisionTree decisionTree = decisionTreeFactory.create(request);

        long decisionTreeId = decisionTree.updateStatus();
        UpdateStatusTreeResponse response = UpdateStatusTreeResponse.builder().decisionTreeId(decisionTreeId).build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(response);
    }

    @Override
    public ResultEntity queryTree(QueryTreeRequest request) {
        DecisionTree decisionTree = decisionTreeFactory.create(request);

//        IPage<TreeInfoDO> page = decisionTree.query(request.getPageNo(), request.getPageSize());
//        List<TreeInfoDO> records = page.getRecords();
//        List<TreeInfoV> vList = TreeMsMapper.INSTANCE.do2vList(records);
//        QueryTreeResponse response = QueryTreeResponse.builder()
//                .list(vList)
//                .totalCount(page.getTotal())
//                .totalPages(page.getPages())
//                .build();
        return ResultEntity.buildSuccess(request.getRequestId()).setData(null);
    }
}