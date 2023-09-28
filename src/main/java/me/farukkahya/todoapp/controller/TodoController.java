package me.farukkahya.todoapp.controller;

import jakarta.validation.ConstraintViolationException;
import me.farukkahya.todoapp.exception.TodoCollectionException;
import me.farukkahya.todoapp.models.TodoDTO;
import me.farukkahya.todoapp.repositories.ITodoRepository;
import me.farukkahya.todoapp.services.ITodoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController // bu anotasyonu bu sınıfın bir rest controller olduğunu belirtmek için kullandım.
@RequestMapping("/api/v1")
// bu anotasyonu "http:localhost:8080/api/v1" adresine gelen istekleri bu controller sınıfına iletmek için kullandım.
public class TodoController {
    @Autowired // bu anotasyon program çalıştığında kodu analiz edecek ve repository'i gerekli nesne ile eşleştirecek.
    private ITodoRepository todoRepository; // database işlemlerini yapabilmek için repository nesnesi oluşturdum.
    @Autowired
    private ITodoServices todoServices;

    @GetMapping("/todos") // http:localhost:8080/api/v1/todos
    // Bütün todoları okumak için oluşturuldu
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoServices.getAllTodos(); // tüm todoları tutmak için bir TodoDTO listesi oluşturdum.
        // eğer todos listesi boş değil ise listenin kendisini ve 'OK' durum kodunu döndürecek.
        return new ResponseEntity<>(todos, !todos.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-todo") // http:localhost:8080/api/v1/create-todo
    // Todoya ekleme yapmak için oluşturuldu
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todoServices.createTodo(todo); // to do ekleme işlemi
            return new ResponseEntity<>(todo, HttpStatus.CREATED); // oluşturılan to do elemanı ve CREATED kodu dönderildi
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); // hata oluşması durumunda hata mesajı ve hata kodu dönderilecek
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // hata oluşması durumunda hata mesajı ve hata kodu dönderilecek
        }
    }

    @GetMapping("todos/{id}") // http:localhost:8080/api/v1/todos/{id}
    // gönderilen id değerine sahip todoyu okumak için oluşturuldu
    public ResponseEntity<?> getTodoById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(todoServices.getTodoById(id), HttpStatus.OK); // gönderilen id değerne sahip to do varsa OK koduyla döndürülecek.
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);// gönderilen id değerne sahip to do yoksa NOT FOUND koduyla döndürülecek.
        }
    }

    @PutMapping("update-todo/{id}") // http:localhost:8080/api/v1/update-todo/{id}
    // gönderilen id değerine sahip todoyu güncellemek için oluşturuldu
    public ResponseEntity<?> updateTodoById(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id); // belirtilen id değerine sahip to do çekilir
        // eğer to do varsa güncellemeler yapılacak ve güncellenen to do OK kodu ile birlikte döndürülecek.
        if (todoOptional.isPresent()) {
            TodoDTO todoToSave = todoOptional.get(); // güncellenecek to do
            todoToSave.setCompleted(todo.isCompleted());
            todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdated_at(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToSave); // to do güncellendi
            return new ResponseEntity<>(todoToSave, HttpStatus.OK);
        }
        // eğer to do yoksa NOT_FOUND kodu ile birlikte belirtilen id değerine sahip bir to do olmadığını belirten bir mesaj döndürülür.
        else {
            return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete-todo/{id}") // http:localhost:8080/api/v1/delete-todo/{id}
    // gönderilen id değerine sahip todoyu silmek için oluşturuldu
    public ResponseEntity<?> deleteTodoById(@PathVariable String id) {
        try {
            todoRepository.deleteById(id); // girilem id değerine sahip todoyu silindi
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK); // todonun silindiğini belirten mesaj OK koduyla gönderildi.
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // hata oluşması durumunda hata mesajı ve hata kodu dönderilecek
        }
    }
}