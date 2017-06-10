1.配置多个事务控制器(一个数据源对应一个事务):
``` xml
<?xml version="1.0" encoding="UTF-8"?>  
<beans xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"  
    xmlns:tx="http://www.springframework.org/schema/tx" xmlns:jdbc="http://www.springframework.org/schema/jdbc"  
    xmlns:context="http://www.springframework.org/schema/context"  
    xsi:schemaLocation="  
     http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd  
     http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd  
     http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-3.0.xsd  
     http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd  
     http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd">  
  
    <bean  
        class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">  
        <property name="locations">  
            <value>classpath:init-config.properties</value>  
        </property>  
    </bean>  
      
    <!-- enable component scanning (beware that this does not enable mapper scanning!) -->  
    <context:component-scan base-package="org.zhuc.mybatis" />  
  
    <!-- enable autowire -->  
    <context:annotation-config />  
  
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"  
        init-method="init" destroy-method="close">  
        <!-- 基本属性 url、user、password -->  
        <property name="url" value="${dataSource.url}" />  
        <property name="username" value="${dataSource.username}" />  
        <property name="password" value="${dataSource.password}" />  
        <property name="connectionProperties" value="${dataSource.driver}"></property>  
  
        <!-- 配置初始化大小、最小、最大 -->  
        <property name="initialSize" value="1" />  
        <property name="minIdle" value="1" />  
        <property name="maxActive" value="20" />  
  
        <!-- 配置获取连接等待超时的时间 -->  
        <property name="maxWait" value="60000" />  
  
        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->  
        <property name="timeBetweenEvictionRunsMillis" value="60000" />  
  
        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->  
        <property name="minEvictableIdleTimeMillis" value="300000" />  
  
        <property name="validationQuery" value="SELECT 'x'" />  
        <property name="testWhileIdle" value="true" />  
        <property name="testOnBorrow" value="false" />  
        <property name="testOnReturn" value="false" />  
  
        <!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->  
        <property name="poolPreparedStatements" value="true" />  
        <property name="maxPoolPreparedStatementPerConnectionSize"  
            value="20" />  
  
        <!-- 配置监控统计拦截的filters -->  
        <property name="filters" value="stat" />  
    </bean>  
  
    <!-- define the SqlSessionFactory -->  
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">  
        <property name="dataSource" ref="dataSource" />  
        <property name="typeAliasesPackage" value="org.zhuc.mybatis.domain" />  
    </bean>  
  
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">  
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>  
        <property name="basePackage" value="org.zhuc.mybatis.mapper" />  
    </bean>  
  
    <!-- transaction manager, use JtaTransactionManager for global tx -->  
    <bean id="transactionManager"  
        class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  
        <property name="dataSource" ref="dataSource" />  
        <qualifier value="isap" />  
    </bean>  
  
    <!-- 全注解方式   需加上@Transactional -->  
    <tx:annotation-driven transaction-manager="transactionManager" />  
      
    <!-- 事务控制的业务方法配 -->  
    <!--    
    <tx:advice id="txAdvice" transaction-manager="transactionManager">  
        <tx:attributes>  
            <tx:method name="get*" read-only="true" />  
            <tx:method name="page*" read-only="true" />  
            <tx:method name="list*" read-only="true" />  
            <tx:method name="*" />  
        </tx:attributes>  
    </tx:advice>  
    -->  
    <!-- 事务控制拦截 -->  
    <!--    
    <aop:config proxy-target-class="true">  
        <aop:advisor pointcut="execution(* org.zhuc..*.service..*Service.*(..))"  
            advice-ref="txAdvice" />  
    </aop:config>  
    -->  
      
    <!-- =================================================================== -->  
    <!-- 数据源2 -->  
    <bean id="dataSource2" class="com.alibaba.druid.pool.DruidDataSource"  
        init-method="init" destroy-method="close">  
        <!-- 基本属性 url、user、password -->  
        <property name="url" value="${dataSource2.url}" />  
        <property name="username" value="${dataSource2.username}" />  
        <property name="password" value="${dataSource2.password}" />  
        <property name="connectionProperties" value="${dataSource2.driver}"></property>  
  
        <!-- 配置初始化大小、最小、最大 -->  
        <property name="initialSize" value="1" />  
        <property name="minIdle" value="1" />  
        <property name="maxActive" value="20" />  
  
        <!-- 配置获取连接等待超时的时间 -->  
        <property name="maxWait" value="60000" />  
  
        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->  
        <property name="timeBetweenEvictionRunsMillis" value="60000" />  
  
        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->  
        <property name="minEvictableIdleTimeMillis" value="300000" />  
  
        <property name="validationQuery" value="SELECT 'x'" />  
        <property name="testWhileIdle" value="true" />  
        <property name="testOnBorrow" value="false" />  
        <property name="testOnReturn" value="false" />  
  
        <!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->  
        <property name="poolPreparedStatements" value="true" />  
        <property name="maxPoolPreparedStatementPerConnectionSize"  
            value="20" />  
  
        <!-- 配置监控统计拦截的filters -->  
        <property name="filters" value="stat" />  
    </bean>  
      
    <bean id="sqlSessionFactory2" class="org.mybatis.spring.SqlSessionFactoryBean">  
        <property name="dataSource" ref="dataSource2" />  
        <property name="typeAliasesPackage" value="org.zhuc.mybatis.domain2" />  
    </bean>  
      
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">  
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory2"/>  
        <property name="basePackage" value="org.zhuc.mybatis.mapper2" />  
    </bean>  
      
    <bean id="transactionManager2"  
        class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  
        <property name="dataSource" ref="dataSource2" />  
        <qualifier value="insurance" />  
    </bean>  
  
    <!-- 全注解方式 -->  
    <tx:annotation-driven transaction-manager="transactionManager2" />  
      
</beans>  
```
service类写法:

``` java
package org.zhuc.mybatis.service;  
  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional;  
import org.zhuc.mybatis.domain2.Flow;  
import org.zhuc.mybatis.mapper2.FlowMapper;  
  
/** 
 * @author zhuc 
 * @create 2013-3-11 下午1:19:03 
 */  
@Service("flowService")  
@Transactional(value = "insurance", rollbackFor = Exception.class)  
public class FlowService {  
  
    @Autowired  
    private FlowMapper flowMapper;  
  
    /** 
     * @param id 
     * @return 
     */  
    public Flow get(String id) {  
        return flowMapper.get(id);  
    }  
  
}  
```

2.动态切换   
3.多数据源的配置方法，既配置多个datasource，对应多个sqlsessionfactory，分别把不同的sqlsessionfactory配置到对应的MapperScannerConfigurer中去，mybatis会根据扫描的包路径，自动区分数据源
``` xml
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">    
    <property name="sqlSessionFactoryBeanName" value="oneSqlSessionFactory"></property>    
    <property name="basePackage" value="aaa.bbb.one.*.dao" />    
</bean> 

<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">    
    <property name="sqlSessionFactoryBeanName" value="twoSqlSessionFactory"></property>    
    <property name="basePackage" value="aaa.bbb.two.*.dao" />    
</bean> 
```

*注意*:spring默认对检查时异常不会回滚,对非检查时异常会进行回滚.如果需要对检查时异常进行回滚,直接使用rollbackfor=Exception.class即可.
