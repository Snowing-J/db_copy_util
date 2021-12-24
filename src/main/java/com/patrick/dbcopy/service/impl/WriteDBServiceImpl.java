package com.patrick.dbcopy.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.patrick.dbcopy.mapper.*;
import com.patrick.dbcopy.service.WriteDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Primary
@Service
public class WriteDBServiceImpl implements WriteDBService {

    @Autowired
    private AbstractDBCopyMapper abstractDBCopyMapper;

    @Value("${DbCopyVerifyCfg.write-source-name}")
    private String writeSourceName;

    // 数据库原有事务回导致数据库切换不成功，需要新开一个事务
    // 具体可以参考 https://baijiahao.baidu.com/s?id=1685490934094994913&wfr=spider&for=pc
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DS("mysql_writesource")
    @Override
    public Integer writeDataByTableName(String tableName, List<Map<String, Object>> tableData) throws Exception {
        String writeDBName = abstractDBCopyMapper.selectDatabase();
        List<String> writeTableName = abstractDBCopyMapper.selectAllTablesName();

        if (writeSourceName.compareTo(writeDBName) != 0 || !writeTableName.contains(tableName) ){
//            System.out.println("writeDBName： " + writeDBName + "，  writeSourceName： " + writeSourceName);
//            System.out.println("--------writeDBName"+ writeTableName);
            throw new Exception("---failed change datasource to write source---");
        }
        Integer result = -1;
//        System.out.println("--数据库一致，开始向" + writeDBName + "." + tableName +"中写入数据！---");

        for (Map map : tableData){
            result = abstractDBCopyMapper.insertByMap(tableName, map);
        }
//        System.out.println("---数据写入成功！");
        return result;
    }
}
