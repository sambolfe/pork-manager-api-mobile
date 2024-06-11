package br.csi.porkManagerApi.services;

import br.csi.porkManagerApi.dtos.SaudeDto;
import br.csi.porkManagerApi.dtos.SaudeResponseDto;
import br.csi.porkManagerApi.models.Saude;
import br.csi.porkManagerApi.models.Suino;
import br.csi.porkManagerApi.repositories.SaudeRepository;
import br.csi.porkManagerApi.repositories.SuinoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public ResponseEntity<?> deletarSaude(Long id) throws Exception {
        try {
            Optional<Saude> saudeOptional = saudeRepository.findById(id);
            if (saudeOptional.isPresent()) {
                saudeRepository.deleteById(id);
                return ResponseEntity.ok().build(); // Retorna 200 OK se a exclusão for bem-sucedida
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o registro não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a solicitação."); // Retorna 500 Internal Server Error em caso de erro inesperado
        }
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

            saudesResponse.add(saudeDto);
        }

        return saudesResponse;
    }
    private String salvarFotoLocalmente(byte[] foto) throws IOException {
        // Define o caminho do diretório onde as fotos serão armazenadas
        String diretorioFotos = "F:/fotoSuinos";
        File dir = new File(diretorioFotos);
        if (!dir.exists()) {
            dir.mkdirs(); // Cria o diretório, se não existir
        }

        // Gera um nome único para a foto
        String nomeArquivo = "foto_" + System.currentTimeMillis() + ".jpg";
        File fotoArquivo = new File(dir, nomeArquivo);

        // Salva a foto no sistema de arquivos
        try (FileOutputStream fos = new FileOutputStream(fotoArquivo)) {
            fos.write(foto);
        }

        // Retorna o caminho completo da foto
        return fotoArquivo.getAbsolutePath();
    }
}
