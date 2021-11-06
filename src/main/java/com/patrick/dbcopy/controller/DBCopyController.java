package com.patrick.dbcopy.controller;

import com.patrick.dbcopy.bean.Students;
import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import com.patrick.dbcopy.service.impl.BetaDBServiceImpl;
import com.patrick.dbcopy.service.impl.DBCopyServiceImpl;
import com.patrick.dbcopy.service.impl.OnLineDBServiceImpl;
import com.patrick.dbcopy.service.impl.StudentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dbCopy")
public class DBCopyController {

    @Autowired
    StudentsServiceImpl studentsServiceImpl;

    @Autowired
    OnLineDBServiceImpl onLineDBServiceImpl;

    @Autowired
    BetaDBServiceImpl betaDBServiceImpl;

    @Autowired
    AbstractDBCopyMapper abstractDBCopyMapper;

    @Autowired
    DBCopyServiceImpl dbCopyServiceImpl;

    /**
     * 复制学生表（Students）
     * @throws Exception
     */
//    @Transactional(rollbackFor = Exception.class)
//    @RequestMapping("/copyStudents")
//    public void copyStudents() {
//        System.out.println("开始读取数据---");
//        List<Students> students = null;
//        try {
//            students = onLineDBServiceImpl.readStudents();
//            betaDBServiceImpl.writeStudents(students);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (!students.isEmpty()){
//        }
//    }

    /**
     * 暴力复制所有的表（AllTables）
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/copyAllTables")
    public void copyAllTables() throws Exception{


//    Todo:
//        1.获取所有的表格
//        2.根据表名称暴力同步
        List<String> tablesList = abstractDBCopyMapper.selectAllTablesName();
        System.out.println("---需要同步的表格如下：");
        System.out.println(tablesList);
        String result = dbCopyServiceImpl.copyAllTables(tablesList);
        System.out.println(result);
    }
}
