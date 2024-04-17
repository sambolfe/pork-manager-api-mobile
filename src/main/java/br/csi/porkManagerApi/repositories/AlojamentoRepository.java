package br.csi.porkManagerApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.csi.porkManagerApi.models.Alojamento;

public interface AlojamentoRepository extends JpaRepository<Alojamento, Long> {
}
