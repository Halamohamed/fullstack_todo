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
    public String createItem(@Valid @ModelAttribute("item") TodoItemForm todoItemForm, BindingResult bindingResult, @AuthenticationPrincipal UserDetails principal){

        if(bindingResult.hasErrors()){
            return "create-item";
        }
        if(principal.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
            TodoItem newTodo = new TodoItem(todoItemForm.getItemTitle(), todoItemForm.getItemDescription(), todoItemForm.getDeadline(), todoItemForm.isDoneStatus(), todoItemForm.getReward());
            TodoItem todoItem = todoItemService.create(newTodo,principal.getUsername());

            return "redirect:/items/details?type=id&value=" + todoItem.getItemId();
        }
        return "create-item";
    }

    @GetMapping("/items/create")
    public String getForm(Model model) {
        model.addAttribute("item", new TodoItemForm());
        return "create-item";
    }

    @GetMapping("/items/details")
    public String getItems(@ModelAttribute("itemList") Model model){
        List<TodoItem> itemList = todoItemService.findAll();
        model.addAttribute("itemList", itemList);
        return "item-details";
    }

    @GetMapping("/items/{id}/update")
    public String update(@PathVariable("id") int id,@ModelAttribute("form") TodoItemForm item){

        TodoItem todo1 = new TodoItem(item.getItemTitle(),item.getItemDescription(),item.getDeadline(),item.isDoneStatus(),item.getReward());

        TodoItem todoItem = todoItemService.updateItem(todo1);
        return "update-form";
    }


}
