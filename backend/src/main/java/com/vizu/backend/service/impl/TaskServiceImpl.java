package com.vizu.backend.service.impl;

import com.vizu.backend.application.controller.dto.request.CreateTaskRequest;
import com.vizu.backend.application.controller.dto.request.UpdateTaskRequest;
import com.vizu.backend.application.controller.dto.request.TaskFilter;
import com.vizu.backend.application.controller.dto.response.TaskResponse;
import com.vizu.backend.domain.enums.Priority;
import com.vizu.backend.domain.enums.Role;
import com.vizu.backend.domain.enums.Status;
import com.vizu.backend.domain.model.Project;
import com.vizu.backend.domain.model.Task;
import com.vizu.backend.domain.model.User;
import com.vizu.backend.infraestructure.repository.ProjectMemberRepository;
import com.vizu.backend.infraestructure.repository.ProjectRepository;
import com.vizu.backend.infraestructure.repository.TaskRepository;
import com.vizu.backend.infraestructure.repository.UserRepository;
import com.vizu.backend.infraestructure.specification.TaskSpecification;
import com.vizu.backend.mappers.TaskMapper;
import com.vizu.backend.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, ProjectMemberRepository projectMemberRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest req) {
        Project project = projectRepository.findById(req.getProjectId()).orElseThrow();

        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(Priority.valueOf(req.getPriority()));
        task.setStatus(Status.TODO);
        task.setProject(project);
        task.setDeadline(req.getDeadline());

        if (req.getAssigneeId() != null) {
            validateMember(req.getAssigneeId(), project.getId());
            task.setAssignee(userRepository.findById(req.getAssigneeId()).orElseThrow());
        }

        taskRepository.save(task);
        return TaskMapper.toResponse(task);
    }

    @Transactional
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest req, Long reqUserId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        User user =  userRepository.findById(reqUserId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        validateTaskUpdate(req, task, user);

        if(req.getAssigneeId() != null) {
            User userAssignee = userRepository.findById(req.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setAssignee(userAssignee);
            validateMember(req.getAssigneeId(), task.getProject().getId());
        }

        if (req.getStatus() != null) task.setStatus(Status.valueOf(req.getStatus()));
        if (req.getTitle() != null) task.setTitle(req.getTitle());
        if (req.getDescription() != null) task.setDescription(req.getDescription());
        if (req.getPriority() != null) task.setPriority(Priority.valueOf(req.getPriority()));
        if (req.getDeadline() != null) task.setDeadline(req.getDeadline());

        Task taskSaved = taskRepository.save(task);
        return TaskMapper.toResponse(taskSaved);
    }

    public Page<TaskResponse> listTasks(TaskFilter filter, Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(TaskSpecification.withFilters(filter), pageable);
        return tasks.map(TaskMapper::toResponse);
    }

    public Page<TaskResponse> searchTask(Pageable pageable, String text) {
        Page<Task> tasks = taskRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text, pageable);
        return tasks.map(TaskMapper::toResponse);
    }

    private void validateMember(Long userId, Long projectId) {
        if (!projectMemberRepository.existsByUserIdAndProjectId(userId, projectId)) {
            throw new RuntimeException("User not in project");
        }
    }

    private void validateTaskUpdate(UpdateTaskRequest req, Task task, User user) {
        if (req.getStatus() != null) {
            Status newStatus = Status.valueOf(req.getStatus());

            if (task.getStatus() == Status.DONE && newStatus == Status.TODO){
                throw new RuntimeException("Invalid transition");
            }

            if (task.getPriority() == Priority.CRITICAL && newStatus == Status.DONE) {
                boolean isAdmin = projectMemberRepository
                        .existsByUserIdAndProjectIdAndRole(user.getId(), task.getProject().getId(), Role.ADMIN);

                if (!isAdmin) {
                    throw new RuntimeException("Only admin can close critical tasks");
                }
            }

            if (newStatus == Status.IN_PROGRESS) {
                long count = taskRepository.countByAssigneeIdAndStatus(task.getAssignee().getId(), Status.IN_PROGRESS);
                if (count >= 5){
                    throw new RuntimeException(" Work In Progress limit reached");
                }
            }
        }
    }

}
