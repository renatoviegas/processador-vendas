package desafio.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import desafio.dtos.LoteVendasDTO;
import desafio.enums.DiretorioEnum;
import desafio.model.Venda;
import desafio.model.Vendedor;

public class ProcessadorLoteVendas {

	private LoteVendasDTO lote;

	public ProcessadorLoteVendas(LoteVendasDTO lote) {
		this.lote = lote;
	}

	public void gerarRelatorio() throws IOException {
		int quantidadeClientes = contaClientes();
		int quantidadeVendedores = contaVendedores();
		Venda vendaMaisCara = retornaVendaMaisCara();
		Vendedor piorVendedor = verificaPiorVendedor();
		String filenameOut = DiretorioEnum.SAIDA.getPath() + "result-" + lote.getArquivo().getName();

		BufferedWriter writer = new BufferedWriter(new FileWriter(filenameOut));
		writer.append("Arquivo origem: " + lote.getArquivo().getName() + "\n");
		writer.append("------------------------------------------------------\n");
		writer.append("Quantidade de clientes: " + quantidadeClientes + "\n");
		writer.append("Quantidade de vendedores: " + quantidadeVendedores + "\n");
		writer.append("Venda mais cara - ID: " + vendaMaisCara.getId() + " | Valor: " + vendaMaisCara.getValorTotal() + "\n");
		writer.append("Pior vendedor: " + piorVendedor.getNome() + "\n");
		writer.append("------------------------------------------------------\n");
		writer.close();
	}

	private Vendedor verificaPiorVendedor() {
		Vendedor piorVendedor = null;
		BigDecimal valorVendas = BigDecimal.valueOf(Double.MAX_VALUE);

		for (Map.Entry<String, Vendedor> elemento : lote.getVendedores().entrySet()) {
			Vendedor vendedor = elemento.getValue();
			List<Venda> vendas = buscaVendasPorNome(vendedor.getNome());
			BigDecimal valor = vendas.stream().map(item -> item.getValorTotal())
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

			if (valorVendas.compareTo(valor) > 0) {
				valorVendas = valor;
				piorVendedor = vendedor;
			}
		}
		;

		return piorVendedor;
	}

	private Venda retornaVendaMaisCara() {
		Venda vendaMaisCara = null;
		BigDecimal maiorValorVenda = BigDecimal.valueOf(Double.MIN_VALUE);

		for (Venda venda : lote.getVendas()) {
			if (venda.getValorTotal().compareTo(maiorValorVenda) > 0) {
				maiorValorVenda = venda.getValorTotal();
				vendaMaisCara = venda;
			}
		}
		return vendaMaisCara;
	}

	private int contaVendedores() {
		return lote.getVendedores().size();
	}

	private int contaClientes() {
		return lote.getClientes().size();
	}

	private List<Venda> buscaVendasPorNome(String nomeVendedor) {
		return lote.getVendas().stream().filter(venda -> nomeVendedor.equals(venda.getNomeVendedor()))
				.collect(Collectors.toList());
	}

}
