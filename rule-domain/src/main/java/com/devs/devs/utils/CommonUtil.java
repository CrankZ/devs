package com.devs.devs.utils;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 通用工具类
 *
 * @author 松梁
 * @date 2021/11/27
 */
public class CommonUtil {

    /**
     * 集合转成逗号分隔
     *
     * @param list
     * @return
     */
    public static String list2Str(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        return String.join(",", list);
    }
}
