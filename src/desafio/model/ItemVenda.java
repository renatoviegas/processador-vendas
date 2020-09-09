package desafio.model;

import java.math.BigDecimal;

public class ItemVenda {

	private Integer id;
	private double quantidade;
	private BigDecimal preco;

	public ItemVenda(Integer id, double quantidade, BigDecimal preco) {
		this.id = id;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public BigDecimal getValorTotal() {
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
}
