package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
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
public class LocacaoService {

    protected Date dataLocacao = new Date(); // Modificador de acesso protected para poder ter acesso na classe de teste.

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

        LocacaoService locacaoService = new LocacaoService();

//		When
        Locacao locacao = null;
        try {
            locacao = locacaoService.alugarFilme(usuario, filmes);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

//		Then
        // Verifica o preço da locação
        filmes.stream().filter(filmeElement -> filmeElement.getPrecoLocacao() == 5. || filmeElement.getPrecoLocacao() == 6.5 || filmeElement.getPrecoLocacao() == 7. || filmeElement.getPrecoLocacao() == 10.).map(filmeElement -> true).forEach(System.out::println);
        assert locacao != null;
        System.out.println(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date())); // Verifica a data da locação
        System.out.println(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1))); // Verifica a data de retorno da locação
    }

    /**
     * Atualiza os valores da locação para o usuário e a lista de filmes passados por parâmetro.
     *
     * @param usuario O usuário do qual deve ser feita a modificação.
     * @param filmes  A lista de filmes do qual deve ser feita a modificação.
     * @return A <code>Locacao</code> atualizada.
     */
    public Locacao alugarFilme(Usuario usuario, @NotNull List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
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

        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(dataLocacao);

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
        locacao.setValor(valorTotal);

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 1);
        }
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locação...
        //TODO adicionar método para salvar

        /*Princípios dos testes unitários
         * Fast -> os resultados precisam ser exibidos imediatamente para garantir que sempre serão executados no momento correto.
         * Independent -> Tudo o que o teste precisa para executar já está nele. || Isolate -> Cada teste é responsável por encontrar uma única falha isoladamente.
         * Repeatable -> O teste precisa executar quantas vezes forem necessárias e exibir sempre o mesmo resultado.
         * Self-Verifying -> O teste deve ser capaz de verificar quando sua execução foi correta ou quando a mesma falhou ou deu erro.
         * Timely -> O teste deve ser criado no momento certo.*/

        return locacao;
    }
}