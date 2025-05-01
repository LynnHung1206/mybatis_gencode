package ${basePackage}.dao;

import ${basePackage}.vo.${toCamelCaseFirstCapitalize(table.tableName)}Vo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface ${toCamelCaseFirstCapitalize(table.tableName)}Dao extends BaseMapper<${toCamelCaseFirstCapitalize(table.tableName)}Vo>{

}
