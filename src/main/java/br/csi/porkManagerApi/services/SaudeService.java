package br.csi.porkManagerApi.services;

import br.csi.porkManagerApi.dtos.SaudeDto;
import br.csi.porkManagerApi.dtos.SaudeResponseDto;
import br.csi.porkManagerApi.models.Saude;
import br.csi.porkManagerApi.models.Suino;
import br.csi.porkManagerApi.repositories.SaudeRepository;
import br.csi.porkManagerApi.repositories.SuinoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaudeService {

    private final SaudeRepository saudeRepository;
    private final SuinoRepository suinoRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    public SaudeService(SaudeRepository saudeRepository, SuinoRepository suinoRepository) {
        this.saudeRepository = saudeRepository;
        this.suinoRepository = suinoRepository;
    }

    @Transactional
    public Saude salvarSaude(SaudeDto saudeDto) throws Exception {
        try {
            Suino suino = suinoRepository.findById(saudeDto.getIdSuino())
                    .orElseThrow(() -> new EntityNotFoundException("O ID do suíno requisitado não existe!"));

            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Saude saude = new Saude();
            saude.setPeso(saudeDto.getPeso());
            saude.setTipoTratamento(saudeDto.getTipoTratamento());
            saude.setDataInicioTratamento(sdf.parse(saudeDto.getDataInicioTratamento()));
            saude.setObservacoes(saudeDto.getObservacoes());
            saude.setCriadoEm(currentDate);
            saude.setAtualizadoEm(currentDate);
            saude.setSuino(suino);

            if (saudeDto.getDataEntradaCio() != null && !saudeDto.getDataEntradaCio().isBlank()) {
                saude.setDataEntradaCio(sdf.parse(saudeDto.getDataEntradaCio()));
            }

            // Salvar a foto localmente, se presente
            if (saudeDto.getFoto() != null && !saudeDto.getFoto().isEmpty()) {
                String caminhoFoto = salvarFotoLocalmente(saudeDto.getFoto());
                saude.setFoto(caminhoFoto);
            }

            saudeRepository.save(saude);
            return saude;
        } catch (Exception e) {
            throw new Exception("Falha ao salvar a saúde: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Saude atualizarSaude(SaudeDto saudeDto, Long id) throws Exception {
        try {
            Suino suino = suinoRepository.findById(saudeDto.getIdSuino())
                    .orElseThrow(() -> new EntityNotFoundException("O ID do suíno requisitado não existe!"));

            Saude saude = saudeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("O ID da saúde requisitada não existe!"));

            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            saude.setPeso(saudeDto.getPeso());
            saude.setTipoTratamento(saudeDto.getTipoTratamento());
            saude.setDataInicioTratamento(sdf.parse(saudeDto.getDataInicioTratamento()));
            saude.setObservacoes(saudeDto.getObservacoes());
            saude.setAtualizadoEm(currentDate);
            saude.setSuino(suino);

            if (saudeDto.getDataEntradaCio() != null && !saudeDto.getDataEntradaCio().isBlank()) {
                saude.setDataEntradaCio(sdf.parse(saudeDto.getDataEntradaCio()));
            }

            if (saudeDto.getFoto() != null && !saudeDto.getFoto().isEmpty()) {
                String caminhoFoto = salvarFotoLocalmente(saudeDto.getFoto());
                saude.setFoto(caminhoFoto);
            }

            saudeRepository.save(saude);
            return saude;
        } catch (Exception e) {
            throw new Exception("Falha ao atualizar a saúde: " + e.getMessage(), e);
        }
    }

    public Saude getSaude(Long id) {
        return saudeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Saúde não encontrada com o ID: " + id));
    }

    @Transactional
    public List<SaudeResponseDto> getAllSaudes() {
        List<Saude> saudes = saudeRepository.findAll();
        List<SaudeResponseDto> saudesResponse = new ArrayList<>();

        for (Saude saude : saudes) {
            SaudeResponseDto saudeDto = new SaudeResponseDto();
            saudeDto.setId(saude.getId());
            saudeDto.setPeso(saude.getPeso());
            saudeDto.setDataEntradaCio(saude.getDataEntradaCio());
            saudeDto.setTipoTratamento(saude.getTipoTratamento());
            saudeDto.setDataInicioTratamento(saude.getDataInicioTratamento());
            saudeDto.setObservacoes(saude.getObservacoes());
            saudeDto.setCriadoEm(saude.getCriadoEm());
            saudeDto.setAtualizadoEm(saude.getAtualizadoEm());
            saudeDto.setIdentificadorOrelha(saude.getSuino().getIdentificacaoOrelha());

            saudeDto.setFoto(saude.getFoto()); // Configurar o campo foto

            saudesResponse.add(saudeDto);
        }

        return saudesResponse;
    }
    @Transactional
    public ResponseEntity<?> deletarSaude(Long id) throws Exception {
        try {
            saudeRepository.deleteById(id);
            return ResponseEntity.ok().build(); // Retorna 200 OK se a exclusão for bem-sucedida
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a solicitação."); // Retorna 500 Internal Server Error em caso de erro inesperado
        }
    }

    private String salvarFotoLocalmente(MultipartFile foto) throws IOException {
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + foto.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;

        File dest = new File(filePath);
        dest.getParentFile().mkdirs();
        foto.transferTo(dest);

        return filePath;
    }

}
