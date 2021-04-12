FROM java:8
EXPOSE 8082
ADD target/novel-front-3.5.1.jar novel-front-3.5.1.jar
ENV dburl=""
ENV username=""
ENV	password=""
ENTRYPOINT ["java -Dspring.datasource.url=${dburl} -Dspring.datasource.username=${username} -Dspring.datasource.password=${password} -jar /target/novel-front-3.5.1.jar"]
