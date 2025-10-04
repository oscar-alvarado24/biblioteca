package com.ceiba.biblioteca.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LendingRequest {
    private  String isbn;
    private String identificacionUsuario;
    private int tipoUsuario;
}
