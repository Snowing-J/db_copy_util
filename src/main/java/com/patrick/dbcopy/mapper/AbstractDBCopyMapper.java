package com.patrick.dbcopy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AbstractDBCopyMapper {

    String selectDatabase();

    List<String> selectAllTablesName();

    List<Map<String, Object>> selectDataByTableName(String tableName);

    Integer insertByMap(@Param("tableName") String tableName,
                        @Param("map") Map map);
}
