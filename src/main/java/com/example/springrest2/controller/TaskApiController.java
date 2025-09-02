package com.example.springrest2.controller;

import com.example.springrest2.domain.Task;
import com.example.springrest2.dto.TaskCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Task createTask(@RequestBody TaskCreateRequest request) {
        long id = sequence.incrementAndGet(); // 1
        Task task = new Task(id, request.title(), false);
        taskStore.put(id, task);
        return task;
    }
}
