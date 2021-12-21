package com.patrick.dbcopy.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
//import com.patrick.dbcopy.bean.Students;
import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OnLineDBServiceImpl {

    @Autowired
    AbstractDBCopyMapper abstractDBCopyMapper;

    @Value("${DbCopyVerifyCfg.read-source-name}")
    private String readSourceName;

//    // 读取学生表数据
//    @DS("mysql_readsource")
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    public List<Students> readStudents() throws Exception{
//        String readDBName = abstractDBCopyMapper.selectDatabase();
//        if (readSourceName.compareTo(readDBName) != 0) {
//            System.out.println("当前链接的数据库是： " + readDBName + "----" + readSourceName);
//            throw new Exception("---failed change datasource to read source---");
//        }
//        List<Students> students = new ArrayList<>();
//        System.out.println("--数据库一致，开始从" + readDBName + ".Students中读取数据！---");
//        for (int i = 4; i < 104; i++) {
//            students.add(studentsServiceImpl.getById(i));
//        }
//        System.out.println(students);
//        return students;
//    }

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
