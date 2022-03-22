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
        Mockito.when(calculadora.somarDoisInteiros(1,2)).thenReturn(5);

        System.out.println(calculadora.somarDoisInteiros(1,2));
    }
}