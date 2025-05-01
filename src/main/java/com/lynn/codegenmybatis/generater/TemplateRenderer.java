package com.lynn.codegenmybatis.generater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Component
public class TemplateRenderer {
  private final Configuration cfg;

  public TemplateRenderer() {
    cfg = new Configuration(Configuration.VERSION_2_3_31);
    cfg.setClassForTemplateLoading(this.getClass(), "/template");
    cfg.setDefaultEncoding("UTF-8");
  }

  /**
   * 製造檔案的魔法
   *
   * @param tplName
   * @param data
   * @param outputPath
   */
  public void render(String tplName, Map<String, Object> data, String outputPath) {
    addHelpers(data);
    try (Writer out = new FileWriter(new File(outputPath))) {
      Template tpl = cfg.getTemplate(tplName);
      tpl.process(data, out);
    } catch (IOException | TemplateException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 增加 ftl helper
   *
   * @param data
   */
  public void addHelpers(Map<String, Object> data) {
    data.put("toCamelCase", (TemplateMethodModelEx) arguments -> {
      if (arguments.isEmpty()) {
        throw new TemplateModelException("Missing argument");
      }
      return this.toCamelCase(arguments.get(0).toString(), false);
    });
  }

  /**
   * 變成駝峰的魔法
   *
   * @param s
   * @param capitalizeFirst
   * @return
   */
  public String toCamelCase(String s, boolean capitalizeFirst) {
    String[] parts = s.split("_");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < parts.length; i++) {
      String p = parts[i];
      sb.append(i == 0 && !capitalizeFirst
          ? p
          : p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase());
    }
    return sb.toString();
  }
}

