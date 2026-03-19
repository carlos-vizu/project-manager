package com.vizu.backend.mappers;

import com.vizu.backend.application.controller.dto.response.ProjectMemberResponse;
import com.vizu.backend.domain.model.ProjectMember;

import java.util.List;

public class ProjectMemberMapper {

    public static ProjectMemberResponse toResponse(ProjectMember projectMember) {
        ProjectMemberResponse response = new ProjectMemberResponse();
        response.setUserId(projectMember.getUser().getId());
        response.setUserName(projectMember.getUser().getName());
        response.setUserEmail(projectMember.getUser().getEmail());
        response.setUserRole(projectMember.getRole().toString());

        return response;
    }

    public static List<ProjectMemberResponse> listEntityToResponseList(List<ProjectMember> projectMembers) {
        return projectMembers.stream()
                .map(projectMember ->
                        new ProjectMemberResponse(projectMember.getId(),
                                projectMember.getProject().getName(),
                                projectMember.getUser().getEmail(),
                                projectMember.getRole().toString()))
                .toList();
    }

}
