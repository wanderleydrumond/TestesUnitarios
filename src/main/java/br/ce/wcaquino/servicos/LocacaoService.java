package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

/**
 * Classe de execução do sistema.
 */
public class LocacaoService {
    /**
     * Atualiza os valores da locação para o usuário e filme passados por parâmetro.
     *
     * @param usuario O usuário do qual deve ser feita a modificação.
     * @param filme O filme do qual deve ser feita a modificação.
     * @return A <code>Locacao</code> atualizada.
     */
    public Locacao alugarFilme(Usuario usuario, Filme filme) {
        Locacao locacao = new Locacao();
        locacao.setFilme(filme);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(filme.getPrecoLocacao());

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locação...
        //TODO adicionar método para salvar

        /*Princípios dos testes unitários
        * Fast -> os resultados precisam ser exibidos imediatamente
        * Independent -> Tudo o que o teste precisa para executar já está nele.
        * Repeatable -> O teste precisa executar quantas vezes forem necessárias e exibir sempre o mesmo resultado.
        * Self-Verifying -> O teste deve ser capaz de verificar quando sua execução foi correta ou quando a mesma falhou ou deu erro.
        * Timely -> O teste deve ser criado no momento certo.*/

        return locacao;
    }

    /**
     * Realiza os primeiros testes.
     *
     * @param args Argumentão padrão do método main.
     */
    public static void main(String[] args) {
//		Given
        Usuario usuario = new Usuario("Wanderley");
        Filme filme = new Filme("Mother", 1, 5.);
        LocacaoService locacaoService = new LocacaoService();

//		When
        Locacao locacao = locacaoService.alugarFilme(usuario,filme);

//		Then
        System.out.println(5. == locacao.getValor()); // Verifica o preço da locação
        System.out.println(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date())); // Verifica a data da locação
        System.out.println(DataUtils.isMesmaData(locacao.getDataRetorno(),DataUtils.obterDataComDiferencaDias(1))); // Verifica a data de retorno da locação
    }
}