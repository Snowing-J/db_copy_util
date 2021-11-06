package com.patrick.dbcopy.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.patrick.dbcopy.mapper.AbstractDBCopyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class DBCopyServiceImpl {

    @Autowired
    AbstractDBCopyMapper abstractDBCopyMapper;

    @Autowired
    OnLineDBServiceImpl onLineDBService;

    @Autowired
    BetaDBServiceImpl betaDBService;

    private static final int THREADS = Runtime.getRuntime().availableProcessors() + 1;

    private ExecutorService executor = new ThreadPoolExecutor(
            THREADS,
            THREADS,
            5,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("dbCopyJob-thread-%d").build());
            // 此处删除了日志

    public String copyAllTables(List<String> tableList) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(tableList.size());
        Map<String,Integer> affectedLines= new ConcurrentHashMap<>();
        for (String tableName:tableList) {
            executor.submit(()->{
                Integer affectedLine = -1;
                try{
                    affectedLine = copyTable(tableName);
                    System.out.println("---当前线程： " + Thread.currentThread().getName());
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

    private Integer copyTable(String tableName) throws Exception{
        List<Map<String, Object>> tableData = onLineDBService.readDataByTableName(tableName);
//        System.out.println("---当前线程： " + Thread.currentThread().getName());
//        System.out.println("---" + tableName + "中的数据如下：");
//        System.out.println(tableData);
//        System.out.println("---当前线程： " + Thread.currentThread().getName());
        Integer result = betaDBService.writeDataByTableName(tableName, tableData);
        return result;
    }
}
