package matheus.cavalari.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import matheus.cavalari.api.service.ServicoBancario;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicoBancario servicoBancario;

    @Test
    void obterTodosClientes() throws Exception {
        mockMvc.perform(get("/matheus/cavalari/api/v1/cliente"))
                .andExpect(status().isOk());
    }

    @Test
    void obterClientePorNumeroContaInexiatente() throws Exception {
        mockMvc.perform(get("/matheus/cavalari/api/v1/cliente/123456"))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}