package br.com.bussolapay.service;

import br.com.bussolapay.config.exceptions.DividasException;
import br.com.bussolapay.infra.FactoryDividas;
import br.com.bussolapay.infra.FactoryRelatorios;
import br.com.bussolapay.model.*;
import br.com.bussolapay.model.enums.StatusDivida;
import br.com.bussolapay.repository.DividaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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


    public List<ResumoDiario> getResumosDiarios5Dias() {
        List<DividaDTO> dividas = dividaRepository.findDividaDTOByBetweenAndStatus(
                LocalDate.now(), LocalDate.now().plusDays(5L), StatusDivida.PENDENTE.name()
        );


        return FactoryRelatorios.generateResumoPorDia(dividas , new RangeDate(LocalDate.now(), LocalDate.now().plusDays(5L)));
    }
}
