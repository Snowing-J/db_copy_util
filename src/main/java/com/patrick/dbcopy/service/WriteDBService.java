package com.patrick.dbcopy.service;

import java.util.List;
import java.util.Map;

public interface WriteDBService {

    public Integer writeDataByTableName(String tableName, List<Map<String, Object>> tableData) throws Exception;

}
