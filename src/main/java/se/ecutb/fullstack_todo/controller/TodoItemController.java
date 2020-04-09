package se.ecutb.fullstack_todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

//    @GetMapping("/users/items")
//    public String getItems(String title, Model model){
//        TodoItem todoItem = todoItemService.findByTitle(title);
//        if (todoItem != null){
//            List<TodoItem> items = todoItemService.findAll();
//            model.addAttribute("items", items);
//            return "items";
//        }
//        throw new IllegalArgumentException("Todo item is not found");
//
//    }
    @PostMapping("/users/items/create")
    public String createItem(@Valid @ModelAttribute("item") TodoItemForm todoItemForm, BindingResult bindingResult, @AuthenticationPrincipal UserDetails principal){

        if(bindingResult.hasErrors()){
            return "create-item";
        }
        if(principal.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
            TodoItem newTodo = new TodoItem(todoItemForm.getItemTitle(), todoItemForm.getItemDescription(), todoItemForm.getDeadline(), todoItemForm.isDoneStatus(), todoItemForm.getReward());
            TodoItem todoItem = todoItemService.create(newTodo,principal.getUsername());

            return "redirect:/users/items?type=id&value="+todoItem.getItemId();       //return "redirect:/users/items?type=id&value=" + todoItem.getItemId();
        }
        return "create-item";
    }

    @GetMapping("users/items")
    public String findItem(@RequestParam (name="type", defaultValue="all") String type, @RequestParam(name="value", defaultValue = "") String value, Model model){
        if(type.equals("all")){
            model.addAttribute("itemList", todoItemService.findAll());
            return "item-details";
        }
        else if(type.equals("id")){
            int id=Integer.parseInt(value);
            TodoItem todoItem = todoItemService.findByItemId(id);
            model.addAttribute("items", todoItem);
            return "items";
        }
        else{
            throw new IllegalArgumentException("Todo item not found");
        }

    }

    @GetMapping("/users/items/create")
    public String getForm(Model model) {
        model.addAttribute("item", new TodoItemForm());
        return "create-item";
    }

    @GetMapping("/users/items/details")
    public String getItems(@ModelAttribute("itemList") Model model){

        List<TodoItem> itemList = todoItemService.findAllUnAssigned();
        if (String.valueOf(itemList.get(0).getItemId()) != null){      //if (itemList.get(0).getItemId() != 0){
            throw new IllegalArgumentException("Not found");
        }
        model.addAttribute("itemList", itemList);
        return "item-details";
    }

    @GetMapping("/users/items/{id}/update")
    public String getUpdate(@PathVariable("id") int id,Model model){
        TodoItem todo1 = todoItemService.findByItemId(id);
        TodoItemForm todoItemForm= new TodoItemForm(todo1.getItemId(), todo1.getUserName(), todo1.getItemTitle(),todo1.getItemDescription(),todo1.getDeadline(),todo1.isDoneStatus(),todo1.getReward());
       model.addAttribute("form",todoItemForm);
       return "update-form";

    }
    @PostMapping("/users/items/{id}/update")
    public String update(@PathVariable("id") int id,@ModelAttribute("form") TodoItemForm item){

    todoItemService.updateItem(item);
        return "redirect:/users/items/details";
    }


}
