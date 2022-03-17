package br.ce.wcaquino.servicos;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que contém um único teste que executa na mesma incidência quanto o número de parâmetros definidos. <u>Teste parametrizado</u>
 *
 * @author Wanderley Drumond
 * @since 12/03/2022
 * @version 3.1
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CálculoValorLocaçãoTest {
    Usuario usuário = new Usuario("Wanderley");

    List<Filme> filmes;

    private LocacaoService locaçãoService;
    /**
     * Incrementador para o teste <code>deveCalcularValorLocacaoConsiderandoDescontos</code>
     */
    private Integer índice = 0;
    /**
     * Valor da locação utilizado no teste <code>deveCalcularValorLocacaoConsiderandoDescontos</code>
     */
    private Double valorTotalLocação = FilmeBuilder.umFilme().agora().getPrecoLocacao() + FilmeBuilder.umFilme().agora().getPrecoLocacao();

    /**
     * Prepara o ambiente antes da execução de cada teste parametrizado.
     */
    @BeforeEach
    void setup() {
//        Given
        locaçãoService = new LocacaoService();
        filmes = new ArrayList<>(getFilmes().get(índice));
    }

    /**
     * Método que servirá de fonte para carregar os dados do teste parametrizado.
     *
     * @return a lista de listas de filmes
     */
    public List<List<Filme>> getFilmes() {
        List<Filme> filmes1 = new ArrayList<>(Arrays.asList(FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora()));
        List<Filme> filmes2 = new ArrayList<>(Arrays.asList(FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora()));
        List<Filme> filmes3 = new ArrayList<>(Arrays.asList(FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora()));
        List<Filme> filmes4 = new ArrayList<>(Arrays.asList(FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora()));
        return new ArrayList<>(Arrays.asList(filmes1, filmes2, filmes3, filmes4));
    }

    /**
     * Teste que executa várias vezes seguindo os parâmetros determinados.
     *
     * @throws FilmeSemEstoqueException quando algum item da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException        quando <code>Usuario</code> for igual a nulo.
     */
    @ParameterizedTest(name = "{index}º teste")
    @MethodSource("getFilmes")
    @DisplayName("Calcular valor de cada locação de acordo com o seus devidos descontos")
    void deveCalcularValorLocaçãoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
//        When
        Locacao locacao = locaçãoService.alugarFilme(usuário, filmes);
        switch (índice) {
            case 0 -> valorTotalLocação += FilmeBuilder.umFilme().agora().getPrecoLocacao() * 75 / 100;
            case 1 -> valorTotalLocação += FilmeBuilder.umFilme().agora().getPrecoLocacao() * 50 / 100;
            case 2 -> valorTotalLocação += FilmeBuilder.umFilme().agora().getPrecoLocacao() * 25 / 100;
        }

//        Then
        MatcherAssert.assertThat(locacao.getValor(), CoreMatchers.is(valorTotalLocação));
        índice++;
    }
}