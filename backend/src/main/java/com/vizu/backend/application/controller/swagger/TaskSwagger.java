package com.vizu.backend.application.controller.swagger;

import com.vizu.backend.application.controller.dto.request.CreateTaskRequest;
import com.vizu.backend.application.controller.dto.request.TaskFilter;
import com.vizu.backend.application.controller.dto.request.UpdateTaskRequest;
import com.vizu.backend.application.controller.dto.response.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TaskSwagger {

    @Operation(
            summary = "Criar tarefa",
            description = "Cria uma nova tarefa dentro de um projeto"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tarefa criada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Exemplo de resposta",
                            value = """
                            {
                              "id": 1,
                              "title": "Corrigir bug login",
                              "description": "Erro ao autenticar usuário",
                              "status": "TODO",
                              "priority": "HIGH",
                              "projectId": 1
                            }
                            """
                    )
            )
    )
    //io.swagger.v3.oas.annotations.parameters
    ResponseEntity<TaskResponse> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da nova tarefa",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateTaskRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "title": "Corrigir bug login",
                                      "description": "Erro ao autenticar",
                                      "priority": "HIGH",
                                      "deadline": "2026-03-20T18:00:00",
                                      "projectId": 1,
                                      "assigneeId": 2
                                    }
                                    """
                            )
                    )
            )
            CreateTaskRequest req
    );

    @Operation(summary = "Atualizar tarefa",description = "Atualiza dados ou status de uma tarefa com regras de negócio aplicadas")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada")
    @ApiResponse(responseCode = "400", description = "Regra de negócio violada")
    ResponseEntity<TaskResponse> update(
            @Parameter(description = "ID da tarefa", example = "1") Long taskId,
            @Parameter(description = "ID do usuário requisitante", example = "2") Long reqUserId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos para atualização",
                    content = @Content(schema = @Schema(implementation = UpdateTaskRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "status": "IN_PROGRESS",
                                      "priority": "CRITICAL"
                                    }
                                    """
                            )
                    )
            )
            UpdateTaskRequest req
    );

    @Operation(summary = "Listar tarefas", description = "Lista tarefas com filtros, ordenação e paginação")
    ResponseEntity<Page<TaskResponse>> list(@Parameter(description = "Filtros de busca") TaskFilter filter, @Parameter(description = "Paginação") Pageable pageable);

    @Operation( summary = "Busca textual", description = "Busca tarefas por título ou descrição")
    ResponseEntity<Page<TaskResponse>> search(
            @Parameter(description = "Texto da busca", example = "bug") String text,
            @Parameter(description = "Paginação") Pageable pageable
    );
}