package br.ce.wcaquino.matchers;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

/**
 * Classe do qual pertencerão os métodos deste matcher personalizado.
 *
 * @author Wanderley Drumond
 * @since 13/02/2022
 * @version 1.5
 */
public class MatchersPróprios {
    /**
     * Verifica se um determinado uma determinada data cai em um determinado dia da semana.
     *
     * @param diaSemana o qual deve ser verificado.
     * @return a data por extenso.
     */
    @Contract("_ -> new")
    public static @NotNull DiaSemanaMatcher caiEm(Integer diaSemana) {
        return new DiaSemanaMatcher(diaSemana);
    }

    /**
     * Verifica se um determinado uma determinada data cai em um segunda-feira.
     *
     * @return valor boolean.
     */
    @Contract(" -> new")
    public static @NotNull DiaSemanaMatcher caiNumaSegunda() {
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }
}