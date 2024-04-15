package br.csi.porkManagerApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthDto(
        @NotBlank
        @NotNull
        String cpf,
        @NotBlank
        @NotNull
        String senha
) {}
