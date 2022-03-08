package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

class LocacaoServiceTest {
    SoftAssertions softAssertions;

    /**
     * Visto que há mais de um teste utilizando soft assertions, eu garanti que, para cada teste, uma nova instância é criada.
     */
    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
    }

    /**
     * Caso o teste não esteja esperando uma exceção, a melhor opção é deixar que o JUnit a trate.
     *
     * @throws Exception exceção que fará o teste dar erro.
     */
    @Test
    void main() throws Exception {
//        Given
        Usuario usuario = new Usuario("Wanderley");
        Filme filme = new Filme("Mother", 1, 5.);
        Date date = new Date();
        LocacaoService locacaoService = new LocacaoService();


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
     * @throws Exception exceção que fará o teste dar erro.
     */
    @Test
    public void filmeSemEstoqueElegante() throws Exception {

//        Given
        Usuario usuario = new Usuario("Wanderley");
        Filme filme = new Filme("Mother", 0, 5.);
        Date date = new Date();
        LocacaoService locacaoService = new LocacaoService();

//		Then
        Assertions.assertThrows(Exception.class, () -> locacaoService.alugarFilme(usuario, filme));
    }

    /**
     * Este está esperando uma exceção.
     * Solução robusta.
     * <p>
     * Além de capturar a exceção, é possível verificar a mensagem que vem da mesma.
     */
    @Test
    public void filmeSemEstoqueRobusta() {

//        Given
        Usuario usuario = new Usuario("Wanderley");
        Filme filme = new Filme("Mother", 0, 5.);
        Date date = new Date();
        LocacaoService locacaoService = new LocacaoService();

//		Then
        try {
            locacaoService.alugarFilme(usuario, filme);
            Assertions.fail("Exceção deveria ter sido lançada"); // Força o teste a falhar
        } catch (Exception exception) {
            MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.is("Não há este filme em estoque"));
//            exception.printStackTrace();
        }
    }
}