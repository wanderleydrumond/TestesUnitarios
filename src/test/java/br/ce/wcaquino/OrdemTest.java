package br.ce.wcaquino;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Esta classe demonstra como garantir a ordem em que os testes serão executados caso haja necessidade de violar o princípio de independência dos mesmos.
 */
@TestMethodOrder(MethodOrderer.MethodName.class) // Garante que os testes serão executados em ordem alfabética
public class OrdemTest {

    public static int contador = 0;

    @Test
    public void iniciar() {
        contador = 1;
    }

    @Test
    public void verificar() {
        Assertions.assertEquals(1, contador);
    }
}