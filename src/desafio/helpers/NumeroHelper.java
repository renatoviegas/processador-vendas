package desafio.helpers;

import java.math.BigDecimal;

public class NumeroHelper {

	public static BigDecimal strToBigDecimal(String valor) {
		return BigDecimal.valueOf(strToDouble(valor));
	}

	public static Double strToDouble(String valor) {
		return Double.valueOf(valor);
	}
}
