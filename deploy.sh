BUILD_PATH="~/app/build/libs"
JAR_NAME="sprout.jar"

echo "> 배포를 시작합니다." >> /home/ubuntu/deploy.log

CURRENT_PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')

echo "> $CURRENT_PID 가 구동 중입니다!" >> /home/ubuntu/deploy.log
if [ -z $CURRENT_PID ]; then
        echo "> 현재 구동중인 애플리케이션이 없습니다 !" >> /home/ubuntu/deploy.log
else
        echo "> 현재 구동중인 애플리케이션을 종료합니다 !" >> /home/ubuntu/deploy.log
        echo "> kill -9 $CURRENT_PID" >> /home/ubuntu/deploy.log
        kill -9 $CURRENT_PID
        sleep 10
fi

chmod +x $BUILD_PATH/$JAR_NAME

nohup java -jar -Djasypt.encryptor.password="$JASYPT_PASSWORD" -Dspring.profiles.active=prod $BUILD_PATH/$JAR_NAME >> /dev/null &

echo "[$(date)] 배포 완료 !" >> /home/ubuntu/deploy.log
