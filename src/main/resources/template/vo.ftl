package ${basePackage}.vo;

import lombok.Getter;
import lombok.Setter;


/**
* ${table.tableName} 的 Vo
*/
@Getter
@Setter
public class ${table.tableName?cap_first}Vo {
<#list table.columns as c>
    /** ${c.comment} */
    private ${c.javaType} ${toCamelCase(c.name)};
</#list>

}
