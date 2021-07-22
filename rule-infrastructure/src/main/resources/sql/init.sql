# 字段表
# TODO: 字段类型是编码还是序号？
DROP TABLE IF EXISTS rule_field;
CREATE TABLE rule_field
(
    id           bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    create_time  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted      INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    field_status INT         NOT NULL DEFAULT 1 COMMENT '1上线,0下线',
    field_code   varchar(32) NOT NULL COMMENT '字段编码',
    field_name   varchar(32) NOT NULL COMMENT '字段名',
    field_type   varchar(16) NOT NULL COMMENT '字段类型 字符串STRING,NUMBER数字',
    field_source varchar(16) NOT NULL COMMENT '字段来源 入参PARAM；接口取数HTTP',
    field_path   varchar(100) COMMENT '取数路径，入参来源则本字段为null',
    field_desc   varchar(200) COMMENT '描述',
    PRIMARY KEY (id)
) COMMENT '字段';
create unique index uk_field_code on rule_field (field_code);

INSERT INTO rule_field (id, create_time, update_time, field_code, field_name, field_type, field_source, field_path, field_desc)
VALUES ('age', '年龄', 'STRING', 'HTTP', 'https://www.xx.com/getAge', '');
INSERT INTO rule_field (id, create_time, update_time, field_code, field_name, field_type, field_source, field_path, field_desc)
VALUES ('name', '姓名', 'NUMBER', 'PARAM', null, '');

# 规则表
DROP TABLE IF EXISTS rule_info;
CREATE TABLE rule_info
(
    id          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    rule_status INT         NOT NULL DEFAULT 0 COMMENT '1上线,0下线',
    scene_id    bigint      NOT NULL COMMENT '规则所属场景',
    rule_name   varchar(32) NOT NULL COMMENT '规则名',
    rule_type   varchar(16) NOT NULL COMMENT '规则类型，1.规则集RULE_SET；2.决策树DECISION_TREE；3.评分卡SCORE_CARD',
    rule_json   json        NOT NULL COMMENT '结构体',
    rule_desc   varchar(16) NOT NULL COMMENT '规则备注',
    PRIMARY KEY (id)
) COMMENT '规则信息';

INSERT INTO rule_info (scene_id, rule_name, rule_type, rule_json, rule_desc)
VALUES (1, '简单规则名称', 'SIMPLE', '{"desc":"备注","then":[{"type":"PRINT","value":"成功"}],"sceneId":"场景id","ruleName":"规则名称","otherwise":[{"type":"PRINT","value":"失败"}],"conditions":{"and":[{"or":[{"value":"李四","operator":"StringMethod.equals($1,$2)","fieldCode":"name"},{"value":10,"operator":"$1\u003e$2","fieldCode":"age"}]}]}}', '规则描述');

# 场景
DROP TABLE IF EXISTS rule_scene;
CREATE TABLE rule_scene
(
    id          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    scene_name  varchar(32) NOT NULL COMMENT '场景名',
    PRIMARY KEY (id)
);
create unique index idx_scene_name on rule_scene (scene_name);

INSERT INTO rule_scene (scene_name)
values ('场景名称1');

# 规则执行、触发记录表
DROP TABLE IF EXISTS rule_record;
CREATE TABLE rule_record
(
    id          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    request_id  varchar(32) NOT NULL COMMENT '请求id',
    scene_id    bigint      NOT NULL COMMENT '场景id',
    param       json        NOT NULL COMMENT '入参',
    PRIMARY KEY (id)
) COMMENT '规则接口请求记录';

DROP TABLE IF EXISTS rule_result;
CREATE TABLE rule_result
(
    id          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    scene_id    bigint      NOT NULL COMMENT '场景id',
    request_id  varchar(32) NOT NULL COMMENT '请求id',
    rule_type   varchar(16) NOT NULL COMMENT '执行结果类型分开保存，规则类型：规则集RULE_SET；决策树DECISION_TREE；评分卡SCORE_CARD',
    rule_result json        NOT NULL COMMENT '当时场景下所有规则的结构体，包括执行结果',
    PRIMARY KEY (id)
) COMMENT '规则执行结果';

# 触发器表
# TODO: 这个可以暂时先不做，完全解耦，触发操作交给用户
DROP TABLE IF EXISTS trigger;
CREATE TABLE trigger
(
    id              bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    create_time     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted         INT         NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除; 1-已删除',
    trigger_name    varchar(32) NOT NULL COMMENT '触发器名',
    trigger_type    varchar(16) NOT NULL COMMENT '触发器1.HTTP_GET2.HTTP_POST',
    trigger_content varchar(16) NOT NULL COMMENT '触发器内容，一般为JSON（压缩后的）',
    PRIMARY KEY (id)
);