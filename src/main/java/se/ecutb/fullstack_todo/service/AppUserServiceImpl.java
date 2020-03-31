package se.ecutb.fullstack_todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.ecutb.fullstack_todo.data.AppUserRepository;
import se.ecutb.fullstack_todo.data.AppUserRoleRepository;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.dto.AppUserForm;
import se.ecutb.fullstack_todo.entity.AppUser;
import se.ecutb.fullstack_todo.entity.AppUserRole;
import se.ecutb.fullstack_todo.entity.Roles;
import se.ecutb.fullstack_todo.entity.TodoItem;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;
    private AppUserRoleRepository roleRepository;
    private TodoItemRepository todoItemRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, AppUserRoleRepository roleRepository, TodoItemRepository todoItemRepository, BCryptPasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.todoItemRepository = todoItemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser registerAppUser(AppUserForm appUserForm) {
        AppUser newUser = new AppUser(
                appUserForm.getUsername(),
                appUserForm.getFirstName(),
                appUserForm.getLastName(),
                LocalDate.now(),
                passwordEncoder.encode(appUserForm.getPassword())

        );
        AppUserRole role = roleRepository.findByRole(Roles.USER.name());

        Set<AppUserRole> roleSet = new HashSet<>();

        roleSet.add(role);

        newUser = appUserRepository.save(newUser);

        newUser.setRoleSet(roleSet);


        return newUser;
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public Optional<TodoItem> findByItemTitle(String title) {
        return todoItemRepository.findByItemTitle(title);
    }
}
