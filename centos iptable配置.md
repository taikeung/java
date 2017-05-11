# centos7 关闭firewall安装iptables并配置   

- 一、配置防火墙，开启80端口、3306端口 ,CentOS 7.0默认使用的是firewall作为防火墙，这里改为iptables防火墙.   

1.关闭firewall：  

	systemctl stop firewalld.service #停止firewall  
    
	systemctl disable firewalld.service #禁止firewall开机启动  
    
2.安装iptables防火墙    

yum install iptables-services #安装    

vi /etc/sysconfig/iptables #编辑防火墙配置文件   


    :INPUT ACCEPT [0:0]  
    :FORWARD ACCEPT [0:0]  
    :OUTPUT ACCEPT [0:0]   
    -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT  
    -A INPUT -p icmp -j ACCEPT  
    -A INPUT -i lo -j ACCEPT   
    -A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT  
    -A INPUT -m state --state NEW -m tcp -p tcp --dport 80 -j ACCEPT   
    -A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT   
    -A INPUT -j REJECT --reject-with icmp-host-prohibited   
    -A FORWARD -j REJECT --reject-with icmp-host-prohibited   
    COMMIT   
:wq! #保存退出   
systemctl restart iptables.service #最后重启防火墙使配置生效  
systemctl enable iptables.service #设置防火墙开机启动  

- 二、关闭SELINUX    

    vi /etc/selinux/config    
    \#SELINUX=enforcing #注释掉    
    \#SELINUXTYPE=targeted #注释掉    
    SELINUX=disabled #增加   
    :wq! #保存退出    
    setenforce 0 #使配置立即生效    
    
---