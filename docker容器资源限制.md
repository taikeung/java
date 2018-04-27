<center>docker对容器资源进行限制</center>
> 在使用 docker 运行容器时，一台主机上可能会运行几百个容器，这些容器虽然互相隔离，但是底层却使用着相同的 CPU、内存和磁盘资源。如果不对容器使用的资源进行限制，那么容器之间会互相影响，小的来说会导致容器资源使用不公平；大的来说，可能会导致主机和集群资源耗尽，服务完全不可用。  docker 作为容器的管理者，自然提供了控制容器资源的功能。正如使用内核的 namespace 来做容器之间的隔离，docker 也是通过内核的 cgroups 来做容器的资源限制。这篇文章就介绍如何使用 docker 来限制 CPU、内存和 IO，以及对应的 cgroups 文件。   

1.  CPU 资源
	- docker 限制 CPU Share: `-c --cpu-shares 512` 物理机根据512占所有的cpu-share的份额来分配cpu的使用率    
	-  限制容器能使用的 CPU 核数:`--cpus 1.5` 限制容器只能使用1.5个核心 ，如果多个容器都设置了 `--cpus` ，并且它们之和超过主机的 CPU 核数，并不会导致容器失败或者退出，这些容器之间会竞争使用 CPU，具体分配的 CPU 数量取决于主机运行情况和容器的 CPU share 值。也就是说 `--cpus `只能保证在 CPU 资源充足的情况下容器最多能使用的 CPU 数，docker 并不能保证在任何情况下容器都能使用这么多的 CPU（因为这根本是不可能的）
	-  限制容器运行在某些 CPU 核：`--cpuset-cpus=0,1`限制容器只能运行在前两个核心上，`--cpuset-cpus `参数可以和` -c --cpu-shares` 一起使用，限制容器只能运行在某些 CPU 核上，并且配置了使用率。     

2. 内存资源限制
	-  `-m --memory`:容器能使用的最大内存的大小，最小值为4m  
	-  `--memory-swap`:容器能使用的swap的大小
	-  `--memory-swappiness`:默认情况下，主机可以把容器使用的匿名页（anonymous page）swap 出来，你可以设置一个 0-100 之间的值，代表允许 swap 出来的比例
	-  `--memory-reservation`:设置一个内存使用的 soft limit，如果 docker 发现主机内存不足，会执行 OOM 操作。这个值必须小于 --memory 设置的值
	-  `--kernel-memory`:容器能够使用的 kernel memory 大小，最小值为 4m
	-  `--oom-kill-disable`:是否运行 OOM 的时候杀死容器。只有设置了 -m，才可以把这个选项设置为 false，否则容器会耗尽主机内存，而且导致主机应用被杀死
