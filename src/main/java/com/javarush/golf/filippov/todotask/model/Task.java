package com.javarush.golf.filippov.todotask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min=2, max=100)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;



   public Task() {
    }

    public Task(int id, String description, Status status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public Task(String description, Status status) {
        this.description = description;
        this.status= status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static List<String> getValues(){
        return Arrays.asList("id", "description", "status");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (!Objects.equals(description, task.description)) return false;
        return status == task.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
