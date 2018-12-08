FROM java
RUN apt-get update
COPY . /
WORKDIR /  
RUN javac DockerConnectMySQL.java
CMD ["java", "-classpath", "mysql-connector-java-8.0.13.jar:.","DockerConnectMySQL"]
EXPOSE 80
