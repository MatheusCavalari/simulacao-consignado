package matheus.cavalari.api.Repository;

import matheus.cavalari.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByNumeroConta(String numeroConta);
}