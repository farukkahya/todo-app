package me.farukkahya.todoapp.services;

import jakarta.validation.ConstraintViolationException;
import me.farukkahya.todoapp.exception.TodoCollectionException;
import me.farukkahya.todoapp.models.TodoDTO;

public interface ITodoServices {
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;
}
