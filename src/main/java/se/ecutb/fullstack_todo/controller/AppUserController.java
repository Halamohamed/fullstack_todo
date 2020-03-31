package se.ecutb.fullstack_todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.ecutb.fullstack_todo.data.AppUserRepository;
import se.ecutb.fullstack_todo.data.AppUserRoleRepository;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.dto.AppUserForm;
import se.ecutb.fullstack_todo.service.AppUserService;

import javax.validation.Valid;

@Controller
public class AppUserController {

    private AppUserService service;
    private AppUserRoleRepository appUserRoleRepository;
    private TodoItemRepository todoItemRepository;

    @Autowired
    public AppUserController(AppUserService service, AppUserRoleRepository appUserRoleRepository, TodoItemRepository todoItemRepository) {
        this.service = service;
        this.appUserRoleRepository = appUserRoleRepository;
        this.todoItemRepository = todoItemRepository;
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("form" , new AppUserForm());
        return "register";
    }
    @PostMapping("/register")
    public String processForm(@Valid @ModelAttribute(name = "form") AppUserForm userForm, BindingResult bindingResult){
        if(service.findByUsername(userForm.getUsername()).isPresent()){
            FieldError error = new FieldError("form","username","Username already exists " + userForm.getUsername());
            bindingResult.addError(error);
        }
        if(!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            FieldError error = new FieldError("form","passwordConfirm", "Password confirmation doesn't match password");
            bindingResult.addError(error);
        }
        if(bindingResult.hasErrors()){
            return "/register";
        }
        service.registerAppUser(userForm);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login-form";
    }
}
