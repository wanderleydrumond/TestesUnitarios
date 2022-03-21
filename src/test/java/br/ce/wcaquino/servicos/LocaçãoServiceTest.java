package br.ce.wcaquino.servicos;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuárioBuilder;
import br.ce.wcaquino.daos.LocaçãoDAO;
import br.ce.wcaquino.daos.LocaçãoDAOFake;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locação;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.MatchersPróprios;
import br.ce.wcaquino.utils.DataUtils;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

/**
 * Trata aspectos básicos de testes.
 *
 * @author Wanderley Drumond
 * @since 05/03/2022
 * @version 4.1
 */
class LocaçãoServiceTest {
    private List<Filme> filmes;
    private SoftAssertions softAssertions;
    private LocaçãoService locaçãoService;

    /**
     * Visto que há mais de um teste utilizando <i>soft assertions</i>, eu garanti que, para cada teste, uma nova instância é criada.
     */
    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
//        Given
        locaçãoService = new LocaçãoService();
        LocaçãoDAO locaçãoDAO = Mockito.mock(LocaçãoDAO.class);
        locaçãoService.setLocaçãoDAO(locaçãoDAO);
    }

    /**
     * Caso o teste não esteja esperando uma exceção, a melhor opção é deixar que o JUnit a trate.
     *
     * @throws Exception exceção que fará o teste dar erro.
     */
    @Test
    void testeLocaçãoBemSucedida() throws Exception {
//      Given
        Assumptions.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY)); // Verifica se hoje não é sábado

        filmes = new ArrayList<>(Arrays.asList(FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora()));

        Usuario usuario = UsuárioBuilder.umUsuário().agora();

        AtomicReference<Double> soma = new AtomicReference<>(0.);
        AtomicInteger índice = new AtomicInteger();

//		When
        Locação locação = locaçãoService.alugarFilme(usuario, filmes);

        filmes.forEach(filmeElemento -> {
            if (índice.get() == 2) {
                filmes.get(índice.get()).setPrecoLocacao(filmeElemento.getPrecoLocacao() * 75 / 100);
            }
            soma.updateAndGet(somaAuxiliar -> somaAuxiliar + filmeElemento.getPrecoLocacao());
            índice.getAndIncrement();
        });

//		Then
        softAssertions.assertThat(locação.getValor()).as("Preço da locação incorreto").isEqualTo(soma.get().doubleValue());
        softAssertions.assertThat(locação.getDataLocacao()).as("Data da locação incorreta").isEqualTo(locaçãoService.dataLocação);
        softAssertions.assertThat(isMesmaData(locação.getDataRetorno(), obterDataComDiferencaDias(1))).isEqualTo(true);

        softAssertions.assertAll();
    }

    /**
     * Este está esperando uma exceção.
     * Solução elegante.
     *
     * @throws Exception exceção que fará o teste apresentar erro.
     */
    @Test
    void naoDeveAlugarFilmeSemEstoque_Elegante() throws Exception {

//        Given
        Usuario usuario = UsuárioBuilder.umUsuário().agora();
        LocaçãoService locaçãoService = new LocaçãoService();
        filmes = new ArrayList<>(Arrays.asList(FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilmeSemEstoque().agora(), FilmeBuilder.umFilme().semEstoque().agora())); // padrão chained method.

//		Then
        Assertions.assertThrows(FilmeSemEstoqueException.class, () -> locaçãoService.alugarFilme(usuario, filmes)); // Visto que há uma exceção específica, a solução elegante se tornou completa. Deixando a robusta obsoleta.
    }

    /**
     * Este está esperando uma exceção.
     * Solução robusta.
     *
     * @throws FilmeSemEstoqueException exceção que fará o teste apresentar falha.
     */
    @Test
    void naoDeveAlugarFilmesSemUsuario_Robusta() throws FilmeSemEstoqueException { // Deixo o JUnit tratar esta exceção
        filmes = new ArrayList<>(Arrays.asList(FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora()));
//        When
        try {
            locaçãoService.alugarFilme(null, filmes); //Se eu inserir um usuário, o teste falhará
            Assertions.fail();
        } catch (LocadoraException locadoraException) { // Eu trato esta exceção
//            Then
            MatcherAssert.assertThat(locadoraException.getMessage(), CoreMatchers.is("Usuário vazio"));
//            locadoraException.printStackTrace();
        }
    }

    /**
     * Se o filme for alugado no sábado, a devolução deve ser na segunda.
     *
     * @throws FilmeSemEstoqueException quando algum ‘item’ da lista de filmes tiver estoque igual a 0.
     * @throws LocadoraException        quando <code>Usuario</code> for igual a nulo.
     */
    @Test
    void deveDevolverNaSegundaAoAlugarNoSábado() throws FilmeSemEstoqueException, LocadoraException {
//        Given
        Assumptions.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY)); // Verifica se hoje é sábado

        Usuario usuario = UsuárioBuilder.umUsuário().agora();
        List<Filme> filmes = List.of(new Filme("Mother", 1, 5.));
//        When
        Locação retorno = locaçãoService.alugarFilme(usuario, filmes);
//        Then
        MatcherAssert.assertThat(retorno.getDataRetorno(), MatchersPróprios.caiEm(Calendar.MONDAY)); // Abordagem 1
        MatcherAssert.assertThat(retorno.getDataRetorno(), MatchersPróprios.caiNumaSegunda()); // Abordagem 2
    }
}