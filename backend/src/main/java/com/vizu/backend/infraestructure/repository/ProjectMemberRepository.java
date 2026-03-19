package com.vizu.backend.infraestructure.repository;

import com.vizu.backend.domain.enums.Role;
import com.vizu.backend.domain.model.ProjectMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    boolean existsByUserIdAndProjectId(Long userId, Long projectId);

    boolean existsByUserIdAndProjectIdAndRole(Long userId, Long projectId, Role role);

    List<ProjectMember> findByProjectId(Long projectId);

    Optional<ProjectMember> findByUserIdAndProjectId(Long userId, Long projectId);

    Page<ProjectMember> findAllByProjectIdOrderByNameAsc(Long projectId, Pageable pageable);
}
