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

    /**
     * 向B库中同步A库中表名为tableName的表结构
     * @param A 需要同步的数据库名称
     * @param B 需要创建表的数据库名称
     * @param tableName 需要同步的表名
     * @return
     */
    void copyStruct(@Param("A") String A,
                       @Param("B") String B,
                       @Param("tableName") String tableName);
}
