package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

class LocacaoServiceTest {
    private SoftAssertions softAssertions;
    private LocacaoService locacaoService;
    private Filme filme1, filme2, filme3;
    List<Filme> filmes;

    /**
     * Visto que há mais de um teste utilizando soft assertions, eu garanti que, para cada teste, uma nova instância é criada.
     */
    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
//        Given
        locacaoService = new LocacaoService();
    }

    /**
     * Caso o teste não esteja esperando uma exceção, a melhor opção é deixar que o JUnit a trate.
     *
     * @throws Exception exceção que fará o teste dar erro.
     */
    @Test
    void testeLocacao() throws Exception {
//      Given
        filme1 = new Filme("Mother", 1, 5.);
        filme2 = new Filme("Matrix", 2, 7.);
        filme3 = new Filme("Interestelar", 4, 6.5);
        filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3));

        Usuario usuario = new Usuario("Wanderley");

        AtomicReference<Double> soma = new AtomicReference<>(0.);

//		When
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        filmes.forEach(filmeElemento -> soma.updateAndGet(somaAuxiliar -> somaAuxiliar + filmeElemento.getPrecoLocacao()));

//		Then
        softAssertions.assertThat(locacao.getValor()).as("Preço da locação incorreto").isEqualTo(soma.get().doubleValue());

        softAssertions.assertThat(locacao.getDataLocacao()).as("Data da locação incorreta").isEqualTo(locacaoService.dataLocacao);
        softAssertions.assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1))).isEqualTo(true);

        softAssertions.assertAll();
    }

    /**
     * Este está esperando uma exceção.
     * Solução elegante.
     *
     * @throws Exception exceção que fará o teste apresentar erro.
     */
    @Test
    public void filmeSemEstoqueElegante() throws Exception {

//        Given
        Usuario usuario = new Usuario("Wanderley");
        LocacaoService locacaoService = new LocacaoService();

        filme1 = new Filme("Mother", 1, 5.);
        filme2 = new Filme("Matrix", 0, 7.);
        filme3 = new Filme("Interestelar", 4, 6.5);
        filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3));

//		Then
        Assertions.assertThrows(FilmeSemEstoqueException.class, () -> locacaoService.alugarFilme(usuario, filmes)); // Visto que há uma exceção específica, a solução elegante se tornou completa. Deixando a robusta obsoleta.
    }

    /**
     * Este está esperando uma exceção.
     * Solução robusta.
     *
     * @throws FilmeSemEstoqueException exceção que fará o teste apresentar falha.
     */
    @Test
    public void usuarioVazioRobusta() throws FilmeSemEstoqueException { // Deixo o JUnit tratar esta exceção
//        Given
        filme1 = new Filme("Mother", 1, 5.);
        filme2 = new Filme("Matrix", 2, 7.);
        filme3 = new Filme("Interestelar", 4, 6.5);
        filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3));
//        When
        try {
            locacaoService.alugarFilme(null, filmes); //Se eu inserir um usuário, o teste falhará
            Assertions.fail();
        } catch (LocadoraException locadoraException) { // Eu trato esta exceção
//            Then
            MatcherAssert.assertThat(locadoraException.getMessage(), CoreMatchers.is("Usuário vazio"));
//            locadoraException.printStackTrace();
        }
    }
}