FROM maven:3.8.6-amazoncorretto-17 AS build

# Copy pom.xml to the image
COPY pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml verify clean --fail-never

# Copy the source code
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml package

# Install Tomcat  & jdk 17 & ubuntu 22.04
FROM tomcat:10
# Copy source files to tomcat folder structure
COPY --from=build /home/app/target/todotask-1.war /usr/local/tomcat/webapps/todotask.war
EXPOSE 8080

CMD ["catalina.sh", "run"]