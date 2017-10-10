使用log4j接管tomcat日志:
- 将tomcat-juli.jar放置在tomcat/bin目录中，替换原来的
- 将tomcat-juli-adapters.jar、log4j-1.2.17.jar、log4j.properties放置在tomcat/lib目录下
- 删除tomcat/conf目录下的logging.properties文件
- 重启tomcat