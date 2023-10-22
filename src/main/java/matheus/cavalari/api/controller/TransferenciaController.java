package matheus.cavalari.api.controller;

import matheus.cavalari.api.service.ServicoBancario;
import matheus.cavalari.api.entity.Transferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transferencia")
public class TransferenciaController {

    @Autowired
    private ServicoBancario servicoBancario;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Transferencia> realizarTransferencia(@RequestBody Transferencia transferencia){
        Transferencia transferenciaRealizada = servicoBancario.realizarTransferencia(transferencia);
        if (transferenciaRealizada.isSucesso()) {
            return ResponseEntity.ok(transferenciaRealizada);
        } else {
            return ResponseEntity.badRequest().body(transferenciaRealizada);
        }
    }

    @GetMapping("/{numeroConta}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transferencia> obterTransferenciasPorConta(@PathVariable String numeroConta){
        return servicoBancario.obterTransferenciasPorConta(numeroConta);
    }
}
