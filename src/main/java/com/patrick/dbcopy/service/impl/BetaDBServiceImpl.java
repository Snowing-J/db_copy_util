package com.patrick.dbcopy.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.patrick.dbcopy.bean.Students;
import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import com.patrick.dbcopy.mapper.StudentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BetaDBServiceImpl {

    @Autowired
    private AbstractDBCopyMapper abstractDBCopyMapper;

    @Autowired
    private StudentsMapper studentsMapper;

    @Value("${DbCopyVerifyCfg.write-source-name}")
    private String writeSourceName;

    // 数据库原有事务回导致数据库切换不成功，需要新开一个事务
    // 具体可以参考 https://baijiahao.baidu.com/s?id=1685490934094994913&wfr=spider&for=pc
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DS("mysql_writesource")
    public void writeStudents(List<Students> students) throws Exception{
        String writeDBName = abstractDBCopyMapper.selectDatabase();
        if (writeSourceName.compareTo(writeDBName) != 0){
            System.out.println("writeDBName： " + writeDBName + "，  writeSourceName： " + writeSourceName);
            throw new Exception("---failed change datasource to write source---");
        }
        System.out.println("--数据库一致，开始向" + writeDBName + ".Students中写入数据！---");
        for (Students student : students){
            studentsMapper.insert(student);
        }
        System.out.println("---数据写入成功！");
        return;
    }
}
