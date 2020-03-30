package se.ecutb.fullstack_todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.fullstack_todo.data.AppUserRepository;
import se.ecutb.fullstack_todo.data.AppUserRoleRepository;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.entity.AppUser;
import se.ecutb.fullstack_todo.entity.AppUserRole;
import se.ecutb.fullstack_todo.entity.Roles;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MainAppUser {
    private AppUserRoleRepository appUserRoleRepository;
    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private TodoItemRepository todoItemRepository;

    @Autowired
    public MainAppUser(AppUserRoleRepository appUserRoleRepository, AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder, TodoItemRepository todoItemRepository) {
        this.appUserRoleRepository = appUserRoleRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.todoItemRepository = todoItemRepository;
    }

    @PostConstruct
    @Transactional(rollbackFor = RuntimeException.class)
    public void init(){
        AppUser user = new AppUser("ADMIN", "Hala","Ali",LocalDate.now(),passwordEncoder.encode("ADMIN"));
        Set<AppUserRole> roleSet = Arrays.stream(Roles.values())
                .map(role -> appUserRoleRepository.save(new AppUserRole(role.name())))
                .collect(Collectors.toSet());
        user.setRoleSet(roleSet);
        appUserRepository.save(user);
    }


}
