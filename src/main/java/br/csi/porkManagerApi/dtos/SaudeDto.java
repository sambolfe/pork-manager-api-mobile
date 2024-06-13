package br.csi.porkManagerApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class SaudeDto {

    @NotBlank
    @NotNull
    private String tipoTratamento;

    @NotBlank
    @NotNull
    private String dataInicioTratamento;

    @NotBlank
    @NotNull
    private String observacoes;

    private String dataEntradaCio;

    @NotNull
    @Positive
    private BigDecimal peso;

    @NotNull
    private Long idSuino;

    private MultipartFile foto;

    // Getters e Setters

    public String getTipoTratamento() {
        return tipoTratamento;
    }

    public void setTipoTratamento(String tipoTratamento) {
        this.tipoTratamento = tipoTratamento;
    }

    public String getDataInicioTratamento() {
        return dataInicioTratamento;
    }

    public void setDataInicioTratamento(String dataInicioTratamento) {
        this.dataInicioTratamento = dataInicioTratamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getDataEntradaCio() {
        return dataEntradaCio;
    }

    public void setDataEntradaCio(String dataEntradaCio) {
        this.dataEntradaCio = dataEntradaCio;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Long getIdSuino() {
        return idSuino;
    }

    public void setIdSuino(Long idSuino) {
        this.idSuino = idSuino;
    }

    public MultipartFile getFoto() {
        return foto;
    }

    public void setFoto(MultipartFile foto) {
        this.foto = foto;
    }
}
