package desafio.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Venda {

	private Integer id;
	private String nomeVendedor;
	private List<ItemVenda> itens;

	public Venda(Integer id, List<ItemVenda> itens, String nomeVendedor) {
		this.id = id;
		this.itens = itens;
		this.nomeVendedor = nomeVendedor;
	}

	public Integer getId() {
		return id;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public List<ItemVenda> getItens() {
		if (itens == null) {
			itens = new ArrayList<ItemVenda>();
		}

		return Collections.unmodifiableList(itens);
	}

	public void adicionaItemVenda(ItemVenda item) {
		itens.add(item);
	}

	public BigDecimal getValorTotal() {
		return itens.stream().map(item -> item.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
	}
}
