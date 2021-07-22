package com.devs.devs.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.enums.RuleTypeEnum;
import com.devs.devs.exception.DuplicateException;
import com.devs.devs.exception.NoRecordException;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

/**
 * 决策树
 *
 * @author 松梁
 * @date 2021/6/30
 */
@Builder
public class DecisionTree implements BaseDomain {

    private RuleInfoDO ruleInfoDO;

    @Override
    public Long save() {
        ruleInfoDO.insert();
        return ruleInfoDO.getId();
    }

    @Override
    public Long update() {
        if (!checkIdExist()) {
            throw new NoRecordException("当前id不存在");
        }

        if (checkUnique()) {
            throw new DuplicateException("存在code相同记录");
        }

        ruleInfoDO.insert();
        return ruleInfoDO.getId();
    }

    @Override
    public Long updateStatus() {
        if (!checkIdExist()) {
            throw new NoRecordException("当前id不存在");
        }

        ruleInfoDO.insert();
        return ruleInfoDO.getId();
    }

    @Override
    public IPage<RuleInfoDO> query(int pageNo, int pageSize) {
        Long id = ruleInfoDO.getId();
        Long modelId = ruleInfoDO.getSceneId();
        String ruleName = ruleInfoDO.getRuleName();

        LambdaQueryWrapper<RuleInfoDO> wrapper = new LambdaQueryWrapper<RuleInfoDO>()
                .eq(RuleInfoDO::getRuleType, RuleTypeEnum.DECISION_TREE.name())
                .eq(id != null, RuleInfoDO::getId, id)
                .eq(modelId != null, RuleInfoDO::getSceneId, modelId)
                .eq(StringUtils.isNotBlank(ruleName), RuleInfoDO::getRuleName, ruleName);

        return ruleInfoDO.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    /**
     * 判断是否唯一
     *
     * @return
     */
    @Override
    public boolean checkUnique() {
        Long id = ruleInfoDO.getId();
        LambdaQueryWrapper<RuleInfoDO> wrapper = new LambdaQueryWrapper<RuleInfoDO>()
                .ne(id != null, RuleInfoDO::getId, id)
                .eq(RuleInfoDO::getRuleType, ruleInfoDO.getRuleType())
                .eq(RuleInfoDO::getRuleName, ruleInfoDO.getRuleName());

        return ruleInfoDO.selectCount(wrapper) > 0;
    }

    /**
     * 判断id是否存在
     *
     * @return
     */
    @Override
    public boolean checkIdExist() {
        return ruleInfoDO.selectById() != null;
    }
}
