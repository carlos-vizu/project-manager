package com.vizu.backend.application.controller;

import com.vizu.backend.application.controller.dto.response.ProjectMemberResponse;
import com.vizu.backend.application.controller.dto.response.ProjectResponse;
import com.vizu.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@RequestParam String name, @RequestParam String email) {
        return ResponseEntity.ok(projectService.createProject(name, email));
    }

    // ✔ Buscar projeto
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    // ResponseEntity<Page< >>
    @GetMapping
    public ResponseEntity<Page<ProjectResponse>> listAll(Pageable pageable) {
        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<Void> addMember(@PathVariable Long id, @RequestParam Long userId) {
        projectService.addMember(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long id, @PathVariable Long userId) {
        projectService.removeMember(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<Page<ProjectMemberResponse>> members(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(projectService.listMembers(pageable, id));
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<Map<String, Map<String, Long>>> summary(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.summary(id));
    }
}