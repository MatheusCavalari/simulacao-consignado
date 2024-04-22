package matheus.cavalari.simulacaoConsignado.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContratoInputDTO {
    private Long idSimulacao;
    private LocalDate dataContrato;
}
