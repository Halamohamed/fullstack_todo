package se.ecutb.fullstack_todo.service;

import se.ecutb.fullstack_todo.dto.AppUserForm;
import se.ecutb.fullstack_todo.entity.AppUser;
import se.ecutb.fullstack_todo.entity.TodoItem;

import java.util.Optional;

public interface AppUserService {

    AppUser registerAppUser(AppUserForm appUserForm);
    Optional<AppUser> findByUsername(String username);
    Optional<TodoItem> findByItemTitle(String title);

}
