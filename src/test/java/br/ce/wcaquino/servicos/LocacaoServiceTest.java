package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

class LocacaoServiceTest {
    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    void main() {
//        Given
        Usuario usuario = new Usuario("Wanderley");
        Filme filme = new Filme("Mother", 1, 5.);
        Date date = new Date();
        LocacaoService locacaoService = new LocacaoService();

        try {
//		When
            Locacao locacao = locacaoService.alugarFilme(usuario, filme);
//		Then
        softAssertions.assertThat(locacao.getValor()).as("Preço da locação incorreto").isEqualTo(5);
        softAssertions.assertThat(locacao.getDataLocacao()).as("Data da locação incorreta").isEqualTo(locacaoService.dataLocacao);
        softAssertions.assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1))).isEqualTo(true);

        softAssertions.assertAll();
        } catch (Exception exception) {
            exception.printStackTrace();
            Assertions.fail("Não deveria lançar exceção");
        }

    }
}