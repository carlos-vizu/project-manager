package com.vizu.backend.service;

import com.vizu.backend.application.controller.dto.request.CreateTaskRequest;
import com.vizu.backend.application.controller.dto.request.TaskFilter;
import com.vizu.backend.application.controller.dto.request.UpdateTaskRequest;
import com.vizu.backend.application.controller.dto.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    public TaskResponse createTask(CreateTaskRequest req);

    public TaskResponse updateTask(Long id, UpdateTaskRequest req, Long reqUserId);

    public Page<TaskResponse> listTasks(TaskFilter filter, Pageable pageable);

    public Page<TaskResponse> searchTask(Pageable pageable, String text);

    public void removeTask(Long id);
}
