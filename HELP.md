# Read Me First

The following was discovered as part of building this project:

* The following dependencies are not known to work with Spring Native: 'MyBatis Framework, Spring Configuration
  Processor, Spring Boot DevTools'. As a result, your application may not work as expected.

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.6/maven-plugin/reference/html/#build-image)
* [MyBatis Framework](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.5.6/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Native Reference Guide](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.6/reference/htmlsingle/#using-boot-devtools)

### Guides

The following guides illustrate how to use some features concretely:

* [MyBatis Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Additional Links

These additional references should also help you:

* [Configure the Spring AOT Plugin](https://docs.spring.io/spring-native/docs/0.10.5/reference/htmlsingle/#spring-aot-maven)

## Spring Native

This project has been configured to let you generate either a lightweight container or a native executable.

### Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get started with Spring
Native. Docker should be installed and configured on your machine prior to creating the image,
see [the Getting Started section of the reference guide](https://docs.spring.io/spring-native/docs/0.10.5/reference/htmlsingle/#getting-started-buildpacks)
.

To create the image, run the following goal:

```
$ ./mvnw spring-boot:build-image
```

Then, you can run the app like any other container:

```
$ docker run --rm db_copy_until:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image. The GraalVM
native-image compiler should be installed and configured on your machine,
see [the Getting Started section of the reference guide](https://docs.spring.io/spring-native/docs/0.10.5/reference/htmlsingle/#getting-started-native-build-tools)
.

To create the executable, run the following goal:

```
$ ./mvnw package -Pnative
```

Then, you can run the app as follows:

```
$ target/db_copy_until
```
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