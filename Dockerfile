FROM openjdk:11
ADD /target/KinoCMS.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "KinoCMS.jar"]