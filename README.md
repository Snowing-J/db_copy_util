#简介
这是一个可以完成跨库数据同步的基础工具，可广泛用于数据库的分表优化。
#Notes
##数据库方面
###配置问题
```
1.需要在.yaml文件中配置datasource，注意必须要配置primary datasource！具体信息详见.yaml文件。
2.切换数据源需要使用@DS("datasource_name"),此数据源必须要在.yaml中配置。
3.切换数据源后一定要校验数据源的一致性。
4.Spring的事务传播特性控制数据的一致性
 a.在不同的数据库中需要配置不同的事务来达到目的。
 b.只要开始就设置@Transactional(rollbackFor = Exception.class)
 c.读取的时候不需要回滚应该将事务挂起，使用@Transactional(propagation = Propagation.NOT_SUPPORTED)
 d.读取完返回后事务rollbackFor正常运行，不需要额外控制。
 e.数据库原有事务回导致数据库切换不成功，需要新开一个事务
   // 具体可以参考 https://baijiahao.baidu.com/s?id=1685490934094994913&wfr=spider&for=pc
```
###其他小坑
```
1.数据库名称不区分大小写，但是java严格区分大小写，注意在数据库名称匹配时的问题
```
