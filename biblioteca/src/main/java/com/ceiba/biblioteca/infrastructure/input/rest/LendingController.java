package com.ceiba.biblioteca.infrastructure.input.rest;

import com.ceiba.biblioteca.application.dto.LendingBasicResponse;
import com.ceiba.biblioteca.application.dto.LendingCompleteResponse;
import com.ceiba.biblioteca.application.dto.LendingRequest;
import com.ceiba.biblioteca.application.handler.ILendingHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/prestamo")
public class LendingController {
    private final ILendingHandler lendingHandler;

    @Operation(summary = "Add a book lending")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lending created", content = @Content),
            @ApiResponse(responseCode = "400", description = "User invited with book lending active or error in the request", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<LendingBasicResponse> saveLending(@Valid @RequestBody LendingRequest lendingRequest){
         return ResponseEntity.ok().body(lendingHandler.saveLending(lendingRequest));
    }

    @Operation(summary = "Get a book lending")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get lending successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lending not found", content = @Content)
    })
    @GetMapping("/{id-prestamo}")
    public ResponseEntity<LendingCompleteResponse> getLendingById(@PathVariable(name = "id-prestamo")int lendingId){
        return ResponseEntity.ok().body(lendingHandler.getLendingById(lendingId));
    }
}
