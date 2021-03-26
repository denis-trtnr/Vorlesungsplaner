FROM java:8
WORKDIR /
ADD target/todos-0.0.1-SNAPSHOT.jar //
EXPOSE 3010
ENTRYPOINT [ "java", "-jar", "/todos-0.0.1-SNAPSHOT.jar"]