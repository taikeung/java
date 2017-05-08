#centos 7安装mysql：  
CentOS 7的yum源中貌似没有正常安装mysql时的mysql-sever文件，需要去官网上下载  
wget http://dev.mysql.com/get/mysql-community-release-el7-5.noarch.rpm  
rpm -ivh mysql-community-release-el7-5.noarch.rpm  
yum install mysql-community-server  
成功安装之后重启mysql服务  

service mysqld restart  
初次安装mysql是root账户是没有密码的  
设置密码的方法  

mysql -uroot  
mysql> set password for ‘root’@‘localhost’ = password('mypasswd');  
mysql> exit  

授权远程连接
mysql> grant all privileges on \*.* to 'root'@'%' identified by 'omni';
mysql> flush privileges;