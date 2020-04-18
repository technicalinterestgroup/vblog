#!/bin/bash
#重启脚本
echo "Starting vblog Application"
pid=`ps -ef | grep  vblog-web-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
    echo "vblog-0.0.1-SNAPSHOT is running"
    echo "kill -9 的pid:" $pid
    kill -9 $pid
	echo "关闭进程："$pid
fi
	sleep 3
	
	#echo "授予当前用户权限"
	#chmod 777 /ganinfo/system/system-0.0.1-SNAPSHOT.jar
	echo "starting   vblog-web-0.0.1-SNAPSHOT.jar "
    nohup java -jar   vblog-web-0.0.1-SNAPSHOT.jar >> vblog.log 2>&1 &
	
echo "部署完毕！"
	

