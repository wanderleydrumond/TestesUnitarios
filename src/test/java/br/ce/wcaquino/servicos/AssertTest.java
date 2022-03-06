package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AssertTest {

    @BeforeEach
    void setUp() {
    }
    @Test
    public void verificarVerdade() {
//        Verifica se o valor é true
        Assertions.assertTrue(true);
        Assertions.assertTrue(!false); // Não é recomendado utilizar negações
    }

    @Test
    public void verificarFalsidade() {
        Assertions.assertFalse(false);
        Assertions.assertFalse(!true);  // Não é recomendado utilizar negações
    }

    @Test
    public void verificarIgualdadeInteiros() {
        Assertions.assertEquals(1,1);
        Assertions.assertEquals(1,1, "Inteiros diferentes");
    }

    @Test
    public void verificarIgualdadePontoFlutuante() {
        Assertions.assertEquals(0.51234, 0.512,0.001);
        Assertions.assertEquals(Math.PI, 3.14,0.01); // O delta é a margem de erro do número decimal.
    }

    @Test
    public void verificarValorPrimitivoWrapper() {
        int i1 = 5;
        Integer i2 = 5;

        Assertions.assertEquals(i1, i2); // No JUnit 5 há o conceito de boxing/unboxing. É comparado o valor contido na Wrapper class com o valor primitivo correspondente.
        Assertions.assertEquals(Integer.valueOf(i1), i2); // Pega o valor contigo na Wrapper class e compara com o primitivo.
        Assertions.assertEquals(i1,i2.intValue()); // Transforma o valor primitivo em uma Wrapper class.
    }

    @Test
    public void verificarIgualdadeStrings() {
        Assertions.assertEquals("bola", "bola");
        Assertions.assertNotEquals("bola", "Bola");
        Assertions.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assertions.assertTrue("bola".startsWith("bo"));
    }

    @Test
    public void verificarIgualdadeObjetos() {
//        Given
        Usuario usuario1 = new Usuario("Usuário");
        Usuario usuario2 = new Usuario("Usuário");

//        Then
        Assertions.assertEquals(usuario1,usuario2); // Só funciona se tiver o método equals implementado na classe
    }

    @Test
    public void verificarInstancia() {
//        Given
        Usuario usuario1 = new Usuario("Usuário");
        Usuario usuario2 = new Usuario("Usuário");
        Usuario usuario3 = usuario2;

//        Then
        Assertions.assertSame(usuario2,usuario3);
        Assertions.assertNotSame(usuario1,usuario2);
    }

    @Test
    public void verificarNulidade() {
//        Given
        Usuario usuario1 = new Usuario("Usuário");
        Usuario usuario2 = null;

//        Then
        Assertions.assertNull(usuario2);
        Assertions.assertNotNull(usuario1);
    }
}