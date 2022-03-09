package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.DivisaoPorZeroException;

public class Calculadora {
    public int somarDoisInteiros(int valor1, int valor2) {
        return valor1 + valor2;
    }

    public int subtrairDoisInteiros(int valor1, int valor2) {
        return valor1 - valor2;
    }

    public int dividirDoisInteiros(int valor1, int valor2) throws DivisaoPorZeroException {
        if (valor2 == 0) {
            throw new DivisaoPorZeroException();
        }
        return valor1 / valor2;
    }
}