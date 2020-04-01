package se.ecutb.fullstack_todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.dto.TodoItemForm;
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
    public String getItems(String title, Model model){
        Optional<TodoItem> todoItem = itemRepository.findByItemTitle(title);
        if (todoItem != null){
            model.addAttribute("item", todoItem);
            return "items";
        }
        throw new IllegalArgumentException("Todo item is not found");

    }
    @PostMapping("/items/create")
    public String createItem(@ModelAttribute("item") TodoItemForm todoItemForm, Model model){
        model.addAttribute("item", new TodoItemForm());
        return "redirect:/items";
    }

    @GetMapping("/items/create")
    public String getForm(Model model) {
        model.addAttribute(("item"), new TodoItemForm());
        return "create-item";
    }
}
