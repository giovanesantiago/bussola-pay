package br.com.bussolapay.service;

import br.com.bussolapay.config.exceptions.DividasException;
import br.com.bussolapay.infra.FactoryDividas;
import br.com.bussolapay.model.DividaCreate;
import br.com.bussolapay.repository.DividaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DividaService {

    private final ClienteService clienteService;
    private final DividaRepository dividaRepository;


    public void save(DividaCreate dividaCreate) {
        dividaRepository.saveAll(
                Optional.ofNullable(
                        FactoryDividas.generateDividas(dividaCreate, clienteService.getClienteLogado())
                ).orElseThrow(() -> new DividasException("Falha ao fabricar dividas"))
        );
    }



}
