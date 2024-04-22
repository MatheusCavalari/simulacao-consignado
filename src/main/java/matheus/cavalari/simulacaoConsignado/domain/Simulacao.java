package matheus.cavalari.simulacaoConsignado.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.cavalari.simulacaoConsignado.enums.Convenio;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Simulacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "data_simulacao")
    private LocalDate dataSimulacao;

    @Column(name = "cpf_cliente")
    private String cpfCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "convenio_cliente")
    private Convenio convenioCliente;

    @Column(name = "valor_solicitado")
    private BigDecimal valorSolicitado;

    @Column(name = "taxa_aplicada")
    private BigDecimal taxaAplicada;

    @Column(name = "quantidade_parcelas")
    private int quantidadeParcelas;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;


}
