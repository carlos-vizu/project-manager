package com.vizu.backend.infraestructure.repository;

import com.vizu.backend.domain.enums.Status;
import com.vizu.backend.domain.model.Project;
import com.vizu.backend.domain.model.Task;
import com.vizu.backend.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    long countByAssigneeIdAndStatus(Long assigneeId, Status status);
    Page<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);
    List<Task> findByProjectId(Long projectId);
}