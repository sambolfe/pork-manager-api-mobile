package br.csi.porkManagerApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotBlank
    @NotNull
    private String nome;
    @NotNull
    private Long id;
    @NotBlank
    @NotNull
    private String token;
    @NotBlank
    @NotNull
    private String role;
    @NotNull
    private Boolean active;
}
