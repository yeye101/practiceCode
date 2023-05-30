package com.yeye.practiceCode.util;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class CodeGenerator {

  public static void main(String[] args) {
    String password = "Zdc20010604.";
    String username = "root";
    String url = "jdbc:mysql://110.42.136.163:3306/mall_wms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    String finalProjectPath = System.getProperty("user.dir");

    DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(url, username, password);
    // 生成全部注释掉就好
    List<String> tableList = new ArrayList<>();
//    tableList.add("wms_ware_order_task_detail");
    creteModel(dataSourceConfig, finalProjectPath, Boolean.TRUE, tableList);
//    createSingleModel(dataSourceConfig,finalProjectPath);
  }
  private static void creteModel(DataSourceConfig.Builder dataSourceConfig,
                                 String finalProjectPath,
                                 Boolean isOverride,
                                 List<String> tableList) {
    FastAutoGenerator.create(dataSourceConfig)
      .globalConfig(builder -> {
        builder.author("yeye1") // 设置作者
//          .enableSwagger() // 开启 swagger 模式
          .fileOverride() // 覆盖已生成文件
          .disableOpenDir() //禁止打开输出目录
          .outputDir(finalProjectPath + "/src/main/java"); // 指定输出目录
      })
      .packageConfig(builder -> {
        builder.parent("com.yeye.practiceCode") // 设置父包名
          .entity("model") //设置entity包名
          .other("dao") // 设置dto包名
          .pathInfo(Collections.singletonMap(OutputFile.xml, finalProjectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径

      }).strategyConfig(builder -> {

        if (!CollectionUtils.isEmpty(tableList)) {
          builder.addInclude(tableList);
        }

        builder.addTablePrefix("wms_");// 设置过滤表前缀

        builder.entityBuilder() // entity配置
          .enableRemoveIsPrefix()
          .enableTableFieldAnnotation()
          .enableLombok();

        builder.controllerBuilder() // controller配置
          .enableRestStyle();


        builder.mapperBuilder() // mapper配置
          .enableBaseResultMap()
          .enableBaseColumnList();
        if (isOverride) {
          builder.entityBuilder() // entity配置
            .fileOverride();
          builder.controllerBuilder() // controller配置
            .fileOverride();
          builder.serviceBuilder() // service配置
            .fileOverride();
          builder.mapperBuilder() // mapper配置
            .fileOverride();
        }
      })
      .injectionConfig(consumer -> {
        Map<String, String> customFile = new HashMap<>();
        // DTO
//        customFile.put("DTO.java", "/templates/entityDTO.java.ftl"); //自定义模版引擎
        consumer.customFile(customFile);
      }).templateEngine(new VelocityTemplateEngine())
      .execute();
  }

  private static void createSingleModel(DataSourceConfig.Builder dataSourceConfig, String finalProjectPath) {
    FastAutoGenerator.create(dataSourceConfig)
      // 全局配置
      .globalConfig((scanner, builder) ->
        builder.author(scanner.apply("请输入作者名称？"))
          .fileOverride()
          .outputDir(finalProjectPath + "/src/main/java"))
      // 包配置
      .packageConfig(builder -> {
        builder.parent("com.yeye.practiceCode") // 设置父包名
          .entity("model") //设置entity包名
          .other("dao") // 设置dto包名
          .pathInfo(Collections.singletonMap(OutputFile.xml, finalProjectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径

      })
      // 策略配置
      .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
        .controllerBuilder().enableRestStyle()
        .entityBuilder().enableLombok()
        .mapperBuilder().enableBaseResultMap().enableBaseColumnList()
        .build())

      .execute();


  }

  // 处理 all 情况
  protected static List<String> getTables(String tables) {
    return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
  }

}
