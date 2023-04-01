
# Install Tomcat  & jdk 17 & ubuntu 22.04
FROM tomcat:10
# Copy source files to tomcat folder structure
COPY /target/todotask-1.war /usr/local/tomcat/webapps/todotask.war

EXPOSE 8080

CMD ["catalina.sh", "run"]