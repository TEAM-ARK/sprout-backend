REPOSITORY=/home/ubuntu/app
cd $REPOSITORY

JAR_NAME=inflearn-clone-back.jar
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')

if [ -z "$CURRENT_PID" ]
then
  echo "> No processes found to terminate !" >> /home/ubuntu/deploy.log
else
  echo "> sudo kill -9 $CURRENT_PID" >> /home/ubuntu/deploy.log
  sudo kill -9 "$CURRENT_PID" >> /home/ubuntu/deploy.log 2>&1
  sleep 5
fi

echo "> $JAR_PATH deployed !"
nohup java -jar -Dspring.profiles.active=prod $JAR_PATH >> /dev/null &

echo "[$(date)] server deployed !" >> /home/ubuntu/deploy.log