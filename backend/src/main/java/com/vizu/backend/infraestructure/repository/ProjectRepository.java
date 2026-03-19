package com.vizu.backend.infraestructure.repository;

import com.vizu.backend.application.controller.dto.response.ProjectResponse;
import com.vizu.backend.domain.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findAllByOrderByNameAsc(Pageable pageable);
}
