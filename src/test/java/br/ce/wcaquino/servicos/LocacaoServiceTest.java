package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

class LocacaoServiceTest {
    SoftAssertions softAssertions = new SoftAssertions();

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
}