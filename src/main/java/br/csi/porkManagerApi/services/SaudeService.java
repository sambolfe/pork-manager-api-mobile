package br.csi.porkManagerApi.services;

import br.csi.porkManagerApi.dtos.SaudeDto;
import br.csi.porkManagerApi.models.Saude;
import br.csi.porkManagerApi.models.Suino;
import br.csi.porkManagerApi.repositories.SaudeRepository;
import br.csi.porkManagerApi.repositories.SuinoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SaudeService {
    private final SaudeRepository saudeRepository;
    private final SuinoRepository suinoRepository;

    public SaudeService(SaudeRepository saudeRepository, SuinoRepository suinoRepository) {
        this.saudeRepository = saudeRepository;
        this.suinoRepository = suinoRepository;
    }

    @Transactional
    public Saude salvarSaude(SaudeDto saudeDto) throws Exception {
        try {
            Suino suino = suinoRepository.findById(saudeDto.idSuino())
                    .orElseThrow(() -> new EntityNotFoundException("O ID do suíno requisitado não existe!"));

            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Saude saude = new Saude();
            saude.setPeso(saudeDto.peso());
            saude.setTipoTratamento(saudeDto.tipoTratamento());
            saude.setDataInicioTratamento(sdf.parse(saudeDto.dataInicioTratamento()));
            saude.setObservacoes(saudeDto.observacoes());
            saude.setCriadoEm(currentDate);
            saude.setAtualizadoEm(currentDate);
            saude.setSuino(suino);
            saude.setFoto(saudeDto.foto()); // Definindo a foto

            if (saudeDto.dataEntradaCio() != null && !saudeDto.dataEntradaCio().isBlank()) {
                Date date = sdf.parse(saudeDto.dataEntradaCio());
                saude.setDataEntradaCio(date);
            }

            saudeRepository.save(saude);
            return saude;
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }


    @Transactional
    public Saude atualizarSaude(SaudeDto saudeDto, Long id) throws Exception {
        try {
            Suino suino = suinoRepository.findById(saudeDto.idSuino())
                    .orElseThrow(() -> new EntityNotFoundException("O ID do suíno requisitado não existe!"));

            Saude saude = saudeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("O ID da saúde requisitada não existe!"));

            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            saude.setPeso(saudeDto.peso());
            saude.setTipoTratamento(saudeDto.tipoTratamento());
            saude.setDataInicioTratamento(sdf.parse(saudeDto.dataInicioTratamento()));
            saude.setObservacoes(saudeDto.observacoes());
            saude.setAtualizadoEm(currentDate);
            saude.setSuino(suino);
            saude.setFoto(saudeDto.foto()); // Definindo a foto

            // Verifica se a data de entrada no cio foi fornecida
            if (saudeDto.dataEntradaCio() != null && !saudeDto.dataEntradaCio().isBlank()) {
                Date date = sdf.parse(saudeDto.dataEntradaCio());
                saude.setDataEntradaCio(date);
            }

            saudeRepository.save(saude);
            return saude;
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public Saude getSaude(Long id) {
        return saudeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Suino não encontrado com o ID: " + id));
    }
    public List<Saude> getAllSaudes() {
        return saudeRepository.findAll();
    }

}
