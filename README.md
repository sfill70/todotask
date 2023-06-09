When run manually, flush is spring.datasource.url=jdbc:mysql://your IP:your port/todo 
and only use !!!TomCat 10.1 !!!
The table was created using flywaydb, file migration db/migration/V1__create_tables.sql, this file generated by the Workbench.

## To run project
1. Make sure you have installed docker and ports 8080, 3306 are free
2. Open a command line in the location where the application is installed and run the command:
   mvn clean install      -to check that the project is building normally
   
   if *.war file is successfully created
   
   Windows
   
   docker compose up
   
   Ubuntu
   
   sudo docker compose up
   
   
   If something went wrong, you can try to do this:
   
   sudo su
   
   docker compose up

3. The application will download all dependencies and create a docker container. Docker container will be launched automatically
4. After the successful completion of the previous point, the application will be available at the link:
http://localhost:8080/todotask/

| Command  | Method | Address        | Description                                                                                                |
|----------|--------|----------------|------------------------------------------------------------------------------------------------------------|
| Show all | GET    | <app>/         | Show all existing in database tasks                                                                        |
| Add      | POST   | <app>/         | Create new task with description and status                                                                |
| Show     | GET    | <app>/{id}     | Show task with ID, where {id} - id of the task which you want to see                                       |
| Delete   | POST   | <app>/del/{id} | Delete task with ID, where {id} - id of the task which you want to delete. Use modal window task_view.html |
| Delete   | POST   | <app>/dlt/     | Delete task, ID is passed in the RequestParam(value = "id"...). Use modal window index.html                |
| Edit     | POST   | <app>/put/{id} | Edit task with ID, where {id} - id of the task which you want to change. use task_put.html                 |
| Edit     | POST   | <app>/put/{id} | Edit task with ID, where {id} -id of the task which you want to change. use modal window task_put.html     |
| Search   |        | <app>/search   | Search REST description && status.  Search MVC description. Search MVC status.                             |

## Technologies used
- Spring
- Thymeleaf
- Docker
- Tomcat
- MySQL
- flyway

start page

![Image alt](https://github.com/sfill70/todotask/blob/master/src/main/resources/static/img/td_start.png)

add page

![Image alt](https://github.com/sfill70/todotask/blob/master/src/main/resources/static/img/td_add.png)

view page

![Image alt](https://github.com/sfill70/todotask/blob/master/src/main/resources/static/img/td_view.png)

search page

![Image alt](https://github.com/sfill70/todotask/blob/master/src/main/resources/static/img/td_search_rest2.png)

put page

![Image alt](https://github.com/sfill70/todotask/blob/master/src/main/resources/static/img/td_put.png)

delete page

![Image alt](https://github.com/sfill70/todotask/blob/master/src/main/resources/static/img/td_del.png)

runing container docker

![Image alt](https://github.com/sfill70/todotask/blob/master/src/main/resources/static/img/td_run_contailer.png)
