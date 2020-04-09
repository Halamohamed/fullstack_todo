package se.ecutb.fullstack_todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.fullstack_todo.data.AppUserRepository;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.dto.TodoItemForm;
import se.ecutb.fullstack_todo.entity.AppUser;
import se.ecutb.fullstack_todo.entity.TodoItem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoItemServiceImpl implements TodoItemService {

  private AppUserRepository appUserRepository;
  private TodoItemRepository todoItemRepository;

  @Autowired
    public TodoItemServiceImpl(AppUserRepository appUserRepository, TodoItemRepository todoItemRepository) {
        this.appUserRepository = appUserRepository;
        this.todoItemRepository = todoItemRepository;
    }

    @Override
    public List<TodoItem> findAll(){
      return todoItemRepository.findAll();
    }

    @Override
    public List<TodoItem> findAllUnAssigned(){
        return todoItemRepository.findAll()
                .stream()
                .filter(todoItem -> todoItem.getUserName().equals(null))
                .collect(Collectors.toList());
    }

    @Override
    public TodoItem findByItemId(int itemId){
      return todoItemRepository.findByItemId(itemId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public TodoItem create(TodoItem item, String username){
        AppUser user = appUserRepository.findByUsername(username).get();
        TodoItem todoItem = new TodoItem(item.getItemTitle(),item.getItemDescription(),item.getDeadline(),item.isDoneStatus(),item.getReward());
        todoItem.setUserName(user);
        return todoItemRepository.save(todoItem);
    }

    @Override
    public TodoItem save(TodoItem todoItem){
      if(todoItem.getItemId() == 0){
          throw new IllegalArgumentException("Todo item is not yet persisted");
      }
      return todoItemRepository.save(todoItem);
    }

    @Override
    public boolean delete(int itemId){
      todoItemRepository.deleteById(itemId);
      return todoItemRepository.existsById(itemId);
    }

    @Override
    public TodoItem findByTitle(String title){
     if(title == null){
        throw new IllegalArgumentException("Title not found");
     }
        Optional<TodoItem> todoItem = todoItemRepository.findByItemTitle(title);
      return todoItem.get();
    }

    @Override
    public TodoItem updateItem(TodoItemForm todoItemForm){
      if(todoItemForm.getItemId() == 0){
          throw new IllegalArgumentException("Todo item not found");
      }
      TodoItem newItem = findByItemId(todoItemForm.getItemId());
//      newItem.setUserName(todoItemForm.getUserName());
      newItem.setItemDescription(todoItemForm.getItemDescription());
      newItem.setDeadline(todoItemForm.getDeadline());
      newItem.setDoneStatus(todoItemForm.isDoneStatus());
      newItem.setReward(todoItemForm.getReward());

      return todoItemRepository.save(newItem);
    }
}
