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
            Date date = sdf.parse(saudeDto.dataEntradaCio());

            Saude saude = new Saude();
            saude.setPeso(saudeDto.peso());
            saude.setDataEntradaCio(date);
            saude.setTipoTratamento(saudeDto.tipoTratamento());
            saude.setDataInicioTratamento(sdf.parse(saudeDto.dataInicioTratamento()));
            saude.setObservacoes(saudeDto.observacoes());
            saude.setCriadoEm(currentDate);
            saude.setAtualizadoEm(currentDate);
            saude.setSuino(suino);

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
            Date date = sdf.parse(saudeDto.dataEntradaCio());

            saude.setPeso(saudeDto.peso());
            saude.setDataEntradaCio(date);
            saude.setTipoTratamento(saudeDto.tipoTratamento());
            saude.setDataInicioTratamento(sdf.parse(saudeDto.dataInicioTratamento()));
            saude.setObservacoes(saudeDto.observacoes());
            saude.setAtualizadoEm(currentDate);
            saude.setSuino(suino);

            saudeRepository.save(saude);
            return saude;
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }
}
