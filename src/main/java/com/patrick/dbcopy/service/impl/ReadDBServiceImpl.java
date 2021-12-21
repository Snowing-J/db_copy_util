package com.patrick.dbcopy.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import com.patrick.dbcopy.service.ReadDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Primary
@Service
public class ReadDBServiceImpl implements ReadDBService {

    @Autowired
    AbstractDBCopyMapper abstractDBCopyMapper;

    /**
     * 读取tableName中的数据并返回
     * @param tableName
     * @return
     */
    @DS("mysql_readsource")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Map<String, Object>> readDataByTableName(String tableName) {
        System.out.println("--开始读取 tableName：%s 中的数据" + tableName);
        return abstractDBCopyMapper.selectDataByTableName(tableName);
    }
}
