package com.codestone.mytodo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	private List<Todo> todos = new ArrayList<>();

	private final AtomicLong counter = new AtomicLong();

	public TodoController() {

	}

	// GET
	@GetMapping("/todo")
	public List<Todo> getTodos1() {
		return todos;
	}

	// ..../todo/id
	@GetMapping("/todo/{id}")
	public Todo getTodosById(@PathVariable long id) {
		return todos.stream().filter(result -> result.getId() == id).findFirst()
				.orElseThrow(() -> new TodoNotFoundException(id));
	}

	// ..../todo/search?name=StoneStone
	@GetMapping("/todo/search")
	public String getTodosByName(@RequestParam(defaultValue = "Dogg") String name) {
		return "search: " + name;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/todo")
	public void addTodo(@RequestBody Todo todo) {
		todos.add(new Todo(counter.getAndIncrement(), todo.getName()));
	}

	@PutMapping("/todo/{id}")
	public void editTodo(@RequestBody Todo todo, @PathVariable long id) {
		todos.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
			result.setName(todo.getName());
		}, () -> {
			throw new TodoNotFoundException(id);
		});
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/todo/{id}")
	public void deleteTodo(@PathVariable long id) {
		todos.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
			todos.remove(result);
		}, () -> {
			throw new TodoNotFoundException(id);
		});
	}
}
