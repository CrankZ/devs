# CREATE DATABASE devs;
# 规则模板（规则执行的粒度是模板）
DROP TABLE IF EXISTS rule_template;
CREATE TABLE rule_template
(
    id            bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    ctime         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted       INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    template_name varchar(32) NOT NULL DEFAULT '' COMMENT '字段名',
    PRIMARY KEY (id)
) COMMENT '模板表';
insert into rule_template(template_name)
values ('模板1');

# 规则-模板映射表
DROP TABLE IF EXISTS rule_template_map;
CREATE TABLE rule_template_map
(
    id          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    ctime       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT      NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    template_id bigint   NOT NULL DEFAULT 0 COMMENT '模板id',
    rule_id     bigint   NOT NULL DEFAULT 0 COMMENT '规则id',
    PRIMARY KEY (id)
) COMMENT '规则-模板映射表';
insert into rule_template_map(template_id, rule_id)
values (1, 1),
       (1, 2),
       (1, 3);

# 基础字段
DROP TABLE IF EXISTS rule_field;
CREATE TABLE rule_field
(
    id                bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    ctime             DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime             DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted           INT          NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    field_code        varchar(32)  NOT NULL DEFAULT '' COMMENT '字段编码',
    field_name        varchar(32)  NOT NULL DEFAULT '' COMMENT '字段名',
    field_type        varchar(16)  NOT NULL DEFAULT '' COMMENT '字段类型 字符串STRING,NUMBER数字',
    field_source_type varchar(16)  NOT NULL DEFAULT '' COMMENT '字段来源 入参PARAM；接口HTTP；反射REFLECT',
    field_source      varchar(100) NOT NULL DEFAULT '' COMMENT '取数地址',
    field_desc        varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
    PRIMARY KEY (id)
) COMMENT '基础字段';
insert into rule_field(field_code, field_name, field_type, field_source_type, field_source, field_desc)
VALUES ('name', '姓名', 'STRING', 'REFLECT', 'engineServiceImpl#testReflect', ''),
       ('age', '年龄', 'NUMBER', 'REFLECT', 'engineServiceImpl#testReflect', '');

# 单个条件
DROP TABLE IF EXISTS rule_condition;
CREATE TABLE rule_condition
(
    id             bigint        NOT NULL AUTO_INCREMENT COMMENT '主键',
    ctime          DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime          DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted        INT           NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    condition_name varchar(32)   NOT NULL COMMENT '条件名称',
    condition_code varchar(32)   NOT NULL COMMENT '条件编码',
    field_code     varchar(32)   NOT NULL COMMENT '字段编码',
    operator       varchar(1024) NOT NULL COMMENT '比较符号',
    expect         varchar(16)   NOT NULL COMMENT '期望值',
    PRIMARY KEY (id)
) COMMENT '规则条件';
insert into rule_condition(condition_code, condition_name, field_code, operator, expect)
values ('C_NAME_IS_ZS', '姓名是否等于张三', 'name', 'StringMethod.equals($EXPECT,$FACT)', '张三'),
       ('C_AGE_GT18', '年龄是否大于18岁', 'age', 'NumberMethod.gt($EXPECT,$FACT)', '18');

# 规则
DROP TABLE IF EXISTS rule_info;
CREATE TABLE rule_info
(
    id         bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    ctime      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted    INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    rule_name  varchar(32) NOT NULL DEFAULT '' COMMENT '规则名',
    conditions varchar(32) NOT NULL DEFAULT '' COMMENT '条件组',
    rule_desc  varchar(16) NOT NULL DEFAULT '' COMMENT '规则备注',
    PRIMARY KEY (id)
) COMMENT '规则信息';
insert into rule_info(rule_name, conditions, rule_desc)
values ('姓名是否等于张三', 'C_NAME_IS_ZS', ''),
       ('年龄是否大于18岁', 'C_AGE_GT18', ''),
       ('用户是否符合条件', 'C_NAME_IS_ZS && C_AGE_GT18', '');

# 规则执行结果
DROP TABLE IF EXISTS rule_result;
CREATE TABLE rule_result
(
    id         bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    ctime      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted    INT          NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    rule_id    bigint       NOT NULL DEFAULT 0 COMMENT '规则ID',
    request    varchar(100) NOT NULL DEFAULT '' COMMENT '执行入参',
    response   varchar(100) NOT NULL DEFAULT '' COMMENT '执行出参',
    is_trigger INT          NOT NULL DEFAULT 0 COMMENT '规则是否触发 0-未触发; 1-触发',
    facts      varchar(32)  NOT NULL DEFAULT '' COMMENT '规则执行结果',
    snapshot   varchar(32)  NOT NULL DEFAULT '' COMMENT '规则执行时的快照',
    PRIMARY KEY (id)
) COMMENT '规则执行结果';

# TODO: 规则触发器
DROP TABLE IF EXISTS rule_trigger;
CREATE TABLE rule_trigger
(
    id            bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    ctime         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted       INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    rule_id       bigint      NOT NULL DEFAULT 0 COMMENT '规则ID',
    success_type  varchar(32) NOT NULL DEFAULT '' COMMENT '规则触发成功 执行的动作类型（HTTP，邮件，企业微信）',
    success_value varchar(32) NOT NULL DEFAULT '' COMMENT '规则触发成功 内容',
    failure_type  varchar(32) NOT NULL DEFAULT '' COMMENT '规则触发失败 执行的动作类型（HTTP，邮件，企业微信）',
    failure_value varchar(32) NOT NULL DEFAULT '' COMMENT '规则触发失败 内容',
    PRIMARY KEY (id)
) COMMENT '规则触发器';