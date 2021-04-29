FROM openjdk:11
WORKDIR /kinocms
ADD /target/KinoCMS.jar /kinocms
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "KinoCMS.jar"]