package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

class LocacaoServiceTest {
    SoftAssertions softAssertions;
    LocacaoService locacaoService;

    /**
     * Visto que há mais de um teste utilizando soft assertions, eu garanti que, para cada teste, uma nova instância é criada.
     */
    @BeforeEach
    void setUp() {
        locacaoService = new LocacaoService();
        softAssertions = new SoftAssertions();
    }

    /**
     * Caso o teste não esteja esperando uma exceção, a melhor opção é deixar que o JUnit a trate.
     *
     * @throws Exception exceção que fará o teste dar erro.
     */
    @Test
    void main() throws Exception {
//      Given
        Usuario usuario = new Usuario("Wanderley");
        Filme filme = new Filme("Mother", 1, 5.);

//		When
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

//		Then
        softAssertions.assertThat(locacao.getValor()).as("Preço da locação incorreto").isEqualTo(5);
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
        Filme filme = new Filme("Mother", 0, 5.);
        LocacaoService locacaoService = new LocacaoService();

//		Then
        Assertions.assertThrows(FilmeSemEstoqueException.class, () -> locacaoService.alugarFilme(usuario, filme)); // Visto que há uma exceção específica, a solução elegante se tornou completa. Deixando a robusta obsoleta.
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
        Filme filme = new Filme("Matrix", 2, 6.);

//        When
        try {
            locacaoService.alugarFilme(null, filme); //Se eu inserir um usuário, o teste falhará
            Assertions.fail();
        } catch (LocadoraException locadoraException) { // Eu trato esta exceção
            MatcherAssert.assertThat(locadoraException.getMessage(), CoreMatchers.is("Usuário vazio"));
//            locadoraException.printStackTrace();
        }
    }
}