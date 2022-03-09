package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.DivisaoPorZeroException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void somarDoisInteiros() {
//        Given
        int valor1 = 5, valor2 = 3;
//        When
        int resultado = calculadora.somarDoisInteiros(valor1,valor2);
//        Then
        Assertions.assertEquals(8,resultado);
    }

    @Test
    public void subtrairDoisInteiros() {
//        Given
        int valor1 = 8, valor2 = 5;

//        When
        int resultado = calculadora.subtrairDoisInteiros(valor1,valor2);

//        Then
        Assertions.assertEquals(3, resultado);
    }

    @Test
    public void dividirDoisInteiros() throws DivisaoPorZeroException {
//        Given
        int valor1 = 6, valor2 = 3;

//        When
        int resultado = calculadora.dividirDoisInteiros(valor1,valor2);

//        Then
        Assertions.assertEquals(2, resultado);
    }

    @Test
    public void lancarExcecaoDivisaoZero() throws DivisaoPorZeroException {
//        Given
        int valor1 = 10, valor2 = 0;

//        Then
        Assertions.assertThrows(DivisaoPorZeroException.class, () -> calculadora.dividirDoisInteiros(valor1,valor2));
    }
}