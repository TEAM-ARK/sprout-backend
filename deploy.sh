BUILD_PATH="/home/ubuntu/sprout/build/libs"
JAR_NAME="sprout.jar"

echo ">[$(date)] ===========================================================================" >>/home/ubuntu/deploy.log
echo ">[$(date)] 배포를 시작합니다." >>/home/ubuntu/deploy.log

CURRENT_PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')
if [ -z $CURRENT_PID ]; then
  echo ">[$(date)] 현재 구동중인 애플리케이션이 없습니다 !" >>/home/ubuntu/deploy.log
else
  echo ">[$(date)] 현재 구동중인 애플리케이션 $CURRENT_PID 을 종료합니다 !" >>/home/ubuntu/deploy.log
  kill -9 $CURRENT_PID
  sleep 10
fi

echo ">[$(date)] $BUILD_PATH/$JAR_NAME 에 실행 권한을 부여합니다 !" >>/home/ubuntu/deploy.log
chmod +x $BUILD_PATH/$JAR_NAME

echo ">[$(date)] $BUILD_PATH/$JAR_NAME 을 실행합니다 !" >>/home/ubuntu/deploy.log
nohup java -jar -Djasypt.encryptor.password="$JASYPT_PASSWORD" -Dspring.profiles.active=prod $BUILD_PATH/$JAR_NAME >>/dev/null &

CURRENT_PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')
if [ -z $CURRENT_PID ]; then
  echo ">[$(date)] 실행에 성공했습니다 ! PID: $CURRENT_PID" >>/home/ubuntu/deploy.log
else
  echo ">[$(date)] 실행에 실패했습니다 ! 실행 로그를 확인하십시오 !" >>/home/ubuntu/deploy.log
fi
