package br.csi.porkManagerApi.dtos;

import br.csi.porkManagerApi.models.Alojamento.StatusAlojamento;
import br.csi.porkManagerApi.models.Suino.TipoSuino;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AlojamentoDto(
        @NotBlank
        @NotNull
        String nome,
        @NotNull
        TipoSuino tipo,
        @NotNull
        @Positive
        Integer capacidade,
        @NotNull
        StatusAlojamento status
) {
}