package com.fnaka.cobrancafatura.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnaka.cobrancafatura.ControllerTest;
import com.fnaka.cobrancafatura.application.boleto.busca.DefaultBuscaBoletoPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.buscapix.DefaultBuscaPixPorBoletoIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoCommand;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoOutput;
import com.fnaka.cobrancafatura.application.boleto.cria.DefaultCriaBoletoUseCase;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaBoletoRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ControllerTest(controllers = BoletoAPI.class)
public class BoletoAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DefaultCriaBoletoUseCase criaBoletoUseCase;

    @MockBean
    private DefaultBuscaBoletoPorIdUseCase buscaBoletoPorIdUseCase;

    @MockBean
    private DefaultBuscaPixPorBoletoIdUseCase buscaPixPorBoletoIdUseCase;

    @Test
    void givenAValidRequest_whenCallsCriaBoleto_shouldReturnResponseWithId() throws Exception {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNumeroTituloCliente = "12345678901234567890";
        final var expectedId = BoletoID.from("123");

        final var criaBoletoRequest = new CriaBoletoRequest(
                expectedConvenio, expectedNumeroTituloCliente
        );

        when(criaBoletoUseCase.execute(any()))
                .thenReturn(CriaBoletoOutput.from(expectedId));

        // when
        final var request = MockMvcRequestBuilders.post("/v1/boletos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(criaBoletoRequest));

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isCreated())
//                .andExpect(header().string(HttpHeaders.LOCATION, "/v1/boletos/" + expectedId.getValue()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId.getValue())));

        final var captor = ArgumentCaptor.forClass(CriaBoletoCommand.class);
        verify(criaBoletoUseCase).execute(captor.capture());
        final var actualCommand = captor.getValue();
        Assertions.assertEquals(expectedConvenio, actualCommand.convenio());
        Assertions.assertEquals(expectedNumeroTituloCliente, actualCommand.nossoNumero());
    }
}
