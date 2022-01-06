BUILD_PATH="~/app/build/libs"
JAR_NAME="sprout.jar"

echo "> 배포를 시작합니다." >>/home/ubuntu/deploy.log

CURRENT_PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')
if [ -z $CURRENT_PID ]; then
  echo "> 현재 구동중인 애플리케이션이 없습니다 !" >>/home/ubuntu/deploy.log
else
  echo "> 현재 구동중인 애플리케이션 $CURRENT_PID 을 종료합니다 !" >>/home/ubuntu/deploy.log
  kill -9 $CURRENT_PID
  sleep 10
fi

echo "> 배포를 시작합니다 !" >>/home/ubuntu/deploy.log
echo "> $BUILD_PATH/$JAR_NAME 에 실행 권한을 부여합니다 !" >>/home/ubuntu/deploy.log
chmod +x $BUILD_PATH/$JAR_NAME

echo "> 애플리케이션을 실행합니다 !" >>/home/ubuntu/deploy.log
nohup java -jar -Djasypt.encryptor.password="$JASYPT_PASSWORD" -Dspring.profiles.active=prod $BUILD_PATH/$JAR_NAME >>/dev/null &

echo "[$(date)] 배포 완료 !" >>/home/ubuntu/deploy.log
