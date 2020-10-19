package co.edu.santiago.springboot.reactor.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data @ToString
@AllArgsConstructor
public class User {
    private String name;
    private String surname;
}
