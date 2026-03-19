package com.vizu.backend.application.controller.swagger;

import com.vizu.backend.application.controller.dto.response.UserSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserSwagger {

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponse(
            responseCode = "200",
            description = "Usuário encontrado",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                            {
                              "id": 1,
                              "name": "Carlos",
                              "email": "carlos@email.com"
                            }
                            """
                    )
            )
    )
    ResponseEntity<UserSummaryResponse> getUser(@Parameter(description = "ID do usuário", example = "1") Long id);
}