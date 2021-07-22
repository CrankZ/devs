package com.devs.devs.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author 松梁
 * @date 2021/7/22
 */
public interface BaseDomain {
    /**
     * 保存
     *
     * @return
     */
    Long save();

    /**
     * 更新
     *
     * @return
     */
    Long update();

    /**
     * 更新状态
     *
     * @return
     */
    Long updateStatus();

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage query(int pageNo, int pageSize);

    /**
     * 判断是否唯一
     *
     * @return
     */
    boolean checkUnique();

    /**
     * 判断id是否存在
     *
     * @return
     */
    boolean checkIdExist();
}
