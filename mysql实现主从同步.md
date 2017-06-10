# mysql数据库实现主从
要求：
 - 主从版本一致
 - 初始化表，并在后台启动mysql
 - 修改root的密码  

1. 启用mysql二进制日志(主从都开启)  
```
#vi /etc/my.cnf   
[mysqld]  
log-bin=mysql-bin   #[必须]启用二进制日志  
server-id=222      #[必须]服务器唯一ID，默认是1，一般取IP最后一段  
systemctl restart mysqld.service  
```

2. 在主服务器上建立帐户并授权slave  
```
mysql>GRANT REPLICATION SLAVE ON *.* to 'mysync'@'%' identified by '123456';
```

3. 登录主服务器的mysql，查询master的状态   
> mysql>show master status;
   +------------------+----------+--------------+------------------+
   | File             | Position | Binlog_Do_DB | Binlog_Ignore_DB |
   +------------------+----------+--------------+------------------+
   | mysql-bin.000004 |      308 |              |                  |
   +------------------+----------+--------------+------------------+
   1 row in set (0.00 sec)    
    注：执行完此步骤后不要再操作主服务器MYSQL，防止主服务器状态值变化    
    
4. 配置从服务器Slave：
```
mysql>change master to master_host='192.168.145.222',master_user='mysync',master_password='q123456',
master_log_file='mysql-bin.000004',master_log_pos=308;   //注意不要断开，308数字前后无单引号.      
mysql>start slave;    //启动从服务器复制功能     
```

5. 检查从服务器复制功能状态
>mysql> show slave status\G     
              Slave_IO_State: Waiting for master to send event    
              Master_Host: 192.168.2.222  //主服务器地址   
              Master_User: mysync   //授权帐户名，尽量避免使用root    
              Master_Port: 3306    //数据库端口，部分版本没有此行    
              Connect_Retry: 60   
              Master_Log_File: mysql-bin.000004    
              Read_Master_Log_Pos: 600     //#同步读取二进制日志的位置，大于等于Exec_Master_Log_Pos
              Relay_Log_File: ddte-relay-bin.000003    
              Relay_Log_Pos: 251   
              Relay_Master_Log_File: mysql-bin.000004   
              Slave_IO_Running: Yes    //此状态必须YES   
              Slave_SQL_Running: Yes     //此状态必须YES   


