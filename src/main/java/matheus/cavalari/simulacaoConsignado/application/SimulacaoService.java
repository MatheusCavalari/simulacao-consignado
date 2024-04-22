package matheus.cavalari.simulacaoConsignado.application;

import matheus.cavalari.simulacaoConsignado.adapter.out.SimulacaoRepository;
import matheus.cavalari.simulacaoConsignado.application.exception.ClienteNaoEncontradoException;
import matheus.cavalari.simulacaoConsignado.application.exception.PrazoMaximoExcedidoException;
import matheus.cavalari.simulacaoConsignado.domain.Cliente;
import matheus.cavalari.simulacaoConsignado.domain.Simulacao;
import matheus.cavalari.simulacaoConsignado.dto.SimulacaoInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SimulacaoService implements ISimulacaoService {

    private final SimulacaoRepository simulacaoRepository;
    private final RestTemplate restTemplate;

    private static final int PRAZO_PADRAO_NAO_CORRENTISTA = 12;
    private static final int PRAZO_VAREJO = 24;
    private static final int PRAZO_UNICLASS = 36;
    private static final int PRAZO_PERSON = 48;


    private static final BigDecimal TAXA_EMPRESA_PRIVADA = BigDecimal.valueOf(0.026);
    private static final BigDecimal TAXA_ORGAO_PUBLICO = BigDecimal.valueOf(0.022);
    private static final BigDecimal TAXA_INSS = BigDecimal.valueOf(0.016);

    @Value("${cliente.service.url}")
    private String clienteServiceUrl;

    @Autowired
    public SimulacaoService(SimulacaoRepository simulacaoRepository, RestTemplate restTemplate) {
        this.simulacaoRepository = simulacaoRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Simulacao> listarSimulacoes() {
        return simulacaoRepository.findAll();
    }

    @Override
    public Simulacao getSimulacao(Long id) {
        return simulacaoRepository.findById(id).orElse(null);
    }

    @Override
    public Simulacao simularConsignado(SimulacaoInputDTO simulacaoInput) {
        ResponseEntity<Cliente> responseEntity = restTemplate.getForEntity(clienteServiceUrl + "/cliente/cpf/" + simulacaoInput.getCpfCliente(), Cliente.class);
        Cliente cliente = responseEntity.getBody();
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado para o CPF informado: " + simulacaoInput.getCpfCliente());
        }

        int prazoMaximo = calcularPrazoMaximo(cliente);
        if (simulacaoInput.getQuantidadeParcelas() > prazoMaximo) {
            throw new PrazoMaximoExcedidoException("Prazo máximo de simulação excedido para o segmento do cliente");
        }

        BigDecimal taxa = calcularTaxa(cliente);
        BigDecimal taxaFinal = aplicarDescontoCorrentista(cliente, taxa);
        BigDecimal valorTotal = calcularValorTotal(simulacaoInput.getValorSolicitado(), taxaFinal, simulacaoInput.getQuantidadeParcelas());
        BigDecimal valorParcela = calcularValorParcela(valorTotal, simulacaoInput.getQuantidadeParcelas());

        Simulacao simulacao = Simulacao.builder()
                .dataSimulacao(LocalDate.now())
                .cpfCliente(simulacaoInput.getCpfCliente())
                .convenioCliente(cliente.getConvenio())
                .valorSolicitado(simulacaoInput.getValorSolicitado())
                .taxaAplicada(taxaFinal)
                .quantidadeParcelas(simulacaoInput.getQuantidadeParcelas())
                .valorTotal(valorTotal)
                .valorParcela(valorParcela)
                .build();

        simulacaoRepository.save(simulacao);
        return simulacao;
    }

    private BigDecimal aplicarDescontoCorrentista(Cliente cliente, BigDecimal taxa) {
        if (cliente.isCorrentista()) {
            BigDecimal desconto = taxa.multiply(BigDecimal.valueOf(0.05));
            return taxa.subtract(desconto);
        }
        return taxa;
    }

    private BigDecimal calcularTaxa(Cliente cliente) {
        switch (cliente.getConvenio()) {
            case EMPRESA_PRIVADA:
                return TAXA_EMPRESA_PRIVADA;
            case ORGAO_PUBLICO:
                return TAXA_ORGAO_PUBLICO;
            case INSS:
                return TAXA_INSS;
            default:
                return BigDecimal.ZERO;
        }
    }

    private int calcularPrazoMaximo(Cliente cliente) {
        if (cliente.getSegmento() == null) {
            return PRAZO_PADRAO_NAO_CORRENTISTA;
        }

        switch (cliente.getSegmento()) {
            case VAREJO:
                return PRAZO_VAREJO;
            case UNICLASS:
                return PRAZO_UNICLASS;
            case PERSON:
                return PRAZO_PERSON;
            default:
                return PRAZO_PADRAO_NAO_CORRENTISTA;
        }
    }

    private BigDecimal calcularValorTotal(BigDecimal valorSolicitado, BigDecimal taxa, int quantidadeParcelas) {
        return valorSolicitado.multiply(BigDecimal.ONE.add(taxa.multiply(BigDecimal.valueOf(quantidadeParcelas))));
    }

    private BigDecimal calcularValorParcela(BigDecimal valorTotal, int quantidadeParcelas) {
        return valorTotal.divide(BigDecimal.valueOf(quantidadeParcelas), 2, BigDecimal.ROUND_DOWN);
    }
}
