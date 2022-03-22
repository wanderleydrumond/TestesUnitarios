package br.ce.wcaquino.servicos;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.LocaçãoBuilder;
import br.ce.wcaquino.builders.UsuárioBuilder;
import br.ce.wcaquino.daos.LocaçãoDAO;
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
import org.junit.jupiter.api.*;
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
 * @version 5.0
 * @since 05/03/2022
 */
class LocaçãoServiceTest {
    private List<Filme> filmes;
    private SoftAssertions softAssertions;
    private LocaçãoService locaçãoService;
    private SPCService spcService;
    private LocaçãoDAO locaçãoDAO;
    private EmailService emailService;

    /**
     * Visto que há mais de um teste utilizando <i>soft assertions</i>, eu garanti que, para cada teste, uma nova instância é criada.
     */
    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
//        Given
        locaçãoService = new LocaçãoService();
        locaçãoDAO = Mockito.mock(LocaçãoDAO.class);
        locaçãoService.setLocaçãoDAO(locaçãoDAO);
        spcService = Mockito.mock(SPCService.class);
        locaçãoService.setSpcService(spcService);
        emailService = Mockito.mock(EmailService.class);
        locaçãoService.setEmailService(emailService);
    }

    /**
     * Caso o teste não esteja esperando uma exceção, a melhor opção é deixar que o JUnit a trate.
     *
     * @throws Exception exceção que fará o teste dar erro.
     */
    @Test
    @DisplayName("Aluguel de filmes")
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
    @DisplayName("Não é possível alugar um filme caso o mesmo não exista no estoque")
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
    @DisplayName("Devolver o filme na segunda caso alugue no sábado")
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

    /**
     * Teste que verifica se o usuário está negativado. Caso esteja, lançará uma exceção. O teste está a espera desta exceção.
     *
     * @throws FilmeSemEstoqueException caso o valor do estoque seja nulo.
     * @throws LocadoraException        caso o valor do <code>Usuario</code> seja nulo.
     */
    @Test
    @DisplayName("Verificar se o usuário está negativado no SPC")
    void nãoDeveAlugarFilmeParaNegativadoSPC() {
//        Given
        Usuario usuarioCorreto = UsuárioBuilder.umUsuário().agora();
        Usuario usuarioErrado = UsuárioBuilder.umUsuário().comNome("Anna Helena").agora(); // Caso utilize este usuário o teste falhará.
        List<Filme> filmes = List.of(FilmeBuilder.umFilme().agora());

        Mockito.when(spcService.possuiNegativação(usuarioCorreto)).thenReturn(true); // Manipulando o Mockito para que a devida resposta seja dada.
//        When
        try {
            locaçãoService.alugarFilme(usuarioCorreto, filmes);
//        Then
            Assertions.fail();
        } catch (FilmeSemEstoqueException | LocadoraException exception) {
            MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("Usuário negativado"));
//            exception.printStackTrace();
        }
        Mockito.verify(spcService).possuiNegativação(usuarioCorreto);
    }

    /**
     * Teste que verifica se foi envido e-mail para um determinado usuário que deveria ter entregue a lista de filmes, mas ainda não o fez.
     */
    @Test
    @DisplayName("Verificar se o e-mail foi enviado àqueles que tem locações atrasadas")
    void deveEnviarEmailParaLocaçõesAtrasadas() {
//        Given
        Usuario usuarioAtrasado1 = UsuárioBuilder.umUsuário().agora();
        Usuario usuarioEmDia = UsuárioBuilder.umUsuário().comNome("Anna Helena").agora();
        Usuario usuarioAtrasado2 = UsuárioBuilder.umUsuário().comNome("Francisco").agora();
        List<Locação> locações = List.of(LocaçãoBuilder.umaLocação().comUsuário(usuarioAtrasado1).atrasada().agora(),
                LocaçãoBuilder.umaLocação().comUsuário(usuarioEmDia).agora(),
                LocaçãoBuilder.umaLocação().comUsuário(usuarioAtrasado2).atrasada().agora(),
                LocaçãoBuilder.umaLocação().comUsuário(usuarioAtrasado2).atrasada().agora());
        Mockito.when(locaçãoDAO.obterLocaçõesPendentes()).thenReturn(locações);
//        When
        locaçãoService.notificarAtrasos();
//
        Mockito.verify(emailService).notificarAtraso(usuarioAtrasado1); // verifica se o e-mail foi enviado
        Mockito.verify(emailService, Mockito.atLeast(2)).notificarAtraso(usuarioAtrasado2); // verifica se o e-mail foi enviado pelo menos duas vezes
        Mockito.verify(emailService, Mockito.never()).notificarAtraso(usuarioEmDia); // verifica se o e-mail nunca foi enviado
        Mockito.verifyNoMoreInteractions(emailService); // verifica se mais nenhum e-mail foi enviado fora deste escopo.
    }
}