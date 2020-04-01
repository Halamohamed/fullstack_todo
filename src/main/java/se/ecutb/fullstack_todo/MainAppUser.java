package se.ecutb.fullstack_todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.fullstack_todo.data.AppUserRepository;
import se.ecutb.fullstack_todo.data.AppUserRoleRepository;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.dto.AppUserForm;
import se.ecutb.fullstack_todo.entity.AppUser;
import se.ecutb.fullstack_todo.entity.AppUserRole;
import se.ecutb.fullstack_todo.entity.Roles;
import se.ecutb.fullstack_todo.entity.TodoItem;
import se.ecutb.fullstack_todo.service.AppUserDetailsService;
import se.ecutb.fullstack_todo.service.AppUserService;
import se.ecutb.fullstack_todo.service.TodoItemService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MainAppUser {
    private AppUserRepository userRepository;
    private AppUserRoleRepository appUserRole;
    private BCryptPasswordEncoder passwordEncoder;
    private TodoItemService todoItemService;
    private AppUserService service;

    @Autowired
    public MainAppUser(AppUserRoleRepository roleRepository, AppUserRepository repository, BCryptPasswordEncoder passwordEncoder, AppUserService service,TodoItemService todoItemService) {
        this.userRepository = repository;
        this.appUserRole = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.service = service;
        this.todoItemService = todoItemService;
    }

    @PostConstruct
    @Transactional(rollbackFor = RuntimeException.class)
    public void init(){
        AppUser user = new AppUser("admin", "Hala","Ali",LocalDate.now(),passwordEncoder.encode("admin"));
        Set<AppUserRole> roleSet = Arrays.stream(Roles.values())
                .map(role -> appUserRole.save(new AppUserRole(role.name())))
                .collect(Collectors.toSet());
        user.setRoleSet(roleSet);


        userRepository.save(user);
        TodoItem todoItem = new TodoItem("test title", "test description", LocalDate.now().plusDays(10),false,12);




        System.out.println(service.findByUsername("admin"));
        System.out.println(todoItemService.create(todoItem,user.getUsername()));

    }


}
