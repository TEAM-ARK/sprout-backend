JAR_NAME="inflearn-clone-back.jar"

echo "> 현재 구동중인 애플리케이션 확인 중..." >> /home/ubuntu/deploy.log

CURRENT_PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')

echo "$CURRENT_PID"
if [ -z $CURRENT_PID ]; then
        echo "> 현재 구동중인 애플리케이션이 없습니다 !" >> /home/ubuntu/deploy.log
else
        echo "> 구동중인 애플리케이션을 종료합니다 !" >> /home/ubuntu/deploy.log
        echo "> kill -9 $CURRENT_PID" >> /home/ubuntu/deploy.log
        kill -9 $CURRENT_PID
        sleep 3
fi

chmod -x /home/ubuntu/app/build/libs/$JAR_NAME

nohup java -jar -Djasypt.encryptor.password="$JASYPT_PASSWORD" -Dspring.profiles.active=prod /home/ubuntu/app/build/libs/$JAR_NAME >> /dev/null &
echo "[$(date)] 배포 완료 !" >> /home/ubuntu/deploy.log
