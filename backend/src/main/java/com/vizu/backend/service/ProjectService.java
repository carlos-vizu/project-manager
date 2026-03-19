package com.vizu.backend.service;

import com.vizu.backend.application.controller.dto.response.ProjectMemberResponse;
import com.vizu.backend.application.controller.dto.response.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    public Page<ProjectResponse> getAllProjects(Pageable pageable);

    public ProjectResponse createProject(String name, String userEmail);

    public ProjectResponse getProject(Long projectId);

    public void addMember(Long projectId, Long userId);

    public void removeMember(Long projectId, Long userId);

    public Page<ProjectMemberResponse> listMembers(Pageable pageable, Long projectId);

}
