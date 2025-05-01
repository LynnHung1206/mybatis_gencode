package com.lynn.codegenmybatis;

import com.lynn.codegenmybatis.generater.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class CodeGenMybatisApplication implements CommandLineRunner {

  private final GeneratorService generator;

  public static void main(String[] args) {
    SpringApplication.run(CodeGenMybatisApplication.class, args);
  }

  @Override
  public void run(String... args) {
    Scanner sc = new Scanner(System.in, "UTF-8");
    System.out.println("=== MyBatis Codegen ===");
    System.out.println("請輸入 table 名稱（輸入 q 離開）：");
    while (true) {
      System.out.print("> ");
      String table = sc.nextLine().trim();
      if ("q".equalsIgnoreCase(table) || table.isEmpty()) {
        break;
      }
      try {
        generator.generate(table);
        System.out.println("✔ 已生成 Table `" + table);
      } catch (Exception e) {
        System.err.println("✖ 生成失敗: " + e.getMessage());
      }
    }
    sc.close();
    System.out.println("程式結束");
    System.exit(0);
  }
}
