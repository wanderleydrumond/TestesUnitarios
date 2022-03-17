package br.ce.wcaquino;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.jupiter.api.*;

/**
 * Classe que apresenta as assertivas mais básicas.
 *
 * @author Wanderley Drumond
 * @since 06/03/2022
 * @version 6.0
 */
public class AssertTest {
    /**
     * Para que a instância se permaneça para ser utilizada em todos os métodos é necessário que os atributos sejam estáticos.
     */
    private static int incrementador, decrementador;

    /**
     * Este método é executado antes da instanciação de cada método.
     */
    @BeforeEach
    void setUpEach() {
        System.out.println("Incrementador: " + ++incrementador);
    }

    /**
     * Este método é executado depois da instanciação de cada método.
     */
    @AfterEach
    void tearDownEach() {
        System.out.println("Decrementador: " + --decrementador);
    }

    /**
     * Este método é executado antes da instanciação da classe.
     */
    @BeforeAll
    static void afterAll() {
        System.out.println("Incrementador: " + (incrementador = 0) + "\n" + "Decrementador: " + (decrementador = 0));
    }

    /**
     * Este método é executado depois da instanciação da classe.
     */
    @AfterAll
    static void tearDownAll() {
        System.out.println("Incrementador: " + incrementador * 10 + "\n" + "Decrementador: " + decrementador * 10);
    }

    /**
     * Verifica se o valor é <code>true</code>.
     */
    @Test
    @DisplayName("Verificar se o valor é verdadeiro")
    void verificarVerdade() {
        Assertions.assertTrue(true);
        Assertions.assertTrue(!false); // Não é recomendado utilizar negações
    }

    /**
     * Verifica se o valor é <code>false</code>.
     */
    @Test
    @DisplayName("Verificar se o valor é falso")
    void verificarFalsidade() {
        Assertions.assertFalse(false);
        Assertions.assertFalse(!true);  // Não é recomendado utilizar negações
    }

    /**
     * Verifica se dois números inteiros têm o mesmo valor.
     */
    @Test
    @DisplayName("Verificar se um valor inteiro é igual a outro")
    void verificarIgualdadeInteiros() {
        Assertions.assertEquals(1,1);
        Assertions.assertEquals(1,1, "Inteiros diferentes");
    }

    /**
     * Verifica se dois números decimais têm o mesmo valor.
     */
    @Test
    @DisplayName("Verificar se um valor com ponto flutuante é igual a outro")
    void verificarIgualdadePontoFlutuante() {
        Assertions.assertEquals(0.51234, 0.512,0.001);
        Assertions.assertEquals(Math.PI, 3.14,0.01); // O delta é a margem de erro do número decimal.
    }

    /**
     * Verificar se um valor contido numa <i>Wrapper class</i> corresponde ao valor do seu primitivo.
     */
    @Test
    @DisplayName("Verificar boxing/unboxing")
    void verificarValorPrimitivoWrapper() {
        int i1 = 5;
        Integer i2 = 5;

        Assertions.assertEquals(i1, i2); // No JUnit 5 há o conceito de boxing/unboxing. É comparado o valor contido na Wrapper class com o valor primitivo correspondente.
        Assertions.assertEquals(Integer.valueOf(i1), i2); // Pega o valor contigo na Wrapper class e compara com o primitivo.
        Assertions.assertEquals(i1,i2.intValue()); // Transforma o valor primitivo em uma Wrapper class.
    }

    /**
     * Comparar valor entre <i>strings</i>.
     */
    @Test
    @DisplayName("Comparar os valores das strings")
    void verificarIgualdadeStrings() {
        Assertions.assertEquals("bola", "bola");
        Assertions.assertNotEquals("bola", "Bola");
        Assertions.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assertions.assertTrue("bola".startsWith("bo"));
    }

    /**
     * Verificar se um objeto tem o mesmo valor que outro.
     */
    @Test
    @DisplayName("Verificar igualdade de valor entre objetos")
    void verificarIgualdadeObjetos() {
//        Given
        Usuario usuario1 = new Usuario("Usuário");
        Usuario usuario2 = new Usuario("Usuário");

//        Then
        Assertions.assertEquals(usuario1,usuario2); // Só funciona se tiver o método equals implementado na classe
    }

    /**
     * Verificar se um objeto tem a mesma instância que outra.
     */
    @Test
    @DisplayName("Verificar igualdade de instância entre objetos")
    void verificarInstancia() {
//        Given
        Usuario usuario1 = new Usuario("Usuário");
        Usuario usuario2 = new Usuario("Usuário");
        Usuario usuario3 = usuario2;

//        Then
        Assertions.assertSame(usuario2,usuario3);
        Assertions.assertNotSame(usuario1,usuario2);
    }

    /**
     * Verificar se um objeto é nulo.
     */
    @Test
    @DisplayName("Verificar se um objeto é nulo")
    void verificarNulidade() {
//        Given
        Usuario usuario1 = new Usuario("Usuário");
        Usuario usuario2 = null;

//        Then
        Assertions.assertNull(usuario2);
        Assertions.assertNotNull(usuario1);
    }
}