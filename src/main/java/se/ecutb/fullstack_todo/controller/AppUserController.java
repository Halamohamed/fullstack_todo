package se.ecutb.fullstack_todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import se.ecutb.fullstack_todo.data.AppUserRepository;
import se.ecutb.fullstack_todo.data.AppUserRoleRepository;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.dto.AppUserForm;
import se.ecutb.fullstack_todo.entity.AppUser;
import se.ecutb.fullstack_todo.service.AppUserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/users/details")
    public String getUser(@ModelAttribute("list") AppUser appUser, @AuthenticationPrincipal UserDetails user,Model model ){
        if(user == null){
            return "redirect:/login";
        }

        if(appUser.getUsername().equals(user.getUsername()) || user.getAuthorities().stream().allMatch(auth -> auth.getAuthority().equals("ADMIN"))){
            List<AppUser> appUserList = service.findAll();
            model.addAttribute("list", appUserList);
            return "user-details";
        }
        if(appUser.getUsername().equals(user.getUsername()) || user.getAuthorities().stream().allMatch(auth -> auth.getAuthority().equals("USER"))){
            Optional<AppUser> appUser1 = service.findByUsername(appUser.getUsername());
            model.addAttribute("list", appUser1);
            return "user-details";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login-form";
    }

    @PostMapping("/users/search")
    public String searchUser(@ModelAttribute("appUser") AppUser appUser, @AuthenticationPrincipal UserDetails user, Model model){
        if(user == null){
            return "redirect:/login";
        }
        if(appUser.getUsername().equals(user.getUsername()) || user.getAuthorities().stream().allMatch(auth -> auth.getAuthority().equals("ADMIN")
                || user.getAuthorities().stream().allMatch(autho -> autho.getAuthority().equals("USER"))
        )){
            Optional<AppUser> optionalAppUser = service.findByUsername(appUser.getUsername());
            if(optionalAppUser.isPresent()){
                AppUser appUser1 = service.findByUsername(appUser.getUsername()).get();
                model.addAttribute("appUser", appUser1);
                return "oneUser";
            }
            else{
                throw new IllegalArgumentException("Requested user not found");
            }

        }
            return "oneUser";

    }
    @GetMapping("users/search")
    public String getSearch(@ModelAttribute("appUser") AppUser appUser,Model model){
       // model.addAttribute("username", service.findByUsername(username));
        return "user-search";
    }

    @GetMapping("/accessdenied")
    public String getAccessDenied(){
        return "access-denied";
    }



}

