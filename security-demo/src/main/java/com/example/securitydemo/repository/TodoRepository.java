package com.example.securitydemo.repository;

import com.example.securitydemo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "todo", path = "todo")
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
