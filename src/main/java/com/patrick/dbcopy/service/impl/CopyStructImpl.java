package com.patrick.dbcopy.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import com.patrick.dbcopy.service.CopyStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Service
public class CopyStructImpl implements CopyStruct {

    @Autowired
    AbstractDBCopyMapper abstractDBCopyMapper;

    @Value("${DbCopyVerifyCfg.read-source-name}")
    private String readSourceName;

    @Value("${DbCopyVerifyCfg.write-source-name}")
    private String writeSourceName;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @DS("mysql_writesource")
    @Override
    public String copyAllTableStruct(List<String> tablesList) {
        String writeDBName = abstractDBCopyMapper.selectDatabase();
        if (writeSourceName.compareTo(writeDBName) != 0){
//            System.out.println("writeDBName： " + writeDBName + "，  writeSourceName： " + writeSourceName);
            // TODO：注意返回值，或者日志处理
            return "Error";
        }
//        System.out.println("--数据库一致，开始同步表结构！---");
        for (String tableName : tablesList) {
            // 把读库中的表结构同步到写库中
            abstractDBCopyMapper.copyStruct(readSourceName, writeSourceName, tableName);
        }
        List<String> newTablesList = abstractDBCopyMapper.selectAllTablesName();
        // 通过比较数据库中的表名是否一致确认是否完成表结构同步
        return tablesList.equals(newTablesList) ? "OK" : "Error";
    }

}
