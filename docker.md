# <center> docker</center>
1. docker命令：	
    - docker images:查看本地存在的docker镜像
    - docker run -idt --name node centos  -p 10080:80 -p 10022:22 /bin/bash:运行docker镜像,centos是镜像的名称，创建docker容器
    - docker ps:查看运行的容器 (默认查看运行的容器) -a 查看所有的容器(包括未运行的容器) -a -q(所有未运行的容器)
    - docker attach f25c1bcb60dd:进入容器id为f25c1bcb60dd的容器
    - exit：进入容器中的linux后，需要退出，但是同时会关闭容器，如果想不关闭容器可以使用快捷键ctrl+p+q.  
    - docker start/stop containerId or containerName:启动容器
    - docker kill  containerId or containerName:杀死容器
    - docker rm containerId or containerName：删除容器
    - docker rmi imageId：删除镜像
    - docker rm \`docker ps -a -q\`:删除所有的未启动的容器
2. ssh连接docker容器:  
	- 修改root用户密码:passwd
	- 安装openssh:yum install passwd openssl openssh-server -y
    - 启动ssh：/usr/sbin/sshd -D   
    	ssh-keygen -q -t rsa -b 2048 -f /etc/ssh/ssh_host_rsa_key -N ' '   
        ssh-keygen -q -t ecdsa -f /etc/ssh/ssh_host_ecdsa_key -N ' '		
        ssh-keygen -t dsa -f /etc/ssh/ssh_host_ed25519_key  -N ' '
    - docker commit containerId 保存的镜像名称
3. 搭建镜像仓库:
	- 拉取镜像仓库的镜像:docker pull registry
	- 启动镜像：docker run -d -p 5000:5000 -v /opt/data/registry:/tmp/registry registry  
		默认情况下,上传的镜像会放在docker容器的目录下，但是一旦容器删掉镜像仓库也会删掉，所以我们一般情况下会指定本地一个		目录挂载到容器的/tmp/registry下。
    - 修改需要上传镜像的tag，方便后续上传：docker tag busybox 192.168.112.136:5000/busybox
    - 上传镜像：docker push 192.168.112.136:5000/busybox
    - 可能出现的问题:The push refers to a repository [hub.docker.jiankunking.io:5000/haproxy]   
		Get https://hub.docker.jiankunking.io:5000/v1/_ping: http: server gave HTTP response to HTTPS client    
        解决办法：在”/etc/docker/“目录下，创建”daemon.json“文件。在文件中写入：   
        {   
   			 "insecure-registries": [   
        		"hub.docker.jiankunking.io:5000"   
    		]   
		}  
        重启docker:systemctl restart docker.service
    - 查看镜像仓库中的镜像：浏览器地址栏输入http://120.25.79.217:5000/v2/_catalog