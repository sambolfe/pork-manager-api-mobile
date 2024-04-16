package br.csi.porkManagerApi.dtos;

import br.csi.porkManagerApi.models.Suino;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public record SuinoDto(
        @NotNull
        Long idRaca,
        @NotBlank
        @NotNull
        String identificacaoOrelha,
        @NotNull
        String dataNasc,
        @NotNull
        String sexo,
        @NotBlank
        @NotNull
        String observacoes,
        @NotNull
        String tipoSuino,
        @NotNull
        Long idUsuario
){}
