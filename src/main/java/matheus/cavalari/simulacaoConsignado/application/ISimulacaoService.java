package matheus.cavalari.simulacaoConsignado.application;

import matheus.cavalari.simulacaoConsignado.domain.Simulacao;
import matheus.cavalari.simulacaoConsignado.dto.SimulacaoInputDTO;

import java.util.List;

public interface ISimulacaoService {
    List<Simulacao> listarSimulacoes();
    Simulacao getSimulacao(Long id);
    Simulacao simularConsignado(SimulacaoInputDTO simulacao);
}
