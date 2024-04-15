package br.csi.porkManagerApi.services;

import br.csi.porkManagerApi.dtos.AuthDto;
import br.csi.porkManagerApi.dtos.LoginDto;
import br.csi.porkManagerApi.models.Usuario;
import br.csi.porkManagerApi.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginService(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginDto authenticateUsuario(AuthDto authDto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(authDto.cpf(), authDto.senha());
        Authentication a =  authenticationManager.authenticate(auth);

        Usuario user = (Usuario) a.getPrincipal();
        String jwt = jwtService.createJwt(user);

        LoginDto loginDto = new LoginDto();
        loginDto.setNome(user.getNome());
        loginDto.setId(user.getId());
        loginDto.setToken(jwt);
        loginDto.setActive(user.getActive());
        loginDto.setRole(user.getRole().name());

        return loginDto;
    }
}
