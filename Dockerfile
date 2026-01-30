# 1. Use Java 17 LTS (lightweight Alpine version)
FROM eclipse-temurin:17-jdk-alpine

# 2. Copy jar file into default docker vm
COPY target/rag-chat-storage-0.0.1-SNAPSHOT.jar /usr/app/

# 2. Set working directory
WORKDIR /usr/app/

# 5. Run app
ENTRYPOINT ["java", "-jar", "rag-chat-storage-0.0.1-SNAPSHOT.jar"]