package matheus.cavalari.simulacaoConsignado.adapter.in;

import matheus.cavalari.simulacaoConsignado.application.ISimulacaoService;
import matheus.cavalari.simulacaoConsignado.application.exception.ClienteNaoEncontradoException;
import matheus.cavalari.simulacaoConsignado.application.exception.PrazoMaximoExcedidoException;
import matheus.cavalari.simulacaoConsignado.domain.Simulacao;
import matheus.cavalari.simulacaoConsignado.dto.SimulacaoInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimulacaoControllerTest {

    private SimulacaoController simulacaoController;

    @Mock
    private ISimulacaoService simulacaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        simulacaoController = new SimulacaoController(simulacaoService);
    }

    @Test
    public void testListarSimulacoes() {
        List<Simulacao> simulacoes = new ArrayList<>();
        when(simulacaoService.listarSimulacoes()).thenReturn(simulacoes);

        ResponseEntity<?> responseEntity = simulacaoController.listarSimulacoes();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(simulacoes, responseEntity.getBody());
    }

    @Test
    public void testGetSimulacao() {
        Simulacao simulacao = new Simulacao();
        when(simulacaoService.getSimulacao(1L)).thenReturn(simulacao);

        ResponseEntity<?> responseEntity = simulacaoController.getSimulacao(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(simulacao, responseEntity.getBody());
    }

    @Test
    public void testGetSimulacaoNotFound() {
        when(simulacaoService.getSimulacao(1L)).thenReturn(null);

        ResponseEntity<?> responseEntity = simulacaoController.getSimulacao(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Simulação não encontrada para o ID informado", responseEntity.getBody());
    }

    @Test
    public void testSimularConsignado() {
        SimulacaoInputDTO simulacaoInputDTO = new SimulacaoInputDTO();
        when(simulacaoService.simularConsignado(simulacaoInputDTO)).thenReturn(new Simulacao());

        ResponseEntity<?> responseEntity = simulacaoController.simularConsignado(simulacaoInputDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testSimularConsignadoClienteNaoEncontrado() {
        SimulacaoInputDTO simulacaoInputDTO = new SimulacaoInputDTO();
        when(simulacaoService.simularConsignado(simulacaoInputDTO)).thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        ResponseEntity<?> responseEntity = simulacaoController.simularConsignado(simulacaoInputDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Cliente não encontrado", responseEntity.getBody());
    }

    @Test
    public void testSimularConsignadoPrazoMaximoExcedido() {
        SimulacaoInputDTO simulacaoInputDTO = new SimulacaoInputDTO();
        when(simulacaoService.simularConsignado(simulacaoInputDTO)).thenThrow(new PrazoMaximoExcedidoException("Prazo máximo excedido"));

        ResponseEntity<?> responseEntity = simulacaoController.simularConsignado(simulacaoInputDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Prazo máximo excedido", responseEntity.getBody());
    }
}
