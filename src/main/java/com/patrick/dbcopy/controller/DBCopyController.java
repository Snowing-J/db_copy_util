package com.patrick.dbcopy.controller;

import com.patrick.dbcopy.bean.Students;
import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import com.patrick.dbcopy.service.impl.BetaDBServiceImpl;
import com.patrick.dbcopy.service.impl.DBCopyServiceImpl;
import com.patrick.dbcopy.service.impl.OnLineDBServiceImpl;
import com.patrick.dbcopy.service.impl.StudentsServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    // 从配置文件注入管理员账户名称
    @Value("${DbCopyVerifyCfg.managers}")
    private List<String> dbMangerList;

    /**
     * 复制学生表（Students）
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/copyStudents")
    public void copyStudents() {
        System.out.println("开始读取数据---");
        List<Students> students = null;
        try {
            students = onLineDBServiceImpl.readStudents();
            betaDBServiceImpl.writeStudents(students);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!students.isEmpty()){
        }
    }

    /**
     * 暴力同步所有的表（AllTables）
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/copyAllTables")
    public String copyAllTables(@Param("user") String user) throws Exception{
        // 判断人员权限
        if (!dbMangerList.contains(user)){
            String result = user + ": 您无权操作数据库";
            System.out.println(result);
            return result;
        }
        // 判断是否有正在操作的记录

        // 开始同步数据
        // 获取数据库中的所有需要同步的表名
        List<String> tablesList = abstractDBCopyMapper.selectAllTablesName();
        System.out.println("---需要同步的表格如下：");
        System.out.println(tablesList);
        // 同步数据逻辑
        String result = dbCopyServiceImpl.copyAllTables(tablesList);
        System.out.println(result);
        return result;
    }
}
