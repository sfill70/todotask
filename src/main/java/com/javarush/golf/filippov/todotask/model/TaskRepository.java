package com.javarush.golf.filippov.todotask.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends /*CrudRepository*/ JpaRepository<Task, Integer> {

    @Query("select Task from Task")
    List<Task> findByAllTask(Pageable pageable);

    @Query("select Task from Task Task where Task.description LIKE %:description%")
    List<Task> findByDescription(@Param("description") String description);

   /* @Query("select Task from Task Task where Task.status LIKE %:status%")
    List<Task> findByStatus(@Param("status") *//*Status*//* String status);*/

}
