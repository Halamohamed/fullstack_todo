package se.ecutb.fullstack_todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.ecutb.fullstack_todo.data.AppUserRepository;
import se.ecutb.fullstack_todo.data.AppUserRoleRepository;
import se.ecutb.fullstack_todo.data.TodoItemRepository;

@SpringBootApplication
public class FullstackTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullstackTodoApplication.class, args);
    }



}
