import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 */
public class MybatisPlusCodeGenerator {

    public static void main(String[] args) throws IOException {

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/devs?useUnicode=true&useSSL=false&characterEncoding=utf8",
                "root",
                "123456")
//                .driver("com.mysql.cj.jdbc.Driver")
                .build();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);
        // 全局配置
        String projectPath = System.getProperty("user.dir") + "/rule-infrastructure";
        GlobalConfig gc = new GlobalConfig.Builder()
                .outputDir(projectPath + "/src/main/java")
                .author("松梁")
                .commentDate("yyyy-MM-dd")
                //覆盖文件
                .fileOverride()
//                .enableSwagger()
                //打开文件夹
                .openDir(false)
                .build();
        mpg.global(gc);

        Map pathInfo = new HashMap<>();
        String xmlMapperPath = projectPath + "/src/main/resources/mapper/check";
        pathInfo.put(ConstVal.XML_PATH, xmlMapperPath);
        // 包配置
        PackageConfig pc = new PackageConfig.Builder()
                .parent("com.devs.devs")
                .entity("dataobject")
                .mapper("mapper")
                .service("service")
                .xml("xml")
//                .pathInfo(pathInfo)
                .build();
        mpg.packageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig.Builder()
                .enableCapitalMode()
//                .addTablePrefix("t_")
                .addInclude("rule_template_map,rule_template,rule_info,rule_condition,rule_field,rule_result,,rule_trigger".split(","))
                .build();

        strategy.entityBuilder()
                .nameConvert(new EnhanceNameConvert(strategy))
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .idType(IdType.AUTO)
                .enableChainModel()
                .enableLombok()
                .addTableFills(Arrays.asList(new Column("mtime", FieldFill.UPDATE)))
                .enableActiveRecord()
        ;

        strategy.mapperBuilder()
                .enableBaseResultMap()
                .enableBaseColumnList();

        strategy.controllerBuilder()
                .enableHyphenStyle()
                .enableRestStyle();

        TemplateConfig.Builder builder = new TemplateConfig.Builder();
        builder.entity("templates/entity.java");
        builder.mapper("templates/mapper.java");

        mpg.strategy(strategy);
        //不生成controller和service
        mpg.template(builder.build().disable(TemplateType.CONTROLLER, TemplateType.SERVICE));
        FreemarkerTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        mpg.execute(templateEngine);

        //移动xml文件至resources目录下
        System.out.println(JSON.toJSONString(mpg.getConfig().getPathInfo()));
        // xmldir
        String xmlPath = mpg.getConfig().getPathInfo().get(ConstVal.XML_PATH);
        File file = new File(xmlPath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles((dir, name) -> {
                if (name.endsWith(".xml")) {
                    return true;
                }
                return false;
            });
            File destDir = new File(xmlMapperPath);
            if (destDir.exists() && destDir.isDirectory()) {
                FileUtils.deleteDirectory(destDir);
            }

            for (File f : files) {
                FileUtils.moveFileToDirectory(f, destDir, true);
            }
        }
    }

}