package br.csi.porkManagerApi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "raca")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Raca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    @NotBlank
    @NotNull
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotBlank
    @NotNull
    @Column(name = "caracteristicas", columnDefinition = "TEXT")
    private String caracteristicas;

    @OneToMany(mappedBy = "raca")
    @JsonBackReference
    private Set<Suino> suinos;

    @CreatedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "criado_em", nullable = false)
    @NotNull
    private Date criadoEm;

    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "atualizado_em", nullable = false)
    @NotNull
    private Date atualizado_em;
}
