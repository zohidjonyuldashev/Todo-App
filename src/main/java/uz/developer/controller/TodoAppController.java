package uz.developer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uz.developer.Dao.TodoDao;
import uz.developer.models.Todo;

import java.util.List;

@Controller
public class TodoAppController {
    private final TodoDao todoDao;

    public TodoAppController(TodoDao todoDao) {
        this.todoDao = todoDao;
    }


    @GetMapping("/todos")
    public String home(Model model) {
        List<Todo> todos = todoDao.findAll();
        model.addAttribute("todos", todos);
        return "home";
    }

    @GetMapping("/todos/new")
    public String createTodo(Model model) {
        Todo todo = new Todo();
        model.addAttribute("todo", todo);
        return "create";
    }

    @PostMapping("/todos/new")
    public String saveTodo(@ModelAttribute("todo") Todo todo) {
        Todo newTodo = Todo.builder()
                .title(todo.getTitle())
                .priority(todo.getPriority())
                .build();
        todoDao.save(newTodo);
        return "redirect:/todos";
    }

    @GetMapping("/todos/{todoId}/edit")
    public String editTodoForm(@PathVariable("todoId") Integer todoId, Model model) {
        Todo todo = todoDao.findById(todoId);
        model.addAttribute("todo", todo);
        return "update";
    }

    @PostMapping("/todos/{todoId}/edit")
    public String updateTodo(@PathVariable("todoId") Integer todoId, @ModelAttribute("todo") Todo todo) {
        todo.setId(todoId);
        todoDao.update(todo);
        return "redirect:/todos";
    }

    @GetMapping("/todos/{todoId}/delete")
    public String deleteTodo(@PathVariable("todoId") Integer todoId) {
        todoDao.delete(todoId);
        return "redirect:/todos";
    }
}
