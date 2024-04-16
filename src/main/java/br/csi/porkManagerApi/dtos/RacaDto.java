package br.csi.porkManagerApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RacaDto(
        @NotBlank
        @NotNull
        String nome,
        @NotBlank
        @NotNull
        String descricao,
        @NotBlank
        @NotNull
        String caracteristicas
) {}