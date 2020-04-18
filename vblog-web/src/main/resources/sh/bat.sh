
case $1 in
start)
    echo "start   vblog-web-0.0.1-SNAPSHOT.jar "
    nohup java -jar   vblog-web-0.0.1-SNAPSHOT.jar >> vblog.log 2>&1 &
    echo "部署完毕！"
;;

stop)
    echo "stop vblog Application"
    pid=`ps -ef | grep  vblog-web-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
    if [ -n "$pid" ]
    then
    echo "vblog-0.0.1-SNAPSHOT is running"
    echo "kill -9 的pid:" $pid
    kill -9 $pid
        echo "关闭进程："$pid
    fi

;;
*)
    if [ ${1:0:1} != '-' ]; then echo "bad command "; exit 1; fi
;;
esac
exit 0