FROM zhtangsh/openjdk8-office:7.5

ENV VERSION 1.0.0-SNAPSHOT

COPY target/oc-basic-${VERSION}.jar  /app.jar

ENV JAVA_OPTS=""

ENV LC_ALL="C.UTF-8"

EXPOSE 8800

CMD ["sh", "-c" , "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]