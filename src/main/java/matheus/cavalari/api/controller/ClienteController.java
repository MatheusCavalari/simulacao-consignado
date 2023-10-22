package matheus.cavalari.api.controller;

import matheus.cavalari.api.service.ServicoBancario;
import matheus.cavalari.api.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    @Autowired
    private ServicoBancario servicoBancario;

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente =  servicoBancario.criarCliente(cliente);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCliente.getId())
                .toUri();

        return ResponseEntity.created(location).body(novoCliente);
    }

    @GetMapping("/obterTodosClientes")
    public ResponseEntity<List<Cliente>> obterTodosClientes() {
        List<Cliente> clientes = servicoBancario.obterTodosClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/conta/{numeroConta}")
    public ResponseEntity<Cliente> obterClientePorNumeroConta(@PathVariable String numeroConta) {
        Cliente cliente = servicoBancario.obterClientePorNumeroConta(numeroConta);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
