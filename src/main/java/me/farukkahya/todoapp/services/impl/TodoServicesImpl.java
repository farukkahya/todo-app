package me.farukkahya.todoapp.services.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import me.farukkahya.todoapp.exception.TodoCollectionException;
import me.farukkahya.todoapp.models.TodoDTO;
import me.farukkahya.todoapp.repositories.ITodoRepository;
import me.farukkahya.todoapp.services.ITodoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepository.findAll(); // tüm todoları tutmak için bir TodoDTO listesi oluşturdum.
        if (!todos.isEmpty()){
            return todos; // todos boş değil ise dödürülücek
        }else{
            return new ArrayList<TodoDTO>(); // eğer todos boş ise boş bit liste döndürülecek.
        }
    }

    @Override
    public TodoDTO getTodoById(String id) throws TodoCollectionException {
        Optional<TodoDTO> optionalTodo = todoRepository.findById(id); // belirtilen id değerine sahip to do çekilir
        if (optionalTodo.isEmpty()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id)); // eğer to do yoksa hata döndürülür.
        }else {
            return optionalTodo.get(); // eğer to do varsa döndürülür.
        }
    }

    @Override
    public void updateTodoById(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> todoWithID = todoRepository.findById(id); // belirtilen id değerine sahip to do çekilir
        Optional<TodoDTO> todoWithName = todoRepository.findByTodo(todo.getTodo()); // aynı isimde to do var mı kontrol etmek için
        // eğer to do varsa güncellemeler yapılacak ve güncellenen to do OK kodu ile birlikte döndürülecek.
        if (todoWithID.isPresent()){
            if (todoWithName.isPresent() && !todoWithName.get().getId().equals(id)){ // aynı isimde farklı bir id değerine sahip to do varsa hata dönderecek.
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }
            TodoDTO todoToUpdate = todoWithID.get();// güncellenecek to do
            todoToUpdate.setTodo(todo.getTodo());
            todoToUpdate.setDescription(todo.getDescription());
            todoToUpdate.setCompleted(todo.isCompleted());
            todoToUpdate.setUpdated_at(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToUpdate); // to do güncellendi
        }else{
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }
}
