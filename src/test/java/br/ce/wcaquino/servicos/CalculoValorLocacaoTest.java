package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que contém um único teste que executa na mesma incidência quanto o número de parâmetros definidos.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalculoValorLocacaoTest {
    private final Filme filme1 = new Filme("Mother", 1, 10.);
    private final Filme filme2 = new Filme("Matrix", 2, 10.);
    private final Filme filme3 = new Filme("Interestelar", 4, 10.);
    private final Filme filme4 = new Filme("The Conjuring", 6, 10.);
    private final Filme filme5 = new Filme("The Day the Earth Stood Still", 5, 10.);
    private final Filme filme6 = new Filme("Midsommar", 1, 10.);

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
    private Double valorTotalLocação = filme1.getPrecoLocacao() + filme2.getPrecoLocacao();

    /**
     * Prepara o ambiente antes da execução de cada teste parametrizado.
     */
    @BeforeEach
    public void setup() {
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
        List<Filme> filmes1 = new ArrayList<>(Arrays.asList(filme1, filme2, filme3));
        List<Filme> filmes2 = new ArrayList<>(Arrays.asList(filme1, filme2, filme3, filme4));
        List<Filme> filmes3 = new ArrayList<>(Arrays.asList(filme1, filme2, filme3, filme4, filme5));
        List<Filme> filmes4 = new ArrayList<>(Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6));
        return new ArrayList<>(Arrays.asList(filmes1, filmes2, filmes3, filmes4));
    }

    /**
     * Esse teste funciona, mas ele é executado quatro vezes mais. Deixei só por causa do forEach.
     * TODO Apagar este método
     *
     * @throws FilmeSemEstoqueException quando algum item da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException        quando <code>Usuario</code> for igual a nulo.
     */
    @ParameterizedTest
    @MethodSource("getFilmes")
    @Disabled
    public void deveCalcularValorLocaçãoConsiderandoDescontosComProblemas() throws FilmeSemEstoqueException, LocadoraException {
//        Given
        Usuario usuario = new Usuario("Wanderley");

//        When
        int index = 0;
        double valorLocacao = filme1.getPrecoLocacao() + filme2.getPrecoLocacao();

        for (List<Filme> listasElemento : getFilmes()) {
            Locacao locacao = locaçãoService.alugarFilme(usuario, listasElemento);
            switch (index) {
                case 0 -> valorLocacao += filme3.getPrecoLocacao() * 75 / 100;
                case 1 -> valorLocacao += filme3.getPrecoLocacao() * 50 / 100;
                case 2 -> valorLocacao += filme3.getPrecoLocacao() * 25 / 100;
            }

            MatcherAssert.assertThat(locacao.getValor(), CoreMatchers.is(valorLocacao));

            index++;
        }
    }

    /**
     * Teste que executa várias vezes seguindo os parâmetros determinados.
     *
     * @throws FilmeSemEstoqueException quando algum item da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException        quando <code>Usuario</code> for igual a nulo.
     */
    @ParameterizedTest(name = "{index}º teste")
    @MethodSource("getFilmes")
    public void deveCalcularValorLocaçãoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
//        When
        Locacao locacao = locaçãoService.alugarFilme(usuário, filmes);
        switch (índice) {
            case 0 -> valorTotalLocação += filme3.getPrecoLocacao() * 75 / 100;
            case 1 -> valorTotalLocação += filme3.getPrecoLocacao() * 50 / 100;
            case 2 -> valorTotalLocação += filme3.getPrecoLocacao() * 25 / 100;
        }

//        Then
        MatcherAssert.assertThat(locacao.getValor(), CoreMatchers.is(valorTotalLocação));
        índice++;
    }
}