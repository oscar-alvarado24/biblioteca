package com.ceiba.biblioteca.infrastructure.input.rest;

import com.ceiba.biblioteca.calificador.ResultadoPrestarTest;
import com.ceiba.biblioteca.calificador.SolicitudPrestarLibroTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class LendingControllerTest {
    public static final int USER_AFFILIATE = 1;
    public static final int USER_EMPLOYEE = 2;
    public static final int USER_INVITED = 3;
    public static final int USER_UNKNOWN = 5;
    public static final int DAYS_LENDING_USER_AFFILIATE = 10;
    public static final int DAYS_LENDING_USER_EMPLOYEE = 8;
    public static final int DAYS_LENDING_USER_INVITED = 7;
    public static final String URL_SAVE = "/prestamo";
    public static final String URL_GET = URL_SAVE + "/%d";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String addDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return result.format(format);
    }

    @Test
    void saveRequestBookLendingForUserInvitedWithOutBookLendingActive() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_INVITED)))).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_INVITED)))).andReturn();
    }

    @Test
    void saveRequestBookLendingForUserEmployeeWithOutBookLendingActive() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_EMPLOYEE)))).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_EMPLOYEE)))).andReturn();
    }

    @Test
    void saveRequestBookLendingForUserAffiliateWithOutBookLendingActive() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_AFFILIATE)))).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_AFFILIATE)))).andReturn();
    }

    @Test
    void saveRequestBookLendingForUserUnknown() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "974148", USER_UNKNOWN)))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("Tipo de usuario no permitido en la biblioteca"))).andReturn();
    }

    @Test
    void saveSuccessfullyRequestBookLendingForUserNotInvitedWithBookLendingActive() throws Exception {
        {
            mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_AFFILIATE)))).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_AFFILIATE)))).andReturn();


            mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "123456", USER_AFFILIATE)))).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_AFFILIATE)))).andReturn();
        }
    }

    @Test
    void generateExceptionWhenSaveRequestBookLendingForUserInvitedWithBookLendingActive() throws Exception {
        {
            mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_INVITED)))).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_INVITED)))).andReturn();


            mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "123456", USER_INVITED)))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("El usuario con identificación 123456 ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo"))).andReturn();
        }
    }

    @Test
    void getLendingSuccessfullyForUserAffiliate() throws Exception {
        MvcResult resultBookLending = mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN1234", "123456", USER_AFFILIATE)))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andReturn();

        ResultadoPrestarTest resultLending = objectMapper.readValue(resultBookLending.getResponse().getContentAsString(), ResultadoPrestarTest.class);

        mvc.perform(MockMvcRequestBuilders.get(String.format(URL_GET, resultLending.getId())).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_AFFILIATE)))).andExpect(jsonPath("$.isbn", is("ISBN1234"))).andExpect(jsonPath("$.identificacionUsuario", is("123456"))).andExpect(jsonPath("$.tipoUsuario", is(USER_AFFILIATE)));
    }

    @Test
    void getLendingSuccessfullyForUserEmployee() throws Exception {
        MvcResult resultBookLending = mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN1234", "123456", USER_EMPLOYEE)))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andReturn();

        ResultadoPrestarTest resultLending = objectMapper.readValue(resultBookLending.getResponse().getContentAsString(), ResultadoPrestarTest.class);

        mvc.perform(MockMvcRequestBuilders.get(String.format(URL_GET, resultLending.getId())).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_EMPLOYEE)))).andExpect(jsonPath("$.isbn", is("ISBN1234"))).andExpect(jsonPath("$.identificacionUsuario", is("123456"))).andExpect(jsonPath("$.tipoUsuario", is(USER_EMPLOYEE)));
    }

    @Test
    void getLendingSuccessfullyForUserInvited() throws Exception {
        MvcResult resultBookLending = mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN1234", "123456", USER_INVITED)))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion").exists()).andReturn();

        ResultadoPrestarTest resultLending = objectMapper.readValue(resultBookLending.getResponse().getContentAsString(), ResultadoPrestarTest.class);

        mvc.perform(MockMvcRequestBuilders.get(String.format(URL_GET, resultLending.getId())).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_INVITED)))).andExpect(jsonPath("$.isbn", is("ISBN1234"))).andExpect(jsonPath("$.identificacionUsuario", is("123456"))).andExpect(jsonPath("$.tipoUsuario", is(USER_INVITED)));
    }

    @Test
    void getLendingThatDoNotExistAndReceiveAnException() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(String.format(URL_GET, 5)).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "123456", USER_AFFILIATE)))).andExpect(status().isNotFound()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("Prestamo con id 5 no fue encontrado"))).andReturn();
    }

    @Test
    void isbnCanOnlyHaveCharactersAlphanumeric() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885-", "123456", USER_AFFILIATE)))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("Solo se permiten caracteres alfanuméricos para el isbn"))).andReturn();
    }

    @Test
    void userIdCanOnlyHaveCharactersAlphanumeric() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "12-3456", USER_AFFILIATE)))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("Solo se permiten caracteres alfanuméricos para la identificacion del usuario"))).andReturn();
    }

    @Test
    void isbnCanHaveMaximumOfTenDigits() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885035", "123456", USER_AFFILIATE)))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("El isbn puede tener maximo 10 digitos"))).andReturn();
    }

    @Test
    void userIdCanHaveMaximumOfTenDigits() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "12345678901", USER_AFFILIATE)))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("La identificacion del usuario puede tener maximo 10 digitos"))).andReturn();
    }

    @Test
    void userTypeShouldBeANumberOfOneDigit() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "12345678901", 10)))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("El tipo de usuario debe ser un valor de un digito"))).andReturn();
    }

    @Test
    void isbnShouldNotBeNull() throws Exception {
        String json = "{" + "\"identificacionUsuario\":\"123456\"," + "\"tipoUsuario\": 5}";
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("El isbn es obligatorio por lo que no puede estar vacio"))).andReturn();
    }

    @Test
    void userIdShouldNotBeNull() throws Exception {
        String json = "{" + "\"isbn\":\"ISBN1234\"," + "\"tipoUsuario\": 5}";
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("La identificacion delusuario es obligatoria por lo que no puede estar vacio"))).andReturn();
    }

    @Test
    void userTypeShouldNotBeNull() throws Exception {
        String json = "{" + "\"isbn\":\"ISBN1234\"," + "\"identificacionUsuario\":\"123456\"}";
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("El tipo de usuario es obligatorio"))).andReturn();
    }

    @Test
    void stringCanOnlyHaveCharactersValid() throws Exception {
        String jsonInvalid = "{" + "\"isbn\":\"ISBN1234\"," + "\"identificacionUsuario\":''," + "\"tipoUsuario\": 5}";
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(jsonInvalid)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("Error con los datos en la peticion, por favor revisa que cumplen con los requerimientos"))).andReturn();
    }

    @Test
    void userTypeMustBeAnInteger() throws Exception {
        String json = "{" + "\"isbn\":\"ISBN1234\"," + "\"identificacionUsuario\":\"123456\"," + "\"tipoUsuario\": 5.5}";
        mvc.perform(MockMvcRequestBuilders.post(URL_SAVE).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists()).andExpect(jsonPath("$.mensaje", is("El tipo de usuario debe ser un numero entero"))).andReturn();
    }
}