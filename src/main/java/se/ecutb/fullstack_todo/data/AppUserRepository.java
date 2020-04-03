package se.ecutb.fullstack_todo.data;

import org.springframework.data.repository.CrudRepository;
import se.ecutb.fullstack_todo.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser,Integer> {

    Optional<AppUser> findByUsername(String username);
    AppUser findByUserId (int userId);
    List<AppUser> findAll();
}
