package com.patrick.dbcopy.controller;

import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import com.patrick.dbcopy.service.CopyStruct;
import com.patrick.dbcopy.service.DBCopyService;
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
    AbstractDBCopyMapper abstractDBCopyMapper;

    @Autowired
    DBCopyService dbCopyService;

    @Autowired
    CopyStruct copyStruct;

    // 从配置文件注入管理员账户名称
    @Value("${DbCopyVerifyCfg.managers}")
    private List<String> dbMangerList;

    /**
     * 复制数据库中所有的表结构（包含索引、备注、引擎等，但不包含数据）
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/copyAllTableStruct")
    public String copyAllTableStruct(@Param("user") String user) throws Exception{
        // 判断人员权限
        if (!dbMangerList.contains(user)){
            String result = user + ": 您无权操作数据库";
            System.out.println(result);
            return result;
        }
        // 判断是否有正在操作的记录
        //使用redis缓存管理
        // 开始同步数据
        // 获取数据库中的所有需要copy的表名
        List<String> tablesList = abstractDBCopyMapper.selectAllTablesName();
//        System.out.println("---需要copy的表格如下：");
//        System.out.println(tablesList);
        // 同步表结构
        String result = copyStruct.copyAllTableStruct(tablesList);
//        System.out.println(result);
        return result;
    }

    /**
     * 同步所有的表（AllTables）
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
            //使用redis缓存管理
        // 开始同步数据
        // 获取数据库中的所有需要同步的表名
        List<String> tablesList = abstractDBCopyMapper.selectAllTablesName();
        System.out.println("---需要同步的表格如下：");
        System.out.println(tablesList);
        // 同步数据逻辑
        String result = dbCopyService.copyAllTables(tablesList);
        System.out.println(result);
        return result;
    }

    /**
     * 同步一个指定的表（tableName）
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/copyTable")
    public String copyTable(@Param("user") String user, @Param("tableName") String tableName) throws Exception{
        // 判断人员权限
        if (!dbMangerList.contains(user)){
            String result = user + ": 您无权操作数据库";
            System.out.println(result);
            return result;
        }
        // 判断是否有正在操作的记录
        // 使用redis缓存管理
        // 开始同步数据
        // 获取数据库中的所有需要同步的表名
//        List<String> tablesList = abstractDBCopyMapper.selectAllTablesName();
//        System.out.println("---需要同步的表格如下：");
        System.out.println(tableName);
        // 同步数据逻辑
        Integer r = dbCopyService.copyTable(tableName);
        String result = r == 1 ? "ok" : "erro";
        System.out.println(result);
        return result;
    }
}
