package com.vizu.backend.service.impl;

import com.vizu.backend.application.controller.dto.response.ProjectMemberResponse;
import com.vizu.backend.application.controller.dto.response.ProjectResponse;
import com.vizu.backend.domain.enums.Role;
import com.vizu.backend.domain.model.Project;
import com.vizu.backend.domain.model.ProjectMember;
import com.vizu.backend.domain.model.User;
import com.vizu.backend.infraestructure.repository.ProjectMemberRepository;
import com.vizu.backend.infraestructure.repository.ProjectRepository;
import com.vizu.backend.infraestructure.repository.UserRepository;
import com.vizu.backend.mappers.ProjectMapper;
import com.vizu.backend.mappers.ProjectMemberMapper;
import com.vizu.backend.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMemberRepository projectMemberRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
    }

    public Page<ProjectResponse> getAllProjects(Pageable pageable) {
        Page<Project> projects = projectRepository.findAllByOrderByNameAsc(pageable);
        return projects.map(ProjectMapper::toResponse);
    }

    @Transactional
    public ProjectResponse createProject(String name, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setName(name);
        project.setOwner(user);
        Project saved = projectRepository.save(project);

        // cria membership como ADMIN
        ProjectMember member = new ProjectMember();
        member.setUser(user);
        member.setProject(saved);
        member.setRole(Role.ADMIN);
        projectMemberRepository.save(member);

        return ProjectMapper.toResponse(saved);
    }

    public ProjectResponse getProject(Long projectId) {
        return ProjectMapper.toResponse(projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found")));
    }

    @Transactional
    public void addMember(Long projectId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectMember member = new ProjectMember();
        member.setUser(user);
        member.setProject(project);
        member.setRole(Role.MEMBER);

        projectMemberRepository.save(member);
    }

    @Transactional
    public void removeMember(Long projectId, Long userId) {
        ProjectMember member = projectMemberRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        projectMemberRepository.delete(member);
    }

    public Page<ProjectMemberResponse> listMembers(Pageable pageable, Long projectId) {
        Page<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectIdOrderByNameAsc(projectId, pageable);
        return projectMembers.map(ProjectMemberMapper::toResponse);
    }
}
