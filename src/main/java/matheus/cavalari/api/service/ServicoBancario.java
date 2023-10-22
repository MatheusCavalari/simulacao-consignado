package matheus.cavalari.api.service;

import matheus.cavalari.api.Repository.ClienteRepository;
import matheus.cavalari.api.Repository.TransferenciaRepository;
import matheus.cavalari.api.entity.Cliente;
import matheus.cavalari.api.entity.Transferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServicoBancario {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> obterTodosClientes() {
        return clienteRepository.findAll();
    }

    public Cliente obterClientePorNumeroConta(String numeroConta) {
        return clienteRepository.findByNumeroConta(numeroConta);
    }

    public Transferencia realizarTransferencia(Transferencia transferencia) {
        Cliente clienteOrigem = clienteRepository.findByNumeroConta(transferencia.getContaOrigem());
        Cliente clienteDestino = clienteRepository.findByNumeroConta(transferencia.getContaDestino());
        transferencia.setData(new Date());

        if (
            clienteOrigem == null || clienteDestino == null ||
            clienteOrigem.getSaldoConta() < transferencia.getValor() ||
            transferencia.getValor() > 1000) {
            transferencia.setSucesso(false);
        } else {
            clienteOrigem.setSaldoConta(clienteOrigem.getSaldoConta() - transferencia.getValor());
            clienteDestino.setSaldoConta(clienteDestino.getSaldoConta() + transferencia.getValor());
            transferencia.setSucesso(true);
            clienteRepository.save(clienteOrigem);
            clienteRepository.save(clienteDestino);
        }

        return transferenciaRepository.save(transferencia);
    }

    public List<Transferencia> obterTransferenciasPorConta(String numeroConta) {
        Sort sort = Sort.by(Sort.Direction.DESC, "data");
        return transferenciaRepository.findByContaOrigemOrderByDataDesc(numeroConta, sort);
    }
}
