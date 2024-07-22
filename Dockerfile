FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/github-repo-list-0.0.1-SNAPSHOT.jar /app/github-repo-list.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "github-repo-list.jar"]
