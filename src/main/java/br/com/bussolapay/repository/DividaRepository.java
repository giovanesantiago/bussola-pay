package br.com.bussolapay.repository;

import br.com.bussolapay.model.Divida;
import br.com.bussolapay.model.DividaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DividaRepository extends JpaRepository<Divida, Long> {

    // TODO: query puxando todas as subdividas ao invés de so do usuário, corrigir isso para mais performance
    @Query(value = """
        WITH dividas_simples AS (
            SELECT id, descricao, data_vencimento, valor, status, 'simples' AS tipo
            FROM divida
            WHERE data_vencimento BETWEEN :dataInicio AND :dataFim
              AND tipo_divida IN ('SIMPLES_A_VISTA', 'SIMPLES_PARCELADA')
                    AND status = :statusDivida
                        AND cliente_id = :clienteId
        ),
        sub AS (
            SELECT *
            FROM sub_divida
            WHERE data_vencimento BETWEEN :dataInicio AND :dataFim
                    AND status = :statusDivida
        ),
        dividas_compostas AS (
            SELECT d.id, d.descricao, sub.data_vencimento, SUM(sub.valor) AS valor, d.status, 'composta' AS tipo
            FROM divida d
            JOIN sub_divida sub ON d.id = sub.divida_id
            WHERE sub.data_vencimento BETWEEN :dataInicio AND :dataFim
                        AND cliente_id = :clienteId
            GROUP BY d.id, d.descricao, sub.data_vencimento, sub.status
        )
        SELECT *
        FROM dividas_simples
        UNION ALL
        SELECT * FROM dividas_compostas
        ORDER BY data_vencimento
        """, nativeQuery = true)
    List<DividaDTO> findDividaDTOByBetweenAndStatus(@Param("dataInicio") LocalDate dataInicio,
                                                    @Param("dataFim") LocalDate dataFim,
                                                    @Param("statusDivida") String statusDivida,
                                                    @Param("clienteId")Long clienteId);
}
