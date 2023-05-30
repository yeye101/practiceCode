package com.yeye.practiceCode.util;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {

  public static void main(String[] args) {
    String password = "Zdc20010604.";
    String username = "root";
    String url = "jdbc:mysql://110.42.136.163:3306/mall_wms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    String finalProjectPath = System.getProperty("user.dir");
    FastAutoGenerator.create(url, username, password)
      .globalConfig(builder -> {
        builder.author("yeye") // 设置作者
          .enableSwagger() // 开启 swagger 模式
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
        builder.addTablePrefix("wms_"); // 设置过滤表前缀
      })
      .injectionConfig(consumer -> {
        Map<String, String> customFile = new HashMap<>();
        // DTO
//        customFile.put("DTO.java", "/templates/entityDTO.java.ftl"); //自定义模版引擎
        consumer.customFile(customFile);
      }).templateEngine(new VelocityTemplateEngine())
      .execute();


  }

}
