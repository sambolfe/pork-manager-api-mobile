package br.csi.porkManagerApi.repositories;

import br.csi.porkManagerApi.models.Suino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuinoRepository extends JpaRepository<Suino, Long> {

}