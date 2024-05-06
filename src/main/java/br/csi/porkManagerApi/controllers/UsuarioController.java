package br.csi.porkManagerApi.controllers;

import br.csi.porkManagerApi.dtos.UsuarioDto;
import br.csi.porkManagerApi.exceptions.InvalidRequestDataException;
import br.csi.porkManagerApi.models.Usuario;
import br.csi.porkManagerApi.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/saveUsuario")
    public ResponseEntity<String> salvarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) throws Exception {
        if(isValidDto(usuarioDto)) {
            boolean res =  usuarioService.salvarUsuario(usuarioDto);
            if(res) {
                return new ResponseEntity<>("Usuário salvo com sucesso!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Erro ao salvar dados de usuário!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        throw new InvalidRequestDataException("Os dados enviados para salvar o usuário são inválidos!");
    }

    @PutMapping("/updateUsuario/{id}")
    public ResponseEntity<String> atualizarUsuario(
            @Valid @RequestBody UsuarioDto usuarioDto,
            @Valid @PathVariable Long id
    ) throws Exception {
        if(isValidDto(usuarioDto) && id != null) {
            boolean res =  usuarioService.atualizarUsuario(usuarioDto, id);
            if(res) {
                return new ResponseEntity<>("Usuário atualizado com sucesso!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Erro ao atualizar dados de usuário!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        throw new InvalidRequestDataException("Os dados enviados para atuliazar o usuário são inválidos!");
    }

    @PutMapping("/activeUsuario/{id}")
    public ResponseEntity<Boolean> desativarUsuario(
            @Valid @PathVariable Long id
    ) throws Exception {
        if(id != null) {
            boolean res =  usuarioService.desativarUsuario(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new InvalidRequestDataException("Identificador do usuário não encontrado!");
    }

    @GetMapping("/getUsuario/{id}")
    public ResponseEntity<Usuario> getUsuario(@Valid @PathVariable Long id) throws Exception  {
        if(id != null) {
            Usuario res =  usuarioService.getUsuario(id);
            if(res != null) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            throw new EntityNotFoundException("Usuário não encontrado!");
        }
        throw new InvalidRequestDataException("Identificador de usuário não encontrado!");
    }

    @GetMapping("/getAllUsuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUsuario/{id}")
    public ResponseEntity<Boolean> deletarUsuario(@Valid @PathVariable Long id) throws Exception {
        if (id != null) {
            boolean res = usuarioService.deletarUsuario(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new InvalidRequestDataException("Identificador do usuário não encontrado!");
    }

    private boolean isValidDto(UsuarioDto usuarioDto) {
        return usuarioDto.nome() != null && !usuarioDto.nome().isBlank()
                && usuarioDto.senha() != null && !usuarioDto.senha().isBlank()
                && usuarioDto.cpf() != null && !usuarioDto.cpf().isBlank()
                && usuarioDto.role() != null && !usuarioDto.role().isBlank()
                && usuarioDto.active() != null;
    }
}
