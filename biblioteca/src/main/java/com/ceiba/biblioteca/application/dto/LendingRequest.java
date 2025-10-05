package com.ceiba.biblioteca.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LendingRequest {
    @NotBlank(message = "El isbn es obligatorio por lo que no puede estar vacio")
    @Size(max = 10, message = "El isbn puede tener maximo 10 digitos")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo se permiten caracteres alfanuméricos para el isbn")
    private  String isbn;

    @NotBlank(message = "La identificacion delusuario es obligatoria por lo que no puede estar vacio")
    @Size(max = 10, message = "La identificacion del usuario puede tener maximo 10 digitos")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo se permiten caracteres alfanuméricos para la identificacion del usuario")
    private String identificacionUsuario;

    @NotNull(message = "El tipo de usuario es obligatorio")
    @Min(value = 1, message = "El tipo de usuario debe ser positivo")
    @Max(value = 9, message = "El tipo de usuario debe ser un valor de un digito")
    private Integer tipoUsuario;
}
