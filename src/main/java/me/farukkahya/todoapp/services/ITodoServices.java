package me.farukkahya.todoapp.services;

import jakarta.validation.ConstraintViolationException;
import me.farukkahya.todoapp.exception.TodoCollectionException;
import me.farukkahya.todoapp.models.TodoDTO;

import java.util.List;

public interface ITodoServices {
    void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;
    List<TodoDTO> getAllTodos();
    TodoDTO getTodoById(String id) throws TodoCollectionException;
    void updateTodoById(String id,TodoDTO todo) throws TodoCollectionException;
}
