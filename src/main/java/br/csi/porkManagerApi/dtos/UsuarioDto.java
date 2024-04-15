package br.csi.porkManagerApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDto(
        @NotBlank
        @NotNull
        String nome,
        @NotBlank
        @NotNull
        String cpf,
        @NotBlank
        @NotNull
        String senha,
        @NotNull
        String role,
        @NotNull
        Boolean active
) {}
