package se.ecutb.fullstack_todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import se.ecutb.fullstack_todo.data.AppUserRepository;
import se.ecutb.fullstack_todo.data.AppUserRoleRepository;
import se.ecutb.fullstack_todo.data.TodoItemRepository;

@Controller
public class AppUserController {

    private AppUserRepository appUserRepository;
    private AppUserRoleRepository appUserRoleRepository;
    private TodoItemRepository todoItemRepository;

    @Autowired
    public AppUserController(AppUserRepository appUserRepository, AppUserRoleRepository appUserRoleRepository, TodoItemRepository todoItemRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserRoleRepository = appUserRoleRepository;
        this.todoItemRepository = todoItemRepository;
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
