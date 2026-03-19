package com.vizu.backend.application.controller.swagger;

import com.vizu.backend.application.controller.dto.response.ProjectMemberResponse;
import com.vizu.backend.application.controller.dto.response.ProjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProjectSwagger {

    @Operation(summary = "Criar projeto")
    @ApiResponse(
            responseCode = "200",
            description = "Projeto criado",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                            {
                              "id": 1,
                              "name": "Projeto Backend",
                              "ownerId": 1,
                              "ownerName": "Carlos"
                            }
                            """
                    )
            )
    )
    ResponseEntity<ProjectResponse> create(@Parameter(example = "Projeto Backend") String name, @Parameter(example = "carlos@email.com") String email);

    @Operation(summary = "Buscar projeto por ID")
    @ApiResponse(
            responseCode = "200",
            description = "Projeto encontrado",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                            {
                              "id": 1,
                              "name": "Projeto Backend",
                              "ownerId": 1,
                              "ownerName": "Carlos"
                            }
                            """
                    )
            )
    )
    ResponseEntity<ProjectResponse> get(@Parameter(example = "1") Long id);

    @Operation(summary = "Listar projetos com paginação")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de projetos",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                            {
                              "content": [
                                {
                                  "id": 1,
                                  "name": "Projeto A",
                                  "ownerId": 1,
                                  "ownerName": "Carlos"
                                },
                                {
                                  "id": 2,
                                  "name": "Projeto B",
                                  "ownerId": 2,
                                  "ownerName": "João"
                                }
                              ],
                              "pageable": {
                                "pageNumber": 0,
                                "pageSize": 10
                              },
                              "totalElements": 2,
                              "totalPages": 1
                            }
                            """
                    )
            )
    )
    ResponseEntity<Page<ProjectResponse>> listAll(@Parameter(description = "Paginação") Pageable pageable);

    @Operation(summary = "Adicionar membro ao projeto")
    ResponseEntity<Void> addMember(@Parameter(example = "1") Long id, @Parameter(example = "2") Long userId);

    @Operation(summary = "Remover membro do projeto")
    ResponseEntity<Void> removeMember(@Parameter(example = "1") Long id, @Parameter(example = "2") Long userId);

    @Operation(summary = "Listar membros do projeto")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de membros",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                            {
                              "content": [
                                {
                                  "userId": 1,
                                  "userName": "Carlos",
                                  "userEmail": "carlos@email.com",
                                  "role": "ADMIN"
                                },
                                {
                                  "userId": 2,
                                  "userName": "João",
                                  "userEmail": "joao@email.com",
                                  "role": "MEMBER"
                                }
                              ],
                              "totalElements": 2,
                              "totalPages": 1
                            }
                            """
                    )
            )
    )
    ResponseEntity<Page<ProjectMemberResponse>> members(@Parameter(example = "1") Long id, Pageable pageable);

    @Operation(summary = "Resumo do projeto")
    @ApiResponse(
            responseCode = "200",
            description = "Resumo de tarefas",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                            {
                              "byStatus": {
                                "TODO": 10,
                                "IN_PROGRESS": 5,
                                "DONE": 20
                              },
                              "byPriority": {
                                "LOW": 3,
                                "HIGH": 10,
                                "CRITICAL": 2
                              }
                            }
                            """
                    )
            )
    )
    ResponseEntity<Map<String, Map<String, Long>>> summary(@Parameter(example = "1") Long id);
}