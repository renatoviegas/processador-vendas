package desafio.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import desafio.dtos.LoteVendasDTO;
import desafio.enums.TipoDadoEnum;
import desafio.exceptions.ArquivoException;
import desafio.helpers.NumeroHelper;
import desafio.interfaces.ProcessadorArquivo;
import desafio.model.Cliente;
import desafio.model.ItemVenda;
import desafio.model.Venda;
import desafio.model.Vendedor;

public class ProcessadorArquivoVendas implements ProcessadorArquivo {

	private static final String CARACTER_DELIMITADOR = "\\ç";

	public boolean processa(File arquivo) {
		System.out.println("Processando o arquivo '" + arquivo.getName() + "'...");
		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
			LoteVendasDTO loteVendasDTO = new LoteVendasDTO(arquivo);

			String linha = reader.readLine();
			while (linha != null) {
				String[] linhaArray = linha.split(CARACTER_DELIMITADOR);
				switch (TipoDadoEnum.getTipo(linhaArray[0])) {
				case VENDEDOR:
					loteVendasDTO.adicionaVendedor(processaVendedor(linhaArray));
					break;
				case CLIENTE:
					loteVendasDTO.adicionaCliente(processaCliente(linhaArray));
					break;
				case VENDA:
					loteVendasDTO.adicionaVenda(processaVenda(linhaArray));
					break;
				default:
					throw new ArquivoException("Linha sem identificador");
				}

				linha = reader.readLine();
			}

			ProcessadorLoteVendas processador = new ProcessadorLoteVendas(loteVendasDTO);
			processador.gerarRelatorio();

			System.out.println("Arquivo '" + arquivo.getName() + "' processado com sucesso.");
			return true;
		} catch (Exception e) {
			System.out.println("Erro ao tentar processar o arquivo '" + arquivo.getName() + "'");
		}

		return false;
	}

	private Venda processaVenda(String[] linhaArray) {
		Integer id = Integer.parseInt(linhaArray[1]);
		List<ItemVenda> itens = processaItensVenda(linhaArray[2]);
		String nomeVendedor = linhaArray[3];

		return new Venda(id, itens, nomeVendedor);
	}

	private List<ItemVenda> processaItensVenda(String itensVendaString) {
		List<ItemVenda> itens = new ArrayList<ItemVenda>();

		String string = itensVendaString.replace("[", "").replace("]", "");
		String[] itensStrArray = string.split(",");
		for (String itensStr : itensStrArray) {
			String[] itemStrArray = itensStr.split("-");

			Integer id = Integer.parseInt(itemStrArray[0]);
			Double quantidade = NumeroHelper.strToDouble(itemStrArray[1]);
			BigDecimal preco = NumeroHelper.strToBigDecimal(itemStrArray[2]);

			itens.add(new ItemVenda(id, quantidade, preco));
		}

		return itens;
	}

	private Cliente processaCliente(String[] linhaArray) {
		String cnpj = linhaArray[1];
		String nome = linhaArray[2];
		String areaNegocio = linhaArray[3];
		return new Cliente(cnpj, nome, areaNegocio);
	}

	private Vendedor processaVendedor(String[] linhaArray) {
		String cpf = linhaArray[1];
		String nome = linhaArray[2];
		BigDecimal salario = NumeroHelper.strToBigDecimal(linhaArray[3]);
		return new Vendedor(cpf, nome, salario);
	}
}
