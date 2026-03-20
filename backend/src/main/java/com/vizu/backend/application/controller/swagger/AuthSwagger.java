package com.vizu.backend.application.controller.swagger;

import com.vizu.backend.application.controller.dto.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthSwagger {

    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT")
    @ApiResponse(
            responseCode = "200",
            description = "Login realizado com sucesso, retorna o token JWT",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                            {
                              "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                            }
                            """
                    )
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Requisição inválida ou credenciais incorretas",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                            "Invalid request!"
                            """
                    )
            )
    )
	ResponseEntity<?> login(
			@RequestBody(description = "Dados de login do usuário", required = true, content = @Content(schema = @Schema(implementation = LoginRequest.class), examples = @ExampleObject(value = """
					{
					  "email": "carlos@email.com",
					  "password": "123456"
					}
					"""))) LoginRequest req);
}