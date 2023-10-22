package matheus.cavalari.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import matheus.cavalari.api.entity.Cliente;
import matheus.cavalari.api.entity.Transferencia;
import matheus.cavalari.api.service.ServicoBancario;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransferenciaController.class)
class TransferenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicoBancario servicoBancario;

    @Test
    void realizarTransferencia() throws Exception {
        Cliente clienteOrigem = new Cliente();
        clienteOrigem.setNome("Cliente Origem");
        clienteOrigem.setNumeroConta("123456");
        clienteOrigem.setSaldoConta(1000.0);

        Cliente clienteDestino = new Cliente();
        clienteDestino.setNome("Cliente Destino");
        clienteDestino.setNumeroConta("789012");
        clienteDestino.setSaldoConta(1000.0);

        Transferencia transferencia = new Transferencia();
        transferencia.setContaOrigem("123456");
        transferencia.setContaDestino("789012");
        transferencia.setValor(500.0);

        ObjectMapper objectMapper = new ObjectMapper();
        String transferenciaJson = objectMapper.writeValueAsString(transferencia);

        mockMvc.perform(MockMvcRequestBuilders.post("/matheus/cavalari/api/v1/transferencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferenciaJson))
                .andExpect(status().isOk());
    }

    @Test
    void obterTransferenciasPorConta() throws Exception {
        mockMvc.perform(get("/matheus/cavalari/api/v1/transferencia/123456"))
                .andExpect(status().isOk());
    }
}