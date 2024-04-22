package matheus.cavalari.simulacaoConsignado.domain;

import lombok.Data;
import matheus.cavalari.simulacaoConsignado.enums.Convenio;
import matheus.cavalari.simulacaoConsignado.enums.SegmentoCliente;

@Data
public class Cliente {
    private Long id;

    private String cpf;

    private String nome;

    private boolean correntista;

    private SegmentoCliente segmento;

    private Convenio convenio;
}