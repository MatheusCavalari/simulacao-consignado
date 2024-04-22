package matheus.cavalari.simulacaoConsignado.adapter.in;

import matheus.cavalari.simulacaoConsignado.application.ISimulacaoService;
import matheus.cavalari.simulacaoConsignado.application.exception.ClienteNaoEncontradoException;
import matheus.cavalari.simulacaoConsignado.application.exception.PrazoMaximoExcedidoException;
import matheus.cavalari.simulacaoConsignado.domain.Simulacao;
import matheus.cavalari.simulacaoConsignado.dto.SimulacaoInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/simulacao")
public class SimulacaoController {

    private final ISimulacaoService simulacaoService;

    @Autowired
    public SimulacaoController(ISimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @GetMapping
    public ResponseEntity<?> listarContratos() {
        try {
            List<Simulacao> simulacoes = simulacaoService.listarSimulacoes();
            return ResponseEntity.ok(simulacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao listar as simulações");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSimulacao(@PathVariable Long id) {
        try {
            Simulacao simulacao = simulacaoService.getSimulacao(id);
            if (simulacao != null) {
                return ResponseEntity.ok(simulacao);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Simulação não encontrada para o ID informado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao buscar a simulação");
        }
    }

    @PostMapping
    public ResponseEntity<?> simularConsignado(@RequestBody SimulacaoInputDTO simulacaoInputDTO) {
        try {
            Simulacao simulacao = simulacaoService.simularConsignado(simulacaoInputDTO);
            return ResponseEntity.ok(simulacao);
        } catch (ClienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PrazoMaximoExcedidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao processar a simulação");
        }
    }

}