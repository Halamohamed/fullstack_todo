package se.ecutb.fullstack_todo.data;

import org.springframework.data.repository.CrudRepository;
import se.ecutb.fullstack_todo.entity.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser,Integer> {

    AppUser findByUsername(String username);
    AppUser findByUserId(int userId);

}
