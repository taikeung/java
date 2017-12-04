mongodb安装
- 下载解压并配置环境变量
>export MONGO_HOME=/home/data/software/mongodb/mongodb-linux-x86_64-rhel70-3.4.10\
>export PATH=$JAVA_HOME/bin:$PATH:$HOME/bin:$MONGO_HOME/bin\
- 启动创建用户和密码授权
> mongod
> use admin
> db.createUser({user: "fid", pwd: "Fid123456",roles: [ { role: "userAdminAnyDatabase", db: "admin" }]})
> db.auth('fid','Fid123456')
- 在mongodb的bin目录下创建mongodb.conf并在其中添加：
    ```
    dbpath=/usr/context/mongodb/data/db/  
    logpath=/usr/context/mongodb/data/log/mongodb.log  
    logappend=true  
    port=27017  
    fork=true  
    nohttpinterface=true  
    auth=true 
    ```
- 以授权的方式启动
> mongod --config /home/data/software/mongodb/bin/mongodb.conf
> mongo  -u root -p Fid123456 --authenticationDatabase admin

