package br.csi.porkManagerApi.services;


import br.csi.porkManagerApi.dtos.UsuarioDto;
import br.csi.porkManagerApi.exceptions.InvalidCpfException;
import br.csi.porkManagerApi.exceptions.InvalidEnumException;
import br.csi.porkManagerApi.models.Usuario;
import br.csi.porkManagerApi.repositories.UsuarioRepository;
import br.csi.porkManagerApi.utils.enumUtils.EnumUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public boolean salvarUsuario(UsuarioDto usuarioDto) throws Exception {
        try {
            Optional<Usuario> isUsuario = usuarioRepository.findByCpf(usuarioDto.cpf());

            if(isUsuario.isPresent()) {
                throw new InvalidCpfException("CPF já existente no sistema!");
            }

            Usuario.Role role = EnumUtils.stringToEnum(Usuario.Role.class, usuarioDto.role());
            if(role.name().isBlank()) {
                throw new InvalidEnumException("Permissão de usuário Inválida");
            }

            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDto.nome().toLowerCase());
            usuario.setCpf(usuarioDto.cpf().replaceAll("\\D", ""));
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDto.senha()));
            usuario.setActive(true);
            usuario.setRole(role);
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public boolean atualizarUsuario(UsuarioDto usuarioDto, Long id) throws Exception {
        try {
            Optional<Usuario> isUsuario = usuarioRepository.findByCpf(usuarioDto.cpf());

            if(isUsuario.isPresent()) {
                throw new InvalidCpfException("CPF já existente no sistema!");
            }

            Usuario.Role role = EnumUtils.stringToEnum(Usuario.Role.class, usuarioDto.role());
            if(role.name().isBlank()) {
                throw new InvalidEnumException("Permissão de usuário Inválida");
            }

            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if(!bcrypt.matches(usuarioDto.senha(), usuario.getSenha())) {
                usuario.setSenha(bcrypt.encode(usuarioDto.senha()));
            }

            usuario.setNome(usuarioDto.nome().toLowerCase());
            usuario.setActive(usuarioDto.active());
            usuario.setRole(role);
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public boolean desativarUsuario(Long id) throws Exception {
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

            usuario.setActive(!usuario.getActive());

            usuarioRepository.save(usuario);
            return usuario.getActive();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

}