package com.druidelf.novelbackstagemanagement.mapper.management;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CommonManagementMapper {
    @Select("${sqlString}")
    Integer anyInteger(@Param("sqlString") String sqlString);
    @Select("${sqlString}")
    Double anyDouble(@Param("sqlString") String sqlString);
    @Select("${sqlString}")
    String anyString(@Param("sqlString") String sqlString);
    @Select("${sqlString}")
    BigDecimal anyBigDecimal(@Param("sqlString") String sqlString);
    @Select("${sqlString}")
    Object anyObject(@Param("sqlString") String sqlString);
    @Select("${sqlString}")
    Map<String,Object> anyStringMap(@Param("sqlString") String sqlString);
    @Select("${sqlString}")
    List<String> anyStringArray(@Param("sqlString") String sqlString);
    @Select("${sqlString}")
    List<Map<String,Object>> anyObjectList(@Param("sqlString") String sqlString);
}
