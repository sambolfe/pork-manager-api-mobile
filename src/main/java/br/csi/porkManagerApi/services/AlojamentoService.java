package br.csi.porkManagerApi.services;

import br.csi.porkManagerApi.dtos.AlojamentoDto;
import br.csi.porkManagerApi.models.Alojamento;
import br.csi.porkManagerApi.repositories.AlojamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AlojamentoService {

    private final AlojamentoRepository alojamentoRepository;

    public AlojamentoService(AlojamentoRepository alojamentoRepository) {
        this.alojamentoRepository = alojamentoRepository;
    }

    @Transactional
    public Alojamento salvarAlojamento(AlojamentoDto alojamentoDto) throws Exception {
        try {
            Date currentDate = new Date(System.currentTimeMillis());

            Alojamento alojamento = new Alojamento();
            alojamento.setNome(alojamentoDto.nome());
            alojamento.setTipo(alojamentoDto.tipo());
            alojamento.setCapacidade(alojamentoDto.capacidade());
            alojamento.setStatus(alojamentoDto.status());
            alojamento.setCriadoEm(currentDate);
            alojamento.setAtualizadoEm(currentDate);

            return alojamentoRepository.save(alojamento);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Transactional
    public Alojamento atualizarAlojamento(Long id, AlojamentoDto alojamentoDto) throws Exception {
        try {
            Alojamento alojamento = alojamentoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("O ID do alojamento requisitado não existe!"));

            Date currentDate = new Date(System.currentTimeMillis());

            alojamento.setNome(alojamentoDto.nome());
            alojamento.setTipo(alojamentoDto.tipo());
            alojamento.setCapacidade(alojamentoDto.capacidade());
            alojamento.setStatus(alojamentoDto.status());
            alojamento.setAtualizadoEm(currentDate);

            return alojamentoRepository.save(alojamento);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Transactional
    public boolean deletarAlojamento(Long id) throws Exception {
        try {
            Optional<Alojamento> alojamentoOptional = alojamentoRepository.findById(id);
            if (alojamentoOptional.isPresent()) {
                alojamentoRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public Alojamento buscarAlojamento(Long id) {
        return alojamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alojamento não encontrado com o ID: " + id));
    }

    public List<Alojamento> listarAlojamentos() {
        return alojamentoRepository.findAll();
    }
}
