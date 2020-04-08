package se.ecutb.fullstack_todo.data;

import org.springframework.data.repository.CrudRepository;
import se.ecutb.fullstack_todo.entity.AppUserRole;
import se.ecutb.fullstack_todo.entity.Roles;

public interface AppUserRoleRepository extends CrudRepository<AppUserRole, Integer> {

    AppUserRole findByRole(Roles role);



}
