package desafio.dtos;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import desafio.model.Cliente;
import desafio.model.Venda;
import desafio.model.Vendedor;

public class LoteVendasDTO {

	private File arquivo;
	private Map<String, Vendedor> vendedores;
	private Map<String, Cliente> clientes;
	private List<Venda> vendas;

	public LoteVendasDTO(File arquivo) {
		this.arquivo = arquivo;
		this.vendedores = new HashMap<String, Vendedor>();
		this.clientes = new HashMap<String, Cliente>();
		this.vendas = new ArrayList<Venda>();
	}
	
	public File getArquivo() {
		return arquivo;
	}

	public void adicionaVendedor(Vendedor vendedor) {
		vendedores.put(vendedor.getCpf(), vendedor);
	}

	public void adicionaCliente(Cliente cliente) {
		clientes.put(cliente.getCnpj(), cliente);
	}

	public void adicionaVenda(Venda venda) {
		vendas.add(venda);
	}

	public Map<String, Vendedor> getVendedores() {
		return vendedores;
	}

	public Map<String, Cliente> getClientes() {
		return clientes;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

}
