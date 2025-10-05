package com.ceiba.biblioteca.infrastructure.output.database.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InfrastructureConstants {
    public static final String MSG_LENDING_NOT_FOUND = "Prestamo con id %d no fue encontrado";
    public static final String MSG_MISMATCHED_INPUT_EXCEPTION = "El tipo de usuario debe ser un numero entero";
    public static final String MSG_JSON_PARSE_EXCEPTION = "Error con los datos en la peticion, por favor revisa que cumplen con los requerimientos";
}
