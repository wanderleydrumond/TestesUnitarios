package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocacaoServiceTest {
    @Test
    void main() {
//        Given
        Usuario usuario = new Usuario("Wanderley");
        Filme filme = new Filme("Mother", 1, 5.);
        LocacaoService locacaoService = new LocacaoService();

//		When
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

//		Then
        // Verifica o preço da locação
        assertThat(locacao.getValor(), is(equalTo(5.)));
        assertThat(locacao.getValor(), is(not(6.)));

        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true)); // Verifica a data da locação
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true)); // Verifica a data de retorno da locação
    }
}