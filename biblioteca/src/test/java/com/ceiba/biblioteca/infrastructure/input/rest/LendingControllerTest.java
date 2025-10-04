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

    @Test
    void saveRequestBookLendingForUserInvitedWithOutBookLendingActive() throws Exception {
        String dateReturn = addDaysSkippingWeekends(LocalDate.now(),DAYS_LENDING_USER_INVITED );
        mvc.perform(
                        MockMvcRequestBuilders.post(URL_SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_INVITED))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion",is(dateReturn)))
                .andReturn();
    }
    @Test
    void saveRequestBookLendingForUserEmployeeWithOutBookLendingActive() throws Exception {
        String dateReturn = addDaysSkippingWeekends(LocalDate.now(),DAYS_LENDING_USER_EMPLOYEE );
        mvc.perform(
                        MockMvcRequestBuilders.post(URL_SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_EMPLOYEE))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion",is(dateReturn)))
                .andReturn();
    }

    @Test
    void saveRequestBookLendingForUserAffiliateWithOutBookLendingActive() throws Exception {
        String dateReturn = addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_AFFILIATE);
        mvc.perform(
                MockMvcRequestBuilders.post(URL_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_AFFILIATE))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fechaMaximaDevolucion").exists())
        .andExpect(jsonPath("$.fechaMaximaDevolucion",is(dateReturn)))
        .andReturn();
    }

    @Test
    void saveRequestBookLendingForUserUnknownWithBookLendingActive() throws Exception {


            {
                mvc.perform(
                                MockMvcRequestBuilders.post(URL_SAVE)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "974148", USER_UNKNOWN))))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.mensaje").exists())
                        .andExpect(jsonPath("$.mensaje",is("Tipo de usuario no permitido en la biblioteca")))
                        .andReturn();

        }
    }

    @Test
    void saveRequestBookLendingForUserAffiliateWithBookLendingActive() throws Exception {
        {
            mvc.perform(
                            MockMvcRequestBuilders.post(URL_SAVE)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7884", "123456", USER_AFFILIATE))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.fechaMaximaDevolucion").exists())
                    .andReturn();


            mvc.perform(
                            MockMvcRequestBuilders.post(URL_SAVE)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "123456", USER_AFFILIATE))))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensaje").exists())
                    .andExpect(jsonPath("$.mensaje",is("El usuario con identificación 123456 ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo")))
                    .andReturn();
        }
    }

    @Test
    void getLendingSuccessfullyForUserAffiliate() throws Exception {
        MvcResult resultBookLending = mvc.perform(MockMvcRequestBuilders
                        .post(URL_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN1234", "123456", USER_AFFILIATE))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists())
                .andReturn();

        ResultadoPrestarTest resultLending = objectMapper.readValue(resultBookLending.getResponse().getContentAsString(), ResultadoPrestarTest.class);

        mvc.perform(MockMvcRequestBuilders
                        .get(String.format(URL_GET,resultLending.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_AFFILIATE))))
                .andExpect(jsonPath("$.isbn", is("ISBN1234")))
                .andExpect(jsonPath("$.identificacionUsuario", is("123456")))
                .andExpect(jsonPath("$.tipoUsuario", is(USER_AFFILIATE)));
    }

    @Test
    void getLendingSuccessfullyForUserEmployee() throws Exception {
        MvcResult resultBookLending = mvc.perform(MockMvcRequestBuilders
                        .post(URL_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN1234", "123456", USER_EMPLOYEE))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists())
                .andReturn();

        ResultadoPrestarTest resultLending = objectMapper.readValue(resultBookLending.getResponse().getContentAsString(), ResultadoPrestarTest.class);

        mvc.perform(MockMvcRequestBuilders
                        .get(String.format(URL_GET,resultLending.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_EMPLOYEE))))
                .andExpect(jsonPath("$.isbn", is("ISBN1234")))
                .andExpect(jsonPath("$.identificacionUsuario", is("123456")))
                .andExpect(jsonPath("$.tipoUsuario", is(USER_EMPLOYEE)));
    }

    @Test
    void getLendingSuccessfullyForUserInvited() throws Exception {
        MvcResult resultBookLending = mvc.perform(MockMvcRequestBuilders
                        .post(URL_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN1234", "123456", USER_INVITED))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists())
                .andReturn();

        ResultadoPrestarTest resultLending = objectMapper.readValue(resultBookLending.getResponse().getContentAsString(), ResultadoPrestarTest.class);

        mvc.perform(MockMvcRequestBuilders
                        .get(String.format(URL_GET,resultLending.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion", is(addDaysSkippingWeekends(LocalDate.now(), DAYS_LENDING_USER_INVITED))))
                .andExpect(jsonPath("$.isbn", is("ISBN1234")))
                .andExpect(jsonPath("$.identificacionUsuario", is("123456")))
                .andExpect(jsonPath("$.tipoUsuario", is(USER_INVITED)));
    }

    @Test
    void getLendingThatDoNotExistAndReceiveAnException() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.get(String.format(URL_GET,5))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("ISBN7885", "123456", USER_AFFILIATE))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.mensaje",is("Prestamo con id 5 no fue encontrado")))
                .andReturn();
    }
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
    }