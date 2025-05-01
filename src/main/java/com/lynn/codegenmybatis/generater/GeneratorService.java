package com.lynn.codegenmybatis.generater;


import com.lynn.codegenmybatis.generater.model.TableMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * @Author: Lynn on 2025/5/1
 */

@Service
public class GeneratorService {

  private final JdbcTemplate jdbc;
  private final TemplateRenderer renderer;

  @Value("${codegen.paths.vo}")
  private String voPath;
  @Value("${codegen.paths.dao}")
  private String daoPath;
  @Value("${codegen.paths.mapper}")
  private String mapperPath;
  @Value("${basePackage}")
  private String basePackage;

  public GeneratorService(JdbcTemplate jdbc, TemplateRenderer renderer) {
    this.jdbc = jdbc;
    this.renderer = renderer;
  }

  public void generate(String tableName) {
    String sql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS "
        + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";
    List<Map<String, Object>> rows = jdbc.queryForList(sql, tableName);
    TableMeta meta = new TableMeta();
    meta.setTableName(tableName);
    List<TableMeta.ColumnMeta> cols = new ArrayList<>();
    if (CollectionUtils.isEmpty(rows)) {
      throw new RuntimeException("Table not found: " + tableName);
    }
    for (var r : rows) {
      TableMeta.ColumnMeta col = new TableMeta.ColumnMeta();
      col.setName((String) r.get("COLUMN_NAME"));
      col.setJavaType(mapToJavaType((String) r.get("DATA_TYPE")));
      col.setComment((String) r.get("COLUMN_COMMENT"));
      cols.add(col);
    }
    meta.setColumns(cols);

    Map<String, Object> data = new HashMap<>();
    data.put("table", meta);

    String className = renderer.toCamelCase(tableName, true);
    data.put("basePackage", basePackage);
    renderer.render("vo.ftl", data, voPath + "/" + className + "Vo.java");
    renderer.render("dao.ftl", data, daoPath + "/" + className + "Dao.java");
    renderer.render("mapper.ftl", data, mapperPath + "/" + className + "Dao.xml");
  }

  /**
   * 對應 java 型別
   * @param sqlType
   * @return
   */
  private String mapToJavaType(String sqlType) {
    return switch (sqlType) {
      case "varchar", "text" -> "String";
      case "int", "tinyint" -> "Integer";
      case "bigint" -> "Long";
      case "datetime", "timestamp" -> "java.time.LocalDateTime";
      default -> "Object";
    };
  }

}
