package br.ce.wcaquino.utils;

import org.jetbrains.annotations.NotNull;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe que contém métodos utilitários para verificações de datas.
 */
public class DataUtils {
	
	/**
	 * Retorna a data enviada por parâmetro com a adição dos dias desejados.
	 * A Data pode estar no futuro (dias > 0) ou no passado (dias < 0).
	 * 
	 * @param data A data atual
	 * @param dias Os dias que será realizada a diferença
	 * @return O dia resultante
	 */
	public static @NotNull Date adicionarDias(Date data, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(DAY_OF_MONTH, dias);
		return calendar.getTime();
	}
	
	/**
	 * Retorna a data atual com a diferença de dias enviados por parâmetro.
	 * A Data pode estar no futuro (parâmetro positivo) ou no passado (parâmetro negativo).
	 * 
	 * @param dias Quantidade de dias a ser incrementado/decrementado
	 * @return Data atualizada
	 */
	public static @NotNull Date obterDataComDiferencaDias(int dias) {
		return adicionarDias(new Date(), dias);
	}
	
	/**
	 * Retorna uma instância de <code>Date</code> refletindo os valores passados por parâmetro.
	 * 
	 * @param dia O dia atual.
	 * @param mes O mês atual.
	 * @param ano O ano atual.
	 * @return A data atualizada.
	 */
	public static @NotNull Date obterData(int dia, int mes, int ano){
		Calendar calendar = Calendar.getInstance();
		calendar.set(DAY_OF_MONTH, dia);
		calendar.set(MONTH, mes - 1);
		calendar.set(YEAR, ano);
		return calendar.getTime();
	}
	
	/**
	 * Verifica se uma data é igual a outra
	 * 	Esta comparação considera apenas dia, mes e ano
	 * 
	 * @param data1 A primeira data a ser verificada.
	 * @param data2 A segunda data a ser verificada.
	 * @return Valor <code>boolean</code> resultante da verificação.
	 */
	public static boolean isMesmaData(Date data1, Date data2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);
		return (calendar1.get(DAY_OF_MONTH) == calendar2.get(DAY_OF_MONTH))
				&& (calendar1.get(MONTH) == calendar2.get(MONTH))
				&& (calendar1.get(YEAR) == calendar2.get(YEAR));
	}
	
	/**
	 * Verifica se uma determinada data é o dia da semana desejado
	 * 
	 * @param data Data a ser avaliada
	 * @param diaSemana <code>true</code> caso seja o dia da semana desejado, <code>false</code> em caso contrário 
	 * @return Valor <code>boolean</code> resultante da verificação.
	 */
	public static boolean verificarDiaSemana(Date data, int diaSemana) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		return calendar.get(DAY_OF_WEEK) == diaSemana;
	}
}