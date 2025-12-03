package br.com.bussolapay.repository;

import br.com.bussolapay.model.Divida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividaRepository extends JpaRepository<Divida, Long> {
}
