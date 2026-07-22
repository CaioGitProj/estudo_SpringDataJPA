package br.com.caiogit.datajpa.libraryapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Data
@AllArgsConstructor
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class)
public class Autor
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", nullable = false, length = 50)
    private String nacionalidade;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.PERSIST)
    private List<Livro> livros;

    @Deprecated
    public Autor() {
    }
}
