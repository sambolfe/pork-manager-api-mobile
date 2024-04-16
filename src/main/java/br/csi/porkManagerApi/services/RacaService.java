package br.csi.porkManagerApi.services;



import br.csi.porkManagerApi.dtos.RacaDto;
import br.csi.porkManagerApi.models.Raca;
import br.csi.porkManagerApi.repositories.RacaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class RacaService {
    private final RacaRepository racaRepository;

    public RacaService(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    @Transactional
    public Raca salvarRaca(RacaDto racaDto) throws Exception {
        try {
            Date currentDate = new Date(System.currentTimeMillis());

            Raca raca = new Raca();
            raca.setNome(racaDto.nome());
            raca.setDescricao(racaDto.descricao());
            raca.setCaracteristicas(racaDto.caracteristicas());
            raca.setCriadoEm(currentDate);
            raca.setAtualizado_em(currentDate);

            return racaRepository.save(raca);

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

    }

    @Transactional
    public Raca atualizarRaca(Long id, RacaDto racaDto) throws Exception {
        try {
            Raca raca = racaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Raça não encontrada com o ID: " + id));

            Date currentDate = new Date(System.currentTimeMillis());

            raca.setNome(racaDto.nome());
            raca.setDescricao(racaDto.descricao());
            raca.setCaracteristicas(racaDto.caracteristicas());
            raca.setAtualizado_em(currentDate);

            return racaRepository.save(raca);
        }catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public boolean verificarExistencia(Long id) {
        return racaRepository.existsById(id);
    }


    @Transactional
    public boolean deletarRaca(Long id) throws  Exception{
        try {
            Optional<Raca> racaOptional = racaRepository.findById(id);
            if (racaOptional.isPresent()) {
                racaRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public Raca buscarRaca(Long id) {
        return racaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raça não encontrada com o ID: " + id));
    }

    public List<Raca> listarRacas() {
        return racaRepository.findAll();
    }
}