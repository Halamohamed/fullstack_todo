package se.ecutb.fullstack_todo.data;

import org.springframework.data.repository.CrudRepository;
import se.ecutb.fullstack_todo.entity.TodoItem;

import java.util.Optional;

public interface TodoItemRepository extends CrudRepository<TodoItem,Integer> {

    TodoItem findByItemId(int itemId);
    Optional<TodoItem> findByItemTitle(String itemTitle);
}
