package matheus.cavalari.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transferencia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "contaOrigem")
    private String contaOrigem;
    @Column(name = "contaDestino")
    private String contaDestino;
    @Column(name = "valor")
    private double valor;
    @Column(name = "data")
    private Date data;
    @Column(name = "sucesso")
    private boolean sucesso;
}
