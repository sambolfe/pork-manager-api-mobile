package br.csi.porkManagerApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SuinoUpdateDto(
        @NotNull
        Long id,
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
