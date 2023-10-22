package matheus.cavalari.api.Repository;

import matheus.cavalari.api.entity.Transferencia;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByContaOrigemOrderByDataDesc(String contaOrigem, Sort sort);
}