package com.devs.devs.valueobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * rule_record
 *
 * @author
 */
@Data
@Accessors(chain = true)
@TableName("rule_record")
public class RuleRecordDO extends Model<RuleRecordDO> {

    /**
     * 主键
     */
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer deleted;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 场景ID
     */
    private Long sceneId;

    /**
     * 入参
     */
    private String param;

}