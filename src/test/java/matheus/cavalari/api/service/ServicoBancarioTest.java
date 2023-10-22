package matheus.cavalari.api.service;

import matheus.cavalari.api.Repository.ClienteRepository;
import matheus.cavalari.api.Repository.TransferenciaRepository;
import matheus.cavalari.api.entity.Cliente;
import matheus.cavalari.api.entity.Transferencia;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ServicoBancarioTest {

    @Autowired
    private ServicoBancario servicoBancario;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private TransferenciaRepository transferenciaRepository;

    @Test
    void criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Novo Cliente");
        cliente.setNumeroConta("123456");
        cliente.setSaldoConta(1000.0);

        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente novoCliente = servicoBancario.criarCliente(cliente);

        assertNotNull(novoCliente);
        assertEquals("123456", novoCliente.getNumeroConta());
    }

    @Test
    public void obterTodosClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Cliente 1");
        cliente1.setNumeroConta("123456");
        cliente1.setSaldoConta(1000.0);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Cliente 2");
        cliente2.setNumeroConta("789012");
        cliente2.setSaldoConta(2000.0);

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        Mockito.when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> clientesRetornados = servicoBancario.obterTodosClientes();

        assertFalse(clientesRetornados.isEmpty());
        assertEquals(2, clientesRetornados.size());
    }

    @Test
    public void obterClientePorNumeroConta() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Exemplo");
        cliente.setNumeroConta("123456");
        cliente.setSaldoConta(1000.0);

        Mockito.when(clienteRepository.findByNumeroConta("123456")).thenReturn(cliente);

        Cliente clienteRetornado = servicoBancario.obterClientePorNumeroConta("123456");

        assertNotNull(clienteRetornado);
        assertEquals("123456", clienteRetornado.getNumeroConta());
    }

    @Test
    public void realizarTransferencia() {
        Cliente clienteOrigem = new Cliente();
        clienteOrigem.setNome("Cliente Origem");
        clienteOrigem.setNumeroConta("123456");
        clienteOrigem.setSaldoConta(2000.0);

        Cliente clienteDestino = new Cliente();
        clienteDestino.setNome("Cliente Destino");
        clienteDestino.setNumeroConta("789012");
        clienteDestino.setSaldoConta(1000.0);

        Transferencia transferencia = new Transferencia();
        transferencia.setContaOrigem("123456");
        transferencia.setContaDestino("789012");
        transferencia.setValor(500.0);

        Mockito.when(clienteRepository.findByNumeroConta("123456")).thenReturn(clienteOrigem);
        Mockito.when(clienteRepository.findByNumeroConta("789012")).thenReturn(clienteDestino);

        Mockito.when(transferenciaRepository.save(transferencia)).thenReturn(transferencia);

        Transferencia transferenciaRealizada = servicoBancario.realizarTransferencia(transferencia);

        assertTrue(transferenciaRealizada.isSucesso());
        assertEquals(1500.0, clienteOrigem.getSaldoConta(), 0.01);
        assertEquals(1500.0, clienteDestino.getSaldoConta(), 0.01);
    }

    @Test
    public void obterTransferenciasPorConta() {
        Transferencia transferencia1 = new Transferencia();
        transferencia1.setContaOrigem("123456");
        transferencia1.setContaDestino("789012");
        transferencia1.setValor(500.0);

        Transferencia transferencia2 = new Transferencia();
        transferencia2.setContaOrigem("123456");
        transferencia2.setContaDestino("789012");
        transferencia2.setValor(300.0);

        List<Transferencia> transferencias = Arrays.asList(transferencia1, transferencia2);

        Mockito.when(transferenciaRepository.findByContaOrigemOrderByDataDesc("123456", Sort.by(Sort.Direction.DESC, "data")))
                .thenReturn(transferencias);

        List<Transferencia> transferenciasRetornadas = servicoBancario.obterTransferenciasPorConta("123456");

        assertFalse(transferenciasRetornadas.isEmpty());
        assertEquals(2, transferenciasRetornadas.size());
    }
}
