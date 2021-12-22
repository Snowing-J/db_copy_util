package com.patrick.dbcopy.service;

import java.util.List;
import java.util.Map;

public interface ReadDBService {

    public List<Map<String, Object>> readDataByTableName(String tableName);

}
