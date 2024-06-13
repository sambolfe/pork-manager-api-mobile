package br.csi.porkManagerApi.controllers;

import br.csi.porkManagerApi.dtos.SaudeDto;
import br.csi.porkManagerApi.dtos.SaudeResponseDto;
import br.csi.porkManagerApi.exceptions.InvalidRequestDataException;
import br.csi.porkManagerApi.models.Saude;
import br.csi.porkManagerApi.services.SaudeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/saude")
public class SaudeController {

    private final SaudeService saudeService;

    @Autowired
    public SaudeController(SaudeService saudeService) {
        this.saudeService = saudeService;
    }

    @PostMapping("/saveSaude")
    public ResponseEntity<?> salvarSaude(
            @ModelAttribute SaudeDto saudeDto,
            @RequestParam("foto") MultipartFile foto) {
        try {
            saudeDto.setFoto(foto); // Vincula a foto ao SaudeDto
            Saude saudeSalva = saudeService.salvarSaude(saudeDto);

            return ResponseEntity.ok().body(saudeSalva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar a saúde: " + e.getMessage());
        }
    }

    private String salvarImagem(MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            // Lógica para salvar a imagem no diretório desejado
            String diretorioDestino = "F:\\FotoSuino\\";
            String nomeArquivo = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
            Path caminhoCompleto = Paths.get(diretorioDestino + nomeArquivo);
            Files.write(caminhoCompleto, foto.getBytes());
            return caminhoCompleto.toString();
        }
        return null;
    }

    @PutMapping("/updateSaude/{id}")
    public ResponseEntity<Saude> atualizarSaude(
            @Valid @RequestPart("saudeDto") SaudeDto saudeDto,
            @RequestPart(value = "foto", required = false) MultipartFile foto,
            @PathVariable("id") Long id) throws Exception {

        if (isValidDto(saudeDto)) {
            Saude updatedSaude = saudeService.atualizarSaude(saudeDto, id);
            return ResponseEntity.ok(updatedSaude);
        } else {
            throw new InvalidRequestDataException("Os dados enviados são inválidos");
        }
    }

    @GetMapping("/getSaude/{id}")
    public ResponseEntity<Saude> getSaude(@PathVariable("id") Long id) {
        Saude saude = saudeService.getSaude(id);
        return ResponseEntity.ok(saude);
    }

    @GetMapping("/getAllSaudes")
    public ResponseEntity<List<SaudeResponseDto>> getAllSaudes() {
        List<SaudeResponseDto> saudeResponseDtos = saudeService.getAllSaudes();
        return ResponseEntity.ok(saudeResponseDtos);
    }

    @DeleteMapping("/deleteSaude/{id}")
    public ResponseEntity<?> deletarSaude(@PathVariable("id") Long id) throws Exception {
        try {
            return saudeService.deletarSaude(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Não é possível excluir saude, pois está sendo referenciado por outras entidades.");
        }
    }

    private boolean isValidDto(SaudeDto saudeDto) {
        return saudeDto != null &&
                saudeDto.getObservacoes() != null && !saudeDto.getObservacoes().isBlank() &&
                saudeDto.getTipoTratamento() != null && !saudeDto.getTipoTratamento().isBlank() &&
                saudeDto.getDataInicioTratamento() != null && !saudeDto.getDataInicioTratamento().isBlank() &&
                saudeDto.getPeso() != null &&
                (saudeDto.getDataEntradaCio() == null || !saudeDto.getDataEntradaCio().isBlank()) &&
                (saudeDto.getIdSuino() == null || saudeDto.getIdSuino() > 0);
    }
}
