# 字段表
# TODO: 字段类型是编码还是序号？
DROP TABLE IF EXISTS `field`;
CREATE TABLE `field`
(
    `ID`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` datetime    NOT NULL,
    `UPDATE_TIME` datetime    NOT NULL,
    `CODE`        varchar(32) NOT NULL COMMENT '字段编码',
    `NAME`        varchar(32) NOT NULL COMMENT '字段名',
    `TYPE`        varchar(16) NOT NULL COMMENT '字段类型 字符串STRING,NUMBER数字',
    `SOURCE`      varchar(16) NOT NULL COMMENT '字段来源 入参PARAM；接口取数HTTP',
    `PATH`        varchar(100) COMMENT '取数路径，入参来源则本字段为null',
    `DESC`        varchar(200) COMMENT '描述',
    PRIMARY KEY (`ID`)
) COMMENT '字段';
create unique index field_CODE_uindex on field (CODE);

INSERT INTO field (ID, CREATE_TIME, UPDATE_TIME, CODE, NAME, TYPE, SOURCE, PATH)
VALUES (1, '2021-02-08 22:41:36', '2021-02-08 22:41:39', 'age', '年龄', '2', '1', null);
INSERT INTO field (ID, CREATE_TIME, UPDATE_TIME, CODE, NAME, TYPE, SOURCE, PATH)
VALUES (2, '2021-02-22 15:38:58', '2021-02-22 15:38:58', 'name', '姓名', '1', '1', null);

# 规则表
DROP TABLE IF EXISTS `rule_info`;
CREATE TABLE `rule_info`
(
    `ID`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` datetime    NOT NULL,
    `UPDATE_TIME` datetime    NOT NULL,
    `RULE_NAME`   varchar(32) NOT NULL COMMENT '规则名',
    `RULE_TYPE`   varchar(16) NOT NULL COMMENT '规则类型，1.规则集RULE_SET；2.决策树DECISION_TREE；3.评分卡SCORE_CARD',
    `MODEL_ID`    varchar(32) NOT NULL COMMENT '规则所属模型',
    `JSON`        json        NOT NULL COMMENT '结构体',
    `REMARK`      varchar(16) NOT NULL COMMENT '规则备注',
    PRIMARY KEY (`ID`)
) COMMENT '规则信息';

INSERT INTO rule_info (ID, CREATE_TIME, UPDATE_TIME, NAME, TYPE, MODEL_ID, JSON, `DESC`)
VALUES (1, '2021-02-23 13:21:17', '2021-02-23 13:21:17', '规则集测试', 'RULE_SET', 'M1', '{"name": "决策集名称", "rules": [{"failure": [{"type": "HTTP", "value": {"url": "http://localhost:8888/action2", "method": "GET", "params": {"k21": "v21", "k22": "v22"}}}], "options": {"desc": "配置选项"}, "success": [{"type": "PRINT", "value": "你好"}, {"type": "HTTP", "value": {"url": "http://localhost:8888/action1", "method": "POST", "params": {"k11": "v11", "k12": "v12"}}}], "conditions": {"and": [{"or": [{"value": "李四", "operator": "StringMethod.equals($1,$2)", "fieldCode": "name"}, {"value": 10, "operator": "$1>$2", "fieldCode": "age"}]}, {"value": 10, "operator": "$1>$2", "fieldCode": "age"}]}}], "remark": "备注", "modelId": "模型ID"}', '备注');

# 模型
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model`
(
    `ID`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` datetime    NOT NULL,
    `UPDATE_TIME` datetime    NOT NULL,
    `MODE_NAME`   varchar(32) NOT NULL COMMENT '模型名',
    PRIMARY KEY (`ID`)
);
create unique index field_MODE_NAME_uindex on model (MODE_NAME);

# 规则执行、触发记录表
DROP TABLE IF EXISTS `rule_record`;
CREATE TABLE `rule_record`
(
    `ID`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` datetime    NOT NULL,
    `UPDATE_TIME` datetime    NOT NULL,
    `REQUEST_ID`  varchar(32) NOT NULL COMMENT '请求ID',
    `MODEL_ID`    varchar(32) NOT NULL COMMENT '模型ID',
    `PARAM`       json        NOT NULL COMMENT '入参',
    PRIMARY KEY (`ID`)
) COMMENT '规则执行记录';

DROP TABLE IF EXISTS `rule_result`;
CREATE TABLE `rule_result`
(
    `ID`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` datetime    NOT NULL,
    `UPDATE_TIME` datetime    NOT NULL,
    `REQUEST_ID`  varchar(32) NOT NULL COMMENT '请求ID',
    `MODEL_ID`    varchar(32) NOT NULL COMMENT '模型ID',
    `RULE_TYPE`   varchar(16) NOT NULL COMMENT '执行结果类型分开保存，规则类型：规则集RULE_SET；决策树DECISION_TREE；评分卡SCORE_CARD',
    `RULE_RESULT` json        NOT NULL COMMENT '当时模型下所有规则的结构体，包括执行结果',
    PRIMARY KEY (`ID`)
) COMMENT '规则执行结果';

# 触发器表
# TODO: 这个可以暂时先不做，完全解耦，触发操作交给用户
DROP TABLE IF EXISTS `trigger`;
CREATE TABLE `trigger`
(
    `ID`              bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME`     datetime    NOT NULL,
    `UPDATE_TIME`     datetime    NOT NULL,
    `TRIGGER_NAME`    varchar(32) NOT NULL COMMENT '触发器名',
    `TRIGGER_TYPE`    varchar(16) NOT NULL COMMENT '触发器1.HTTP_GET2.HTTP_POST',
    `TRIGGER_CONTENT` varchar(16) NOT NULL COMMENT '触发器内容，一般为JSON（压缩后的）',
    PRIMARY KEY (`ID`)
);