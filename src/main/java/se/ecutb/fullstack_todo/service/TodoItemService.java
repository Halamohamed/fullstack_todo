package se.ecutb.fullstack_todo.service;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.fullstack_todo.entity.TodoItem;

import java.util.List;

public interface TodoItemService {
    List<TodoItem> findAll();

    List<TodoItem> findAllUnAssigned();

    TodoItem findByItemId(int itemId);

    @Transactional(rollbackFor = RuntimeException.class)
    TodoItem create(TodoItem item, String username);

    TodoItem save(TodoItem todoItem);

    boolean delete(int itemId);

    TodoItem findByTitle(String title);

    TodoItem updateItem(TodoItem todoItem);
}
