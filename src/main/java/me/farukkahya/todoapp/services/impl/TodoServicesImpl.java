package me.farukkahya.todoapp.services.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import me.farukkahya.todoapp.exception.TodoCollectionException;
import me.farukkahya.todoapp.models.TodoDTO;
import me.farukkahya.todoapp.repositories.ITodoRepository;
import me.farukkahya.todoapp.services.ITodoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TodoServicesImpl implements ITodoServices {

    @Autowired
    private ITodoRepository todoRepository; // database işlemlerini yapabilmek için repository nesnesi oluşturdum.
    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException,TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
        // aynı isme sahip bir to do varsa  hata göndericek yoksa ekleme yapacak.
        if (todoOptional.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        }else {
            todo.setCreated_at(new Date(System.currentTimeMillis())); // kayıt etmeden önce oluşturulma tarihi eklendi.
            todoRepository.save(todo); // to do kaydedildi.
        }
    }
}
