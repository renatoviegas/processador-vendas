package desafio.enums;

public enum TipoDadoEnum {
	ND(""), VENDEDOR("001"), CLIENTE("002"), VENDA("003");

	private String codigo;

	TipoDadoEnum(String codigo) {
		this.codigo = codigo;
	}

	public static TipoDadoEnum getTipo(String codigo) {
		for (TipoDadoEnum tipo : TipoDadoEnum.values()) {
			if (tipo.codigo.equals(codigo))
				return tipo;
		}

		return ND;
	}
}
