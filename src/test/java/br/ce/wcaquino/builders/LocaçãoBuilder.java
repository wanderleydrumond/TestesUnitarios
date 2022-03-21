package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locação;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;

/**
 * Objeto que cria uma <code>Locação</code>.
 * @author Wanderley Drumond
 * @since 21/03/2022
 * @version 1.0
 */
public class LocaçãoBuilder {
    /**
     * Variável que servirá de instância única para todos os métodos do objeto <code>Locação</code>.
     */
    private Locação elementoLocação;

    /**
     * Método construtor <b>privado</b> para somente que o próprio <i>Builder</i> possa criar instâncias de si mesmo.
     */
    private LocaçãoBuilder() {}

    public static @NotNull LocaçãoBuilder umaLocação() {
        LocaçãoBuilder locaçãoBuilder = new LocaçãoBuilder();
        inicializarDadosPadrões(locaçãoBuilder);
        return locaçãoBuilder;
    }

    /**
     * Inicializa e insere valores padrão no objeto <code>Locação</code>.
     *
     * @param locaçãoBuilder Builder que gerará o objeto <code>locação</code>.
     */
    public static void inicializarDadosPadrões(@NotNull LocaçãoBuilder locaçãoBuilder) {
        locaçãoBuilder.elementoLocação = new Locação();
        Locação elementoLocação = locaçãoBuilder.elementoLocação;

        elementoLocação.setUsuario(UsuárioBuilder.umUsuário().agora());
        elementoLocação.setFilmes(Arrays.asList(FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora(), FilmeBuilder.umFilme().agora()));
        elementoLocação.setDataLocacao(new Date());
        elementoLocação.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
        elementoLocação.setValor(4.);
    }

    /**
     * Atualiza o estado do objeto <code>LocaçãoBuilder</code>.
     * <ul>
     *     <li>Muda o nome do usuário.</li>
     * </ul>
     * <p>Mais uma etapa que compõe o padrão <i>chained method</i>.</p>
     *
     * @param usuario Usuário que terá seu nome atualizado
     * @return a mesma instância de <code>LocaçãoBuilder</code> com o nome de usuário atualizado.
     */
    public LocaçãoBuilder comUsuário(Usuario usuario) {
        elementoLocação.setUsuario(usuario);
        return this;
    }

    /**
     * Atualiza o estado do objeto <code>LocaçãoBuilder</code>.
     * <ul>
     *     <li>Insere uma lista de filmes no objeto <code>Locação</code>.</li>
     * </ul>
     * <p>Mais uma etapa que compõe o padrão <i>chained method</i>.</p>
     *
     * @param filmes Lista de filmes a serem inseridos na locação.
     * @return a mesma instância de <code>LocaçãoBuilder</code> com a lista de filmes atualizada.
     */
    public LocaçãoBuilder comFilmes(Filme... filmes) {
        elementoLocação.setFilmes(Arrays.asList(filmes));
        return this;
    }

    /**
     * Atualiza o estado do objeto <code>LocaçãoBuilder</code>.
     * <ul>
     *     <li>Atualiza a data de locação.</li>
     * </ul>
     * <p>Mais uma etapa que compõe o padrão <i>chained method</i>.</p>
     *
     * @param dataDaLocação data do filme a ser alugado.
     * @return a mesma instância de <code>LocaçãoBuilder</code> com a data de locação atualizada.
     */
    public LocaçãoBuilder alugadoEm(Date dataDaLocação) {
        elementoLocação.setDataLocacao(dataDaLocação);
        return this;
    }

    /**
     * Atualiza o estado do objeto <code>LocaçãoBuilder</code>.
     * <ul>
     *     <li>Atualiza a data de retorno.</li>
     * </ul>
     * <p>Mais uma etapa que compõe o padrão <i>chained method</i>.</p>
     *
     * @param dataDoRetorno data que o filme deve ser devolvido.
     * @return a mesma instância de <code>LocaçãoBuilder</code> com a data de retorno atualizada.
     */
    public LocaçãoBuilder devolverEm(Date dataDoRetorno) {
        elementoLocação.setDataRetorno(dataDoRetorno);
        return this;
    }

    /**
     * Atualiza o estado do objeto <code>LocaçãoBuilder</code>.
     * <ul>
     *     <li>Atualiza o valor da locação.</li>
     * </ul>
     * <p>Mais uma etapa que compõe o padrão <i>chained method</i>.</p>
     *
     * @param valor Valor final da locação.
     * @return a mesma instância de <code>LocaçãoBuilder</code> com o valor final da locação atualizado.
     */
    public LocaçãoBuilder comValorDe(Double valor) {
        elementoLocação.setValor(valor);
        return this;
    }

    /**
     * Retorna uma <code>Locação</code> com valor atribuído.
     * @return um objeto <code>Locação</code> já instanciado e inicializado.
     */
    public Locação agora() {
        return elementoLocação;
    }
}