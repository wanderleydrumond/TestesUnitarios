package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocaçãoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locação;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

/**
 * Classe de execução do sistema.
 */
public class LocaçãoService {

    /**
     * Atributo que inicializa com a data atual.
     * <p>Modificador de acesso protected para poder ter acesso na classe de teste.</p>
     */
    protected Date dataLocação = new Date();

    /**
     * Atributo cuja finalidade é dar acesso aos métodos da interface <code>LocaçãoDAO</code>.
     */
    private LocaçãoDAO locaçãoDAO;

    /**
     * Atributo cuja finalidade é dar acesso aos métodos da interface <code>SPCService</code>.
     */
    private SPCService spcService;

    /**
     * Inicializa o sistema.
     *
     * @param args Argumento padrão do método main.
     */
    public static void main(String[] args) {
//		Given
        Usuario usuario = new Usuario("Wanderley");

        Filme filme1 = new Filme("Mother", 1, 5.);
        Filme filme2 = new Filme("Matrix", 2, 7.);
        Filme filme3 = new Filme("Interestelar", 4, 6.5);
        Filme filme4 = new Filme("The Conjuring", 6, 10.);
        Filme filme5 = new Filme("The Day the Earth Stood Still", 5, 10.);
        Filme filme6 = new Filme("Midsommar", 1, 10.);

        List<Filme> filmes = new ArrayList<>(Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6));

        LocaçãoService locaçãoService = new LocaçãoService();

//		When
        Locação locação = null;
        try {
            locação = locaçãoService.alugarFilme(usuario, filmes);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

//		Then
        // Verifica o preço da locação
        filmes.stream().filter(filmeElement -> filmeElement.getPrecoLocacao() == 5. || filmeElement.getPrecoLocacao() == 6.5 || filmeElement.getPrecoLocacao() == 7. || filmeElement.getPrecoLocacao() == 10.).map(filmeElement -> true).forEach(System.out::println);
        assert locação != null;
        System.out.println(DataUtils.isMesmaData(locação.getDataLocacao(), new Date())); // Verifica a data da locação
        System.out.println(DataUtils.isMesmaData(locação.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1))); // Verifica a data de retorno da locação
    }

    /**
     * Atualiza os valores da locação para o usuário e a lista de filmes passados por parâmetro.
     *
     * @param usuario O usuário do qual deve ser feita a modificação.
     * @param filmes  A lista de filmes do qual deve ser feita a modificação.
     * @return A <code>Locação</code> atualizada.
     */
    public Locação alugarFilme(Usuario usuario, @NotNull List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
        if (usuario == null) {
            throw new LocadoraException("Usuário vazio");
        }

        if (filmes.isEmpty()) {
            throw new FilmeSemEstoqueException();
        }

        for (Filme filmeElemento : filmes) {
            if (filmeElemento.getEstoque() == 0) {
                throw new FilmeSemEstoqueException();
            }
        }

        if (spcService.possuiNegativação(usuario)) {
            throw new LocadoraException("Usuário negativado");
        }

        Locação locação = new Locação();
        locação.setFilmes(filmes);
        locação.setUsuario(usuario);
        locação.setDataLocacao(dataLocação);

        Double valorTotal = 0., auxiliar;
        int index = 0;

        for (Filme filmeElemento : filmes) {
            auxiliar = switch (index) {
                case 2 -> filmes.get(index).getPrecoLocacao() * 75 / 100;
                case 3 -> filmes.get(index).getPrecoLocacao() * 50 / 100;
                case 4 -> filmes.get(index).getPrecoLocacao() * 25 / 100;
                case 5 -> 0.;
                default -> filmeElemento.getPrecoLocacao();
            };
            valorTotal += auxiliar;
            index++;
        }
        locação.setValor(valorTotal);

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 1);
        }
        locação.setDataRetorno(dataEntrega);

        //Salvando a locação...
        locaçãoDAO.salvar(locação);

        /*Princípios dos testes unitários
         * Fast -> os resultados precisam ser exibidos imediatamente para garantir que sempre serão executados no momento correto.
         * Independent -> Tudo o que o teste precisa para executar já está nele. || Isolate -> Cada teste é responsável por encontrar uma única falha isoladamente.
         * Repeatable -> O teste precisa executar quantas vezes forem necessárias e exibir sempre o mesmo resultado.
         * Self-Verifying -> O teste deve ser capaz de verificar quando sua execução foi correta ou quando a mesma falhou ou deu erro.
         * Timely -> O teste deve ser criado no momento certo.*/

        return locação;
    }

    /**
     * Instancia um objeto <code>LocaçãoDAO</code> através de injeção de dependência.
     *
     * @param locaçãoDAO objeto <code>LocaçãoDAO</code> a ser instanciado.
     */
    public void setLocaçãoDAO(LocaçãoDAO locaçãoDAO) {
        this.locaçãoDAO = locaçãoDAO;
    }

    /**Instancia um objeto <code>SPCService</code> através de injeção de dependência.
     *
     *
     * @param spcService objeto <code>SPCService</code> a ser instanciado.
     */
    public void setSpcService(SPCService spcService) {
        this.spcService = spcService;
    }
}