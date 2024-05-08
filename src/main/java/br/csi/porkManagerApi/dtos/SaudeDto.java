package br.csi.porkManagerApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

public record SaudeDto(
        @NotBlank
        @NotNull
        String tipoTratamento,
        @NotBlank
        @NotNull
        String dataInicioTratamento,
        @NotBlank
        @NotNull
        String observacoes,
        String dataEntradaCio,
        @NotNull
        @Positive
        BigDecimal peso,
        @NotNull
        Long idSuino,
        byte[] foto
) {}
