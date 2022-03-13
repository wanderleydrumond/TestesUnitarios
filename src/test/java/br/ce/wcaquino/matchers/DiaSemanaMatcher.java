package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Classe que implementa os métodos do matcher personalizado.
 */
public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

    private final Integer diaSemana;

    public DiaSemanaMatcher(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    /**
     * Verifica o dia da semana de acordo com um valor inteiro digitado ou vindo de um enumerador. Ex.: Calendar.MONDAY.
     *
     * @param data A data que deverá ser comparada.
     * @return o valor booleano caso a <code>data</code> seja corresponda ao <code>diaDaSemana</code>
     */
    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.verificarDiaSemana(data, diaSemana);
    }

    /**
     * Exibe a descrição do assert.
     *
     * @param description A descrição o qual o assert deve ter.
     */
    @Override
    public void describeTo(@NotNull Description description) {
        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_WEEK, diaSemana);
        String dataPorExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        description.appendText(dataPorExtenso);
    }
}