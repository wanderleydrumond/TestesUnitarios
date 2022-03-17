package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.DivisaoPorZeroException;
import org.junit.jupiter.api.*;

class CalculadoraTest {

    private Calculadora calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new Calculadora();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Soma de dois números inteiros")
    void somarDoisInteiros() {
//        Given
        int valor1 = 5, valor2 = 3;
//        When
        int resultado = calculadora.somarDoisInteiros(valor1,valor2);
//        Then
        Assertions.assertEquals(8,resultado);
    }

    @Test
    @DisplayName("Subtração de dois números inteiros")
    void subtrairDoisInteiros() {
//        Given
        int valor1 = 8, valor2 = 5;

//        When
        int resultado = calculadora.subtrairDoisInteiros(valor1,valor2);

//        Then
        Assertions.assertEquals(3, resultado);
    }

    @Test
    @DisplayName("Divisão de dois números inteiros")
    void dividirDoisInteiros() throws DivisaoPorZeroException {
//        Given
        int valor1 = 6, valor2 = 3;

//        When
        int resultado = calculadora.dividirDoisInteiros(valor1,valor2);

//        Then
        Assertions.assertEquals(2, resultado);
    }

    @Test
    @DisplayName("Tratar o problema de uma divisão por zero")
    void lancarExcecaoDivisaoZero() throws DivisaoPorZeroException {
//        Given
        int valor1 = 10, valor2 = 0;

//        Then
        Assertions.assertThrows(DivisaoPorZeroException.class, () -> calculadora.dividirDoisInteiros(valor1,valor2));
    }

    @Test
    void deveDividir() {
        String dividendo = "6";
        String dividor = "3";

        int resultado = calculadora.dividir(dividendo,dividor);

        Assertions.assertEquals(2, resultado);
    }
}