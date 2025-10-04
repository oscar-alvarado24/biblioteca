package com.ceiba.biblioteca.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LendingCompleteResponse extends LendingBasicResponse{
    private String isbn;
    private String identificacionUsuario;
    private int tipoUsuario;

    public LendingCompleteResponse(int id, String fechaMaximaDevolucion, String isbn, String identificacionUsuario, int tipoUsuario) {
        super(id, fechaMaximaDevolucion);
        this.isbn = isbn;
        this.identificacionUsuario = identificacionUsuario;
        this.tipoUsuario = tipoUsuario;
    }
}
