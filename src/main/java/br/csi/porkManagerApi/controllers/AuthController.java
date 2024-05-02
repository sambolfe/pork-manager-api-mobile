package br.csi.porkManagerApi.controllers;

import br.csi.porkManagerApi.dtos.AuthDto;
import br.csi.porkManagerApi.dtos.LoginDto;
import br.csi.porkManagerApi.exceptions.InvalidRequestDataException;
import br.csi.porkManagerApi.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<LoginDto> authentication(@Valid @RequestBody AuthDto authDto) {
        if(!authDto.cpf().isBlank() && !authDto.senha().isBlank()) {
            LoginDto loginDto = loginService.authenticateUsuario(authDto);
            return new ResponseEntity<>(loginDto, HttpStatus.OK);
        }
        throw new InvalidRequestDataException("Os dados enviados para autenticação são inválidos!");
    }
}
