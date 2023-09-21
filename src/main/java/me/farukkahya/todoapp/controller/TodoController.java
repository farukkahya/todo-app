package me.farukkahya.todoapp.controller;

import me.farukkahya.todoapp.models.TodoDTO;
import me.farukkahya.todoapp.repositories.ITodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController // bu anotasyonu bu sınıfın bir rest controller olduğunu belirtmek için kullandım.
@RequestMapping("/api/v1")
// bu anotasyonu "http:localhost:8080/api/v1" adresine gelen istekleri bu controller sınıfına iletmek için kullandım.
public class TodoController {
    @Autowired // bu anotasyon program çalıştığında kodu analiz edecek ve repository'i gerekli nesne ile eşleştirecek.
    private ITodoRepository todoRepository; // database işlemlerini yapabilmek için repository nesnesi oluşturdum.

    @GetMapping("/todos") // http:localhost:8080/api/v1/todos
    // Bütün todoları okumak için oluşturuldu
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoRepository.findAll(); // tüm todoları tutmak için bir TodoDTO listesi oluşturdum.
        // eğer todos listesi boş değil ise listenin kendisini ve 'OK' durum kodunu döndürecek.
        if (!todos.isEmpty()) {
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        }
        // todos listesi boş işe hiç to do olmadığını belirten bir mesaj döndürecek.
        else {
            return new ResponseEntity<>("No todos available.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-todo") // http:localhost:8080/api/v1/create-todo
    // Todoya ekleme yapmak için oluşturuldu
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todo.setCreated_at(new Date(System.currentTimeMillis())); // todoya oluşturulma tarihi atandı
            todoRepository.save(todo); // to do kaydedildi
            return new ResponseEntity<TodoDTO>(todo, HttpStatus.CREATED); // oluşturılan to do elemanı ve CREATED kodu dönderildi
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // hata oluşması durumunda hata mesajı ve hata kodu dönderilecek
        }
    }
}