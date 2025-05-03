<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.dao.${table.tableName?cap_first}Dao">
    <resultMap id="BaseResultMap" type="${basePackage}.vo.${toCamelCaseFirstCapitalize(table.tableName)}Vo">
        <#list table.columns as c>
            <result column="${c.name}" property="${toCamelCase(c.name)}" />
        </#list>
    </resultMap>
</mapper>