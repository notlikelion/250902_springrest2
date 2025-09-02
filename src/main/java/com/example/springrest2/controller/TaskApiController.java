package com.example.springrest2.controller;

import com.example.springrest2.domain.Task;
import com.example.springrest2.dto.TaskCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {
    private final Map<Long, Task> taskStore = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    // POST /api/tasks - 할 일 생성
    @PostMapping
//    public Task createTask(@RequestBody TaskCreateRequest request) {
    public ResponseEntity<Task> createTask(@RequestBody TaskCreateRequest request) {
        long id = sequence.incrementAndGet(); // 1
        Task task = new Task(id, request.title(), false);
        taskStore.put(id, task);
//        return task;
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
//        return ResponseEntity.status(201).body(task);
//        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    // GET /api/tasks - 할 일 전체 조회
    @GetMapping
    public List<Task> getTasks() {
        return taskStore.values().stream().toList();
    }

    // GET /api/tasks/{id} - 할 일 조회
    @GetMapping("/{id}")
//    public Task getTaskById(@PathVariable Long id) {
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
//        return taskStore.get(id); // 없으면 그냥 null이 나가고 있음
        Task task = taskStore.get(id);
        if (task != null) {
//            return task;
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/tasks/{id} - 할 일 수정
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        taskStore.put(id, updatedTask);
        return updatedTask;
    }

    // DELETE /api/tasks/{id} - 할 일 삭제
    @DeleteMapping("/{id}")
//    public void deleteTask(@PathVariable Long id) {
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskStore.remove(id);
        return ResponseEntity.noContent().build();
    }
}
