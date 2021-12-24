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
                    System.out.println("---当前线程： " + Thread.currentThread().getName());
                } catch (Exception e) {
//                    log.error("abstractCopy:{}Error",tableName,e);
                } finally {
                    // TODO:返回值问题
                    affectedLines.put(tableName,affectedLine);
                    countDownLatch.countDown();
                    System.out.println("-----affectedLines: " + affectedLines);
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


}
