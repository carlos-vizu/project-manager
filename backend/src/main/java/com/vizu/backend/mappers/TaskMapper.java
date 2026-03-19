package com.vizu.backend.mappers;

import com.vizu.backend.application.controller.dto.response.TaskResponse;
import com.vizu.backend.application.controller.dto.response.UserSummaryResponse;
import com.vizu.backend.domain.model.Task;

public class TaskMapper {

    public static TaskResponse toResponse(Task task) {

        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus().name());
        response.setPriority(task.getPriority().name());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        response.setDeadline(task.getDeadline());
        response.setProjectId(task.getProject().getId());

        if (task.getAssignee() != null) {
            UserSummaryResponse user = new UserSummaryResponse();
            user.setId(task.getAssignee().getId());
            user.setName(task.getAssignee().getName());
            user.setEmail(task.getAssignee().getEmail());

            response.setAssignee(user);
        }
        return response;
    }

}