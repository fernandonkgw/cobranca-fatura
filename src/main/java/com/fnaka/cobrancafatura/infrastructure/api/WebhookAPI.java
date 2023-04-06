package com.fnaka.cobrancafatura.infrastructure.api;

import com.fnaka.cobrancafatura.infrastructure.simulador.models.CriaBoletoRequest;
import com.fnaka.cobrancafatura.infrastructure.simulador.models.CriaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.webhook.models.RecebeNotificacaoPagamentoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/webhooks")
@Tag(name = "Webhook", description = "Recebe notificacao de pagamento do Banco do Brasil")
public interface WebhookAPI {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Recebe notificacao de pagamento do boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    void recebeNotificacao(RecebeNotificacaoPagamentoRequest input);
}
