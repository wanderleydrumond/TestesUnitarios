package br.ce.wcaquino;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Esta classe demonstra como garantir a ordem em que os testes serão executados caso haja necessidade de violar o princípio de independência dos mesmos.
 */
public class OrdemTest {

    public static int contador = 0;

    public void iniciar() {
        contador = 1;
    }

    public void verificar() {
        Assertions.assertEquals(1, contador);
    }

    @Test
    public void garantirOrdemTestes() {
        iniciar();
        verificar();
    }
}