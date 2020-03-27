package se.ecutb.fullstack_todo.data;

import org.springframework.data.repository.CrudRepository;
import se.ecutb.fullstack_todo.entity.TodoItem;

public interface TodoItemRepository extends CrudRepository<TodoItem,Integer> {

    TodoItem findByItemId(int itemId);
    TodoItem findByItemTitle(String itemTitle);
}
