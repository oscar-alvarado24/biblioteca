package com.ceiba.biblioteca.infrastructure.input.rest;

import com.ceiba.biblioteca.application.dto.LendingBasicResponse;
import com.ceiba.biblioteca.application.dto.LendingRequest;
import com.ceiba.biblioteca.application.handler.ILendingHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/prestamo")
public class LendingController {
    private final ILendingHandler lendingHandler;

    @Operation(summary = "Add a new route")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Route created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Route already exists", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<LendingBasicResponse> saveLending(@RequestBody LendingRequest lendingRequest){
         return ResponseEntity.ok().body(lendingHandler.saveLending(lendingRequest));
    }
}
