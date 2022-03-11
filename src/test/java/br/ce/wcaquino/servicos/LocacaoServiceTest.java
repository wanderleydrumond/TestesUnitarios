package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

class LocacaoServiceTest {
    List<Filme> filmes;
    private SoftAssertions softAssertions;
    private LocacaoService locacaoService;
    private Filme filme1, filme2, filme3;

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
    void testeLocacaoBemSucedida() throws Exception {
//      Given
        Assumptions.assumeFalse(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY)); // Verifica se hoje não é sábado

        filme1 = new Filme("Mother", 1, 5.);
        filme2 = new Filme("Matrix", 2, 7.);
        filme3 = new Filme("Interestelar", 4, 6.5);
        filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3));

        Usuario usuario = new Usuario("Wanderley");

        AtomicReference<Double> soma = new AtomicReference<>(0.);
        AtomicInteger índice = new AtomicInteger();
        /*int index = 0;
        Double somatório = 0.;*/

//		When
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

//        Solução utilizando forEach convencional
        /*for (Filme filmeElemento : filmes) {
            if (index == 2) {
                filmes.get(index).setPrecoLocacao(filmeElemento.getPrecoLocacao() * 75 / 100);
            }
            somatório += filmeElemento.getPrecoLocacao();
            index++;
        }*/

//        Solução utilizando forEach com lambda
        filmes.forEach(filmeElemento -> {
            if (índice.get() == 2) {
                filmes.get(índice.get()).setPrecoLocacao(filmeElemento.getPrecoLocacao() * 75 / 100);
            }
            soma.updateAndGet(somaAuxiliar -> somaAuxiliar + filmeElemento.getPrecoLocacao());
            índice.getAndIncrement();
        });

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
    public void naoDeveAlugarFilmeSemEstoque_Elegante() throws Exception {

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
    public void naoDeveAlugarFilmesSemUsuario_Robusta() throws FilmeSemEstoqueException { // Deixo o JUnit tratar esta exceção
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

    /**
     * Deve aplicar desconto de 25% no valor do terceiro filme na mesma locação.
     *
     * @throws FilmeSemEstoqueException quando algum item da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException quando <code>Usuario</code> for igual a nulo.
     */
    @Test
    public void devePagar75Filme3() throws FilmeSemEstoqueException, LocadoraException {
//        Given
        Usuario usuario = new Usuario("Wanderley");

        filme1 = new Filme("Mother", 1, 10.);
        filme2 = new Filme("Matrix", 2, 10.);
        filme3 = new Filme("Interestelar", 4, 10.);
        filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3));

//        When
        Locacao locacao = locacaoService.alugarFilme(usuario,filmes);
//        Then
        MatcherAssert.assertThat(locacao.getValor(), CoreMatchers.is(27.5));
    }

    /**
     * Deve aplicar desconto cumulativo de 50% no valor do terceiro filme na mesma locação.
     *
     * @throws FilmeSemEstoqueException quando algum item da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException quando <code>Usuario</code> for igual a nulo.
     */
    @Test
    public void devePagar50Filme4() throws FilmeSemEstoqueException, LocadoraException {
//        Given
        Usuario usuario = new Usuario("Wanderley");

        filme1 = new Filme("Mother", 1, 10.);
        filme2 = new Filme("Matrix", 2, 10.);
        filme3 = new Filme("Interestelar", 4, 10.);
        Filme filme4 = new Filme("The Conjuring", 6, 10.);
        filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3, filme4));

//        When
        Locacao locacao = locacaoService.alugarFilme(usuario,filmes);
//        Then
        MatcherAssert.assertThat(locacao.getValor(), CoreMatchers.is(32.5));
    }

    /**
     * Deve aplicar desconto cumulativo de 75% no valor do quinto filme na mesma locação.
     *
     * @throws FilmeSemEstoqueException quando algum item da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException quando <code>Usuario</code> for igual a nulo.
     */
    @Test
    public void devePagar75Filme5() throws FilmeSemEstoqueException, LocadoraException {
//        Given
        Usuario usuario = new Usuario("Wanderley");

        filme1 = new Filme("Mother", 1, 10.);
        filme2 = new Filme("Matrix", 2, 10.);
        filme3 = new Filme("Interestelar", 4, 10.);
        Filme filme4 = new Filme("The Conjuring", 6, 10.);
        Filme filme5 = new Filme("The Day the Earth Stood Still", 5, 10.);
        filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3, filme4, filme5));

//        When
        Locacao locacao = locacaoService.alugarFilme(usuario,filmes);
//        Then
        MatcherAssert.assertThat(locacao.getValor(), CoreMatchers.is(35.));
    }

    /**
     * Deve aplicar desconto cumulativo de 100% no valor do sexto filme na mesma locação.
     *
     * @throws FilmeSemEstoqueException quando algum item da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException quando <code>Usuario</code> for igual a nulo.
     */
    @Test
    public void devePagar0Filme6() throws FilmeSemEstoqueException, LocadoraException {
//        Given
        Usuario usuario = new Usuario("Wanderley");

        filme1 = new Filme("Mother", 1, 10.);
        filme2 = new Filme("Matrix", 2, 10.);
        filme3 = new Filme("Interestelar", 4, 10.);
        Filme filme4 = new Filme("The Conjuring", 6, 10.);
        Filme filme5 = new Filme("The Day the Earth Stood Still", 5, 10.);
        Filme filme6 = new Filme("Midsommar", 1, 10.);
        filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6));

//        When
        Locacao locacao = locacaoService.alugarFilme(usuario,filmes);
//        Then
        MatcherAssert.assertThat(locacao.getValor(), CoreMatchers.is(35.));
    }

    /**
     * Se o filme for alugado no sábado, a devolução deve ser na segunda.
     *
     * @throws FilmeSemEstoqueException quando algum item da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException quando <code>Usuario</code> for igual a nulo.
     */
    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
//        Given
        Assumptions.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY)); // Verifica se hoje é sábado

        Usuario usuario = new Usuario("Wanderley");
        List<Filme> filmes = List.of(new Filme("Mother",1,5.));
//        When
        Locacao retorno = locacaoService.alugarFilme(usuario,filmes);
//        Then
        Assertions.assertTrue(DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY));
    }
}