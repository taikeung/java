# maven远程部署tomcat
1. 修改远程tomcat下的tomcat-users.xml:
``` java
<role rolename="manager-gui"/>  
<role rolename="manager-script"/>  
<user username="master" password="omni" roles="manager-gui, manager-script" />
```
2. Maven工程中pom.xml引入tomcat7插件：
``` java
<plugin>  
	<groupId>org.apache.tomcat.maven</groupId>  
	<artifactId>tomcat7-maven-plugin</artifactId>  
	<version>2.2</version>  
	<configuration>  
		<url>http://47.92.0.76:80/manager/text</url>  
		<server>master</server>
		<!-- 此处的名字是项目发布的工程名 --> 
		<path>/${projectName}</path>  
	</configuration>  
</plugin>  
```
3.  在本地maven的setttings.xml文件中，属性<servers><servers>中添加如下配置：
```
<server>  
	<id>master</id>  
	<username>master</username>  
	<password>omni</password>  
</server>
```
4. mvn-tomcat命令：
- mvn tomcat:deploy
- mvn tomcat:undeploy
- mvn tomcat:start
- mvn tomcat:stop
- mvn tomcat:redeploy

5. 部署时出现的问题：
- web.xml which will be ignored  
解决方法：  
pom.xml增加  
```
<plugin> 
	<groupId>org.apache.maven.plugins</groupId> 
		<artifactId>maven-war-plugin</artifactId> 
		<version>2.1.1</version> 
		<configuration> 
			<packagingExcludes>WEB-INF/web.xml</packagingExcludes>
		</configuration> 
 </plugin>
```