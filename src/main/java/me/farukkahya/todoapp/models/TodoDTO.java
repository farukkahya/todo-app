package me.farukkahya.todoapp.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter // alanların getter methodlarını oluşturur
@Setter // alanların setter methodlarını oluşturur
@AllArgsConstructor // bütün alanları içeren yapıcı methodu oluşturur
@NoArgsConstructor // boş bir yapıcı method oluşturur
@Document(collection = "todos") // bu nesnenin hangi koleksiyona model olacağını belirtir.
public class TodoDTO { // todoların modeli
    @Id
    private String id;
    @NotNull(message = "This field cannot be null.")
    private String todo;
    @NotNull(message = "This field cannot be null.")
    private boolean completed;
    private Date created_at;
    private Date updated_at;
}
