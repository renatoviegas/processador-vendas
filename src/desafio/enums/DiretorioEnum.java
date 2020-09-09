package desafio.enums;

public enum DiretorioEnum {
	ENTRADA("data/in/"), PROCESSADO("data/processed/"), SAIDA("data/out/"), ERRO("data/unprocessed/");

	private String path;

	DiretorioEnum(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
