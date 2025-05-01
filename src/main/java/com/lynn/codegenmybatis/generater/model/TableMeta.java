package com.lynn.codegenmybatis.generater.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Lynn on 2025/5/1
 */
@Getter
@Setter
public class TableMeta {
  private String tableName;
  private List<ColumnMeta> columns;

  @Getter
  @Setter
  public static class ColumnMeta {
    private String name;
    private String javaType;
    private String comment;
  }
}
