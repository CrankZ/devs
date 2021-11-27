import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class RepositoryGenerator {

    public static void main(String[] args) throws Exception {

        List<String> warnings = new ArrayList<>();
        boolean overWrite = true;
        File configFile = new File(RepositoryGenerator.class.getResource("/mybatisGenerator.xml").toURI());
        System.out.println(configFile.getAbsolutePath());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overWrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        warnings.stream().forEach(System.out::println);
        System.out.println("生成Mybatis配置成功！");
    }
}