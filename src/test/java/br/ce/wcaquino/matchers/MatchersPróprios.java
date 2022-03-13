package br.ce.wcaquino.matchers;

import java.util.Calendar;

/**
 * Classe do qual pertencerão os métodos deste matcher personalizado.
 */
public class MatchersPróprios {
    /**
     * Verifica se um determinado uma determinada data cai em um determinado dia da semana.
     *
     * @param diaSemana o qual deve ser verificado.
     * @return a data por extenso.
     */
    public static DiaSemanaMatcher caiEm(Integer diaSemana) { // TODO Aceitar esta sugestão?
        return new DiaSemanaMatcher(diaSemana);
    }

    /**
     * Verifica se um determinado uma determinada data cai em um segunda-feira.
     *
     * @return valor boolean.
     */
    public static DiaSemanaMatcher caiNumaSegunda() { // TODO Aceitar esta sugestão?
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }
}