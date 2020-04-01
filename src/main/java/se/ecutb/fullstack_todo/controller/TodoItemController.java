package se.ecutb.fullstack_todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import se.ecutb.fullstack_todo.data.TodoItemRepository;
import se.ecutb.fullstack_todo.dto.TodoItemForm;
import se.ecutb.fullstack_todo.entity.TodoItem;
import se.ecutb.fullstack_todo.service.TodoItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TodoItemController {

    private TodoItemService todoItemService;

    @Autowired
    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GetMapping("/items")
    public String getItems(String title, Model model){
        TodoItem todoItem = todoItemService.findByTitle(title);
        if (todoItem != null){
            List<TodoItem> items = todoItemService.findAll();
            model.addAttribute("items", items);
            return "items";
        }
        throw new IllegalArgumentException("Todo item is not found");

    }
    @PostMapping("/items/create")
    public String createItem(@Valid @ModelAttribute("item") TodoItemForm todoItemForm, @AuthenticationPrincipal UserDetails principal, Model model, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "create-item";
        }
        if(principal.getAuthorities().equals("ADMIN")) {
            TodoItem newTodo = new TodoItem(todoItemForm.getItemTitle(), todoItemForm.getItemDescription(), todoItemForm.getDeadline(), todoItemForm.isDoneStatus(), todoItemForm.getReward());
            TodoItem todoItem = todoItemService.create(newTodo,principal.getUsername());
            model.addAttribute("item", todoItem);
            return "redirect:/items";
        }
        return "create-item";
    }

    @GetMapping("/items/create")
    public String getForm(Model model) {
        model.addAttribute("item", new TodoItemForm());
        return "create-item";
    }
}
