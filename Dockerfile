FROM openjdk:19
ADD target/Student_project.jar Student_project.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","Student_project.jar"]