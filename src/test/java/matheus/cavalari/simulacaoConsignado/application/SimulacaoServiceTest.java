package matheus.cavalari.simulacaoConsignado.application;
import matheus.cavalari.simulacaoConsignado.adapter.out.SimulacaoRepository;
import matheus.cavalari.simulacaoConsignado.application.exception.PrazoMaximoExcedidoException;
import matheus.cavalari.simulacaoConsignado.domain.Cliente;
import matheus.cavalari.simulacaoConsignado.domain.Simulacao;
import matheus.cavalari.simulacaoConsignado.dto.SimulacaoInputDTO;
import matheus.cavalari.simulacaoConsignado.enums.Convenio;
import matheus.cavalari.simulacaoConsignado.enums.SegmentoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimulacaoServiceTest {

    private SimulacaoService simulacaoService;

    @Mock
    private SimulacaoRepository simulacaoRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        simulacaoService = new SimulacaoService(simulacaoRepository, restTemplate);
    }

    @Test
    public void testListarSimulacoes() {
        List<Simulacao> simulacoes = new ArrayList<>();
        when(simulacaoRepository.findAll()).thenReturn(simulacoes);

        List<Simulacao> result = simulacaoService.listarSimulacoes();

        assertSame(simulacoes, result);
    }

    @Test
    public void testGetSimulacao() {
        Simulacao simulacao = new Simulacao();
        when(simulacaoRepository.findById(1L)).thenReturn(Optional.of(simulacao));

        Simulacao result = simulacaoService.getSimulacao(1L);

        assertSame(simulacao, result);
    }

    @Test
    public void testSimularConsignado() {
        Cliente cliente = new Cliente();
        cliente.setSegmento(SegmentoCliente.VAREJO);
        cliente.setConvenio(Convenio.EMPRESA_PRIVADA);
        cliente.setCorrentista(true);

        ResponseEntity<Cliente> responseEntity = new ResponseEntity<>(cliente, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(Cliente.class))).thenReturn(responseEntity);

        SimulacaoInputDTO simulacaoInput = new SimulacaoInputDTO();
        simulacaoInput.setCpfCliente("111.111.111-11");
        simulacaoInput.setQuantidadeParcelas(12);
        simulacaoInput.setValorSolicitado(BigDecimal.valueOf(1000));

        Simulacao result = simulacaoService.simularConsignado(simulacaoInput);

        assertNotNull(result);
        assertEquals("111.111.111-11", result.getCpfCliente());
        assertEquals(Convenio.EMPRESA_PRIVADA, result.getConvenioCliente());
        assertEquals(12, result.getQuantidadeParcelas());
        assertEquals(BigDecimal.valueOf(1000), result.getValorSolicitado());
    }

    @Test
    public void testSimularConsignadoPrazoMaximoExcedido() {
        SimulacaoInputDTO simulacaoInput = new SimulacaoInputDTO();
        simulacaoInput.setCpfCliente("123.456.789-00");
        simulacaoInput.setQuantidadeParcelas(50); // Mais do que o prazo máximo para Varejo

        Cliente cliente = new Cliente();
        cliente.setSegmento(SegmentoCliente.VAREJO);

        ResponseEntity<Cliente> responseEntity = ResponseEntity.ok(cliente); // Criar o ResponseEntity com o cliente válido
        when(restTemplate.getForEntity(anyString(), eq(Cliente.class))).thenReturn(responseEntity);

        assertThatThrownBy(() -> simulacaoService.simularConsignado(simulacaoInput))
                .isInstanceOf(PrazoMaximoExcedidoException.class)
                .hasMessage("Prazo máximo de simulação excedido para o segmento do cliente");
    }
}
