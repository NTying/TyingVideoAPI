package com.tying;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

/**
 * @author Tying
 * @version 1.0
 */
public class CodeGeneratorTest {

    @Test
    public void generate() {
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");

        // 设置输出目录
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("tying");

        // 生成结束后是否打开文件夹
        globalConfig.setOpen(false);

        // 全部配置添加到 AutoGenerator 对象上
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/tying_video?characterEncoding=utf-8&serverTimezone=UTC");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("tying");

        // 数据源配置添加到 generator
        autoGenerator.setDataSource(dataSourceConfig);

        // 包配置, 生成的代码放在哪个包下
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.tying.mbp.generator");

        // 包配置添加到 generator
        autoGenerator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 下划线驼峰命名转换
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 开启lombok
        strategyConfig.setEntityLombokModel(true);
        // 开启RestController
        strategyConfig.setRestControllerStyle(true);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        // 开始生成
        autoGenerator.execute();
    }
}
