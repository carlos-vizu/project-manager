package com.vizu.backend.application.controller;

import com.vizu.backend.application.controller.dto.request.CreateTaskRequest;
import com.vizu.backend.application.controller.dto.request.TaskFilter;
import com.vizu.backend.application.controller.dto.request.UpdateTaskRequest;
import com.vizu.backend.application.controller.dto.response.TaskResponse;
import com.vizu.backend.application.controller.swagger.TaskSwagger;
import com.vizu.backend.domain.model.Task;
import com.vizu.backend.service.TaskService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController implements TaskSwagger {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody CreateTaskRequest req) {
        return ResponseEntity.ok(service.createTask(req));
    }

    @PutMapping("/{taskId}/member/{reqUserId}")
    public ResponseEntity<TaskResponse> update( @PathVariable Long taskId, @PathVariable Long reqUserId, @RequestBody UpdateTaskRequest req) {
        return ResponseEntity.ok(service.updateTask(taskId, req, reqUserId));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> list(TaskFilter filter, Pageable pageable) {
        return ResponseEntity.ok(service.listTasks(filter, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TaskResponse>> search(@RequestParam String text, Pageable pageable) {
        return ResponseEntity.ok(service.searchTask(pageable, text));
    }
}

