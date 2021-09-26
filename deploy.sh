JAR_NAME="inflearn-clone-back.jar"
CONF_PATH="/etc/nginx/nginx.conf"

echo "> Green 애플리케이션 확인 중..." >> /home/ubuntu/deploy.log

CURRENT_PID=$(ps -ef | grep 8081 | grep -v grep | awk '{print $2}')

echo "> $CURRENT_PID 가 구동 중입니다!" >> /home/ubuntu/deploy.log
if [ -z $CURRENT_PID ]; then
        echo "> 현재 구동중인 애플리케이션이 없습니다 !" >> /home/ubuntu/deploy.log
else
        echo "> Green과 Nginx의 연결을 해제합니다 !" >> /home/ubuntu/deploy.log
        sed -i "s/        server localhost:8081;/#        server localhost:8081;/g" $CONF_PATH

        echo "> Nginx Reload !" >> /home/ubuntu/deploy.log
        sudo systemctl reload nginx

        echo "> Connection Time Out까지 30초 대기 중 !" >> /home/ubuntu/deploy.log
        sleep 30

        echo "> 구동중인 애플리케이션을 종료합니다 !" >> /home/ubuntu/deploy.log
        echo "> kill -9 $CURRENT_PID" >> /home/ubuntu/deploy.log
        kill -9 $CURRENT_PID
        sleep 3
fi

chmod -x /home/ubuntu/app/build/libs/$JAR_NAME

nohup java -jar -Dserver.port=8081 -Djasypt.encryptor.password="$JASYPT_PASSWORD" -Dspring.profiles.active=prod /home/ubuntu/app/build/libs/$JAR_NAME >> /dev/null &
echo "[$(date)] 배포 완료 !" >> /home/ubuntu/deploy.log

echo "> Green과 Nginx를 다시 연결합니다 !" >> /home/ubuntu/deploy.log
sed -i "s/#        server localhost:8081;/        server localhost:8081;/g" $CONF_PATH

echo "> Blue와 Nginx의 연결을 해제합니다 !" >> /home/ubuntu/deploy.log
sed -i "s/        server localhost:8080;/#        server localhost:8080;/g" $CONF_PATH

echo "> Nginx Reload !" >> /home/ubuntu/deploy.log
sudo systemctl reload nginx