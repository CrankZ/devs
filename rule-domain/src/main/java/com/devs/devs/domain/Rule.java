package com.devs.devs.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devs.devs.valueobject.RuleInfoDO;
import com.devs.devs.enums.RuleTypeEnum;
import com.devs.devs.exception.DuplicateException;
import com.devs.devs.exception.NoRecordException;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

@Builder
public class Rule {

    private RuleInfoDO ruleInfoDO;

    public Long save() {
        ruleInfoDO.insert();
        return ruleInfoDO.getId();
    }

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

    public Long updateStatus() {
        if (!checkIdExist()) {
            throw new NoRecordException("当前id不存在");
        }

        ruleInfoDO.insert();
        return ruleInfoDO.getId();
    }

    public Page<RuleInfoDO> query(int pageNo, int pageSize) {
        Long id = ruleInfoDO.getId();
        Long sceneId = ruleInfoDO.getSceneId();
        String ruleName = ruleInfoDO.getRuleName();

        LambdaQueryWrapper<RuleInfoDO> wrapper = new LambdaQueryWrapper<RuleInfoDO>()
                .eq(id != null, RuleInfoDO::getId, id)
                .eq(sceneId != null, RuleInfoDO::getSceneId, sceneId)
                .eq(StringUtils.isNotBlank(ruleName), RuleInfoDO::getRuleName, ruleName);

        return ruleInfoDO.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    /**
     * 判断是否唯一
     *
     * @return
     */
    private boolean checkUnique() {
        Long id = ruleInfoDO.getId();
        LambdaQueryWrapper<RuleInfoDO> wrapper = new LambdaQueryWrapper<RuleInfoDO>()
                .eq(RuleInfoDO::getRuleType, RuleTypeEnum.SIMPLE.name())
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
    private boolean checkIdExist() {
        return ruleInfoDO.selectById() != null;
    }

}
