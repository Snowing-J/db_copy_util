package com.patrick.dbcopy.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import com.patrick.dbcopy.service.DBCopyService;
import com.patrick.dbcopy.service.ReadDBService;
import com.patrick.dbcopy.service.WriteDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Primary
@Service
public class DBCopyServiceImpl implements DBCopyService {

    @Autowired
    AbstractDBCopyMapper abstractDBCopyMapper;

    @Autowired
    ReadDBService readDBService;

    @Autowired
    WriteDBService writeDBService;

    @Value("${DbCopyVerifyCfg.write-source-name}")
    private String writeSourceName;

    @Value("${DbCopyVerifyCfg.read-source-name}")
    private String readSourceName;

    // 获取当前cpu的最大逻辑线程数
    private static final int THREADS = Runtime.getRuntime().availableProcessors() + 1;

    private ExecutorService executor = new ThreadPoolExecutor(
            THREADS,
            THREADS,
            5,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("dbCopyJob-thread-%d").build());
            // 此处删除了日志

    @Override
    public String copyAllTables(List<String> tableList) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(tableList.size());
        Map<String,Integer> affectedLines= new ConcurrentHashMap<>();
        for (String tableName:tableList) {
            executor.submit(()->{
                Integer affectedLine = -1;
                try{
                    affectedLine = copyTable(tableName);
                    System.out.println("---当前线程： " + executor);
                } catch (Exception e) {
//                    log.error("abstractCopy:{}Error",tableName,e);
                } finally {
                    affectedLines.put(tableName,affectedLine);
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        return "ok";
    }

    @Override
    public Integer copyTable(String tableName) throws Exception{
        List<Map<String, Object>> tableData = readDBService.readDataByTableName(tableName);
        System.out.println("---" + tableName + "中的数据如下：");
        System.out.println(tableData);
        Integer result = writeDBService.writeDataByTableName(tableName, tableData);
        return result;
    }

//
//    // 数据库原有事务会导致数据库切换不成功，需要新开一个事务
//    // 具体可以参考 https://baijiahao.baidu.com/s?id=1685490934094994913&wfr=spider&for=pc
//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//    @DS("mysql_writesource")
//    @Override
//    public Integer writeDataByTableName(String tableName, List<Map<String, Object>> tableData) throws Exception {
//        String writeDBName = abstractDBCopyMapper.selectDatabase();
//        if (writeSourceName.compareTo(writeDBName) != 0){
//            System.out.println("writeDBName： " + writeDBName + "，  writeSourceName： " + writeSourceName);
//            throw new Exception("---failed change datasource to write source---");
//        }
//        Integer result = -1;
//        System.out.println("--数据库一致，开始向" + writeDBName + "." + tableName +"中写入数据！---");
//
//        for (Map map : tableData){
//            result = abstractDBCopyMapper.insertByMap(tableName, map);
//        }
//        System.out.println("---数据写入成功！");
//        return result;
//    }

//    /**
//     * 读取tableName中的数据并返回
//     * @param tableName
//     * @return
//     */
//    @DS("mysql_readsource")
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    @Override
//    public List<Map<String, Object>> readDataByTableName(String tableName) {
//        System.out.println("--开始读取 tableName：%s 中的数据" + tableName);
//        return abstractDBCopyMapper.selectDataByTableName(tableName);
//    }
}
