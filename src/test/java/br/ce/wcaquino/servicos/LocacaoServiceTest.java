package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals(5., locacao.getValor()); // Verifica o preço da locação
        assertTrue(isMesmaData(locacao.getDataLocacao(), new Date())); // Verifica a data da locação
        assertTrue(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1))); // Verifica a data de retorno da locação
    }
}