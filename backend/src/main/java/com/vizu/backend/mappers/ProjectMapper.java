package com.vizu.backend.mappers;

import com.vizu.backend.application.controller.dto.response.ProjectResponse;
import com.vizu.backend.domain.model.Project;
import com.vizu.backend.domain.model.ProjectMember;

import java.util.List;

public class ProjectMapper {

    public static ProjectResponse toResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setOwnerId(project.getOwner().getId());
        response.setOwnerName(project.getOwner().getName());

        return response;
    }

    public static List<ProjectResponse> toResponseList(List<Project> projects) {
        return projects.stream()
                .map(project ->
                        new ProjectResponse(project.getId(),
                                project.getName(),
                                project.getOwner().getId(),
                                project.getOwner().getName()))
                .toList();
    }
}