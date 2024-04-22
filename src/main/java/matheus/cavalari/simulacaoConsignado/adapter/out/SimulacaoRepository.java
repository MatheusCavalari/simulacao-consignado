package matheus.cavalari.simulacaoConsignado.adapter.out;

import matheus.cavalari.simulacaoConsignado.domain.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimulacaoRepository extends JpaRepository<Simulacao, Long> {
    Optional<Simulacao> findById(Long id);
}


