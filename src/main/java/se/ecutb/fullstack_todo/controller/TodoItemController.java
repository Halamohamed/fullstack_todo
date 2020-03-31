package se.ecutb.fullstack_todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.entity.TodoItem;

import java.util.Optional;

@Controller
public class TodoItemController {

    private TodoItemRepository itemRepository;

    @Autowired
    public TodoItemController(TodoItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items")
    public String getItems(@PathVariable("item") String title, Model model){
        TodoItem todoItem = itemRepository.findByItemTitle(title).get();
        if (todoItem != null){
            model.addAttribute("item", todoItem);
            return "items";
        }
        throw new IllegalArgumentException("Todo item is not found");

    }
}
