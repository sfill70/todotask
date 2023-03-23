package com.javarush.golf.filippov.todotask.repository;

import com.javarush.golf.filippov.todotask.model.Status;
import com.javarush.golf.filippov.todotask.model.Task;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    List<Task> findByStatus(@NotNull Status status);

    @Query("select Task from Task Task where Task.description LIKE %:description%")
    List<Task> findByDescription(@Param("description") String description);
    @Query("select Task from Task Task where Task.status=:status and Task.description LIKE %:description%")
    List<Task> findStatusAndLikeDescription(@Param("status") Status status, @Param("description") String description);


//    List<Task>findByDescriptionIsLike(@NotNull @Size(min = 2, max = 100) String description);

    @Query("select Task from Task Task where Task.status=:status")
    List<Task> findByStatusMy(@Param("status") Status status);

    List<Task> findByDescriptionAndStatus(@NotNull @Size(min = 2, max = 100) String description, @NotNull Status status);

}
