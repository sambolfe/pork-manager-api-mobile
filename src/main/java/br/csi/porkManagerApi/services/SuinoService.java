package br.csi.porkManagerApi.services;

import br.csi.porkManagerApi.dtos.SuinoDto;
import br.csi.porkManagerApi.dtos.SuinoUpdateDto;
import br.csi.porkManagerApi.exceptions.InvalidEnumException;
import br.csi.porkManagerApi.models.Suino;
import br.csi.porkManagerApi.models.Raca;
import br.csi.porkManagerApi.models.Usuario;
import br.csi.porkManagerApi.repositories.RacaRepository;
import br.csi.porkManagerApi.repositories.SuinoRepository;
import br.csi.porkManagerApi.repositories.RacaRepository;
import br.csi.porkManagerApi.repositories.UsuarioRepository;
import br.csi.porkManagerApi.utils.enumUtils.EnumUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SuinoService {
    private final SuinoRepository suinoRepository;
    private final RacaRepository racaRepository;
    private final UsuarioRepository usuarioRepository;

    public SuinoService(SuinoRepository suinoRepository, RacaRepository racaRepository, UsuarioRepository usuarioRepository) {
        this.suinoRepository = suinoRepository;
        this.racaRepository = racaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Suino salvarSuino(SuinoDto suinoDto) throws Exception {
        try {
            Raca raca = racaRepository.findById(suinoDto.idRaca())
                    .orElseThrow(() -> new EntityNotFoundException("Raça não encontrada com o ID: " + suinoDto.idRaca()));
            Usuario usuario = usuarioRepository.findById(suinoDto.idUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + suinoDto.idUsuario()));

            Suino.SexoSuino sexo = EnumUtils.stringToEnum(Suino.SexoSuino.class, suinoDto.sexo());
            Suino.TipoSuino tipoSuino = EnumUtils.stringToEnum(Suino.TipoSuino.class, suinoDto.tipoSuino());

            Date currentDate = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(suinoDto.dataNasc());

            Suino suino = new Suino();
            suino.setRaca(raca);
            suino.setIdentificacaoOrelha(suinoDto.identificacaoOrelha());
            suino.setDataNasc(date);
            suino.setSexo(sexo);
            suino.setObservacoes(suinoDto.observacoes());
            suino.setTipoSuino(tipoSuino);
            suino.setCriadoEm(currentDate);
            suino.setAtualizadoEm(currentDate);
            suino.setUsuario(usuario);

            return suinoRepository.save(suino);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Transactional
    public Suino atualizarSuino(Long id, SuinoUpdateDto suinoDto) throws Exception {
        try {
            Suino suino = suinoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Suino não encontrado com o ID: " + id));

            Date currentDate = new Date(System.currentTimeMillis());

            suino.setIdentificacaoOrelha(suinoDto.identificacaoOrelha());
            suino.setObservacoes(suinoDto.observacoes());
            suino.setAtualizadoEm(currentDate);

            return suinoRepository.save(suino);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Transactional
    public boolean deletarSuino(Long id) {
        try {
            suinoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Suino getSuino(Long id) {
        return suinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Suino não encontrado com o ID: " + id));
    }

    public List<Suino> getAllSuinos() {
        return suinoRepository.findAll();
    }
}
