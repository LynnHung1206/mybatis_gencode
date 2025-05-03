package ${basePackage}.vo;

import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;

/**
* ${table.tableName} 的 Vo
*/
@Getter
@Setter
@TableName("${table.tableName}")
public class  ${toCamelCaseFirstCapitalize(table.tableName)}Vo {
<#list table.columns as c>
    /** ${c.comment} */
    private ${c.javaType} ${toCamelCase(c.name)};
</#list>

}
