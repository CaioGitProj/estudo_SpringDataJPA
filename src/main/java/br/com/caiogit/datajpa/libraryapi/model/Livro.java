package br.com.caiogit.datajpa.libraryapi.model;

import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@AllArgsConstructor
@Data
@ToString(exclude = "autor")
public class Livro
{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 70)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "genero", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private GeneroLivro generoLivro;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @JoinColumn(name = "id_autor", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY) //(cascade = CascadeType.ALL)
    private Autor autor;



    @Deprecated
    public Livro() {
    }
}
