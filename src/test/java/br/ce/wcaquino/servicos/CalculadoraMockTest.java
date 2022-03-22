package br.ce.wcaquino.servicos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Classe criada para explicar como o Mockito pode manipular o comportamento de um método.
 */
public class CalculadoraMockTest {
    /**
     * Exibe o resultado esperado pelo Mockito realizando a chamada do método da própria classe.
     * <p>1 + 2 é 3 mas como eu defini que o resultado esperado é 5 é isso o que ele sempre esperará.</p>
     */
    @Test
    @DisplayName("Exibir valores esperados pelo Mockito")
    void testeValoresMockito() {
        Calculadora calculadora = Mockito.mock(Calculadora.class);
        //uma vez um parâmentro do método utiliza um matcher para obter um valor genérico, todos os demais precisarão também usá-lo. Senão dá erro.
        Mockito.when(calculadora.somarDoisInteiros(Mockito.eq(1),Mockito.anyInt())).thenReturn(5); // somente um valor é genérico. O outro é fixo.

        System.out.println(calculadora.somarDoisInteiros(1,8));
    }
}