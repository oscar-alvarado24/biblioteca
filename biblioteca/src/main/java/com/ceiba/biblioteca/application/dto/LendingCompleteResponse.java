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
    private String identificaciónUsuario;
    private int tipoUsuario;
}
