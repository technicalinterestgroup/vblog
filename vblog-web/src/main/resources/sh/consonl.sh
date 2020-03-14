BUILD_ID=DONTKILLME
PROJECT_HOME="/home/vblog"
AP=$PROJECT_HOME/config/application.properties

sh $PROJECT_HOME/bat.sh stop
cd $PROJECT_HOME/&& rm -rf vblog-web
cd $PROJECT_HOME/&& rm -rf vblog-web-0.0.1-SNAPSHOT.jar
cd $PROJECT_HOME/&& rm -rf LOG_HOME_IS_UNDEFINED
cd $PROJECT_HOME/&& rm -rf conf*
cd $PROJECT_HOME/ && unzip -o vblog-web-0.0.1-SNAPSHOT.zip
cd $PROJECT_HOME/vblog-web
mv vblog-web-0.0.1-SNAPSHOT.jar $PROJECT_HOME/vblog-web-0.0.1-SNAPSHOT.jar
mv config $PROJECT_HOME/config
cd $PROJECT_HOME/&& rm -rf vblog-web
cd $PROJECT_HOME/&& rm -rf vblog-web-0.0.1-SNAPSHOT.zip

sh $PROJECT_HOME/bat.sh stop
sleep 1
sh $PROJECT_HOME/bat.sh start
