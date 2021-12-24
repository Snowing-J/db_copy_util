package com.patrick.dbcopy.service;

import java.util.List;

public interface DBCopyService {

    public String copyAllTables(List<String> tableList) throws Exception;

    public Integer copyTable(String tableName) throws Exception;

}
