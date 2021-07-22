package com.devs.devs.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devs.devs.valueobject.RuleFieldDO;
import com.devs.devs.exception.DuplicateException;
import com.devs.devs.exception.NoRecordException;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

/**
 * 字段
 */
@Builder
public class Field {

    private RuleFieldDO ruleFieldDO;

    public Long save() {
        ruleFieldDO.insert();
        return ruleFieldDO.getId();
    }

    public Long update() {
        if (!checkIdExist()) {
            throw new NoRecordException("当前id不存在");
        }

        if (checkUnique()) {
            throw new DuplicateException("存在code相同记录");
        }

        ruleFieldDO.updateById();
        return ruleFieldDO.getId();
    }

    /**
     * 更新状态
     *
     * @return 主键id
     */
    public Long updateStatus() {
        if (!checkIdExist()) {
            throw new NoRecordException("当前id不存在");
        }

        ruleFieldDO.updateById();
        return ruleFieldDO.getId();
    }

    public IPage<RuleFieldDO> query(int pageNo, int pageSize) {
        Long id = ruleFieldDO.getId();
        String fieldCode = ruleFieldDO.getFieldCode();
        String fieldName = ruleFieldDO.getFieldName();
        String fieldType = ruleFieldDO.getFieldType();
        String fieldSource = ruleFieldDO.getFieldSource();
        String fieldDesc = ruleFieldDO.getFieldDesc();

        LambdaQueryWrapper<RuleFieldDO> wrapper = new LambdaQueryWrapper<RuleFieldDO>()
                .eq(id != null, RuleFieldDO::getId, id)
                .eq(StringUtils.isNotBlank(fieldCode), RuleFieldDO::getFieldCode, fieldCode)
                .eq(StringUtils.isNotBlank(fieldName), RuleFieldDO::getFieldName, fieldName)
                .eq(StringUtils.isNotBlank(fieldType), RuleFieldDO::getFieldType, fieldType)
                .eq(StringUtils.isNotBlank(fieldSource), RuleFieldDO::getFieldSource, fieldSource)
                .eq(StringUtils.isNotBlank(fieldDesc), RuleFieldDO::getFieldDesc, fieldDesc);

        return ruleFieldDO.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    /**
     * 判断是否唯一
     *
     * @return
     */
    private boolean checkUnique() {
        Long id = ruleFieldDO.getId();
        LambdaQueryWrapper<RuleFieldDO> wrapper = new LambdaQueryWrapper<RuleFieldDO>()
                .ne(id != null, RuleFieldDO::getId, id)
                .eq(RuleFieldDO::getFieldCode, ruleFieldDO.getFieldCode());
        return ruleFieldDO.selectCount(wrapper) > 0;
    }

    /**
     * 判断id是否存在
     *
     * @return
     */
    private boolean checkIdExist() {
        return ruleFieldDO.selectById() != null;
    }

}
