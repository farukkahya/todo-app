package me.farukkahya.todoapp.repositories;

import me.farukkahya.todoapp.models.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITodoRepository extends MongoRepository<TodoDTO,String> {
}
