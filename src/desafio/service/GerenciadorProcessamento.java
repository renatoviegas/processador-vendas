package desafio.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import desafio.enums.DiretorioEnum;
import desafio.interfaces.ProcessadorArquivo;

public class GerenciadorProcessamento {

	private boolean processando = false;

	private static GerenciadorProcessamento instancia;

	public static GerenciadorProcessamento getInstancia() {
		if (instancia == null) {
			instancia = new GerenciadorProcessamento();
		}

		return instancia;
	}

	public void ativar() throws IOException {
		if (processando)
			return;

		File[] arquivos = recuperaArquivosParaProcessar();

		if (arquivos.length > 0) {
			processando = true;
			System.out.println("Iniciando o processamento...");

			for (int i = 0; i < arquivos.length; ++i) {			
				processaArquivo(arquivos[i]);
			}

			System.out.println("Processamento finalizado. Aguardando novos arquivos...");
			processando = false;
			ativar();
		}

	}

	private void processaArquivo(File arquivo) {
		ProcessadorArquivo processador = new ProcessadorArquivoVendas();
		if (processador.processa(arquivo)) {
			moveArquivo(arquivo, DiretorioEnum.PROCESSADO);		
			return;
		}
		
		moveArquivo(arquivo, DiretorioEnum.ERRO);		
	}

	private void moveArquivo(File arquivo, DiretorioEnum destino) {
		Path sourcePath = Paths.get(DiretorioEnum.ENTRADA.getPath() + arquivo.getName());
		Path destinationPath = Paths.get(destino.getPath() + arquivo.getName());

		try {
			Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File[] recuperaArquivosParaProcessar() throws IOException {
		File file = new File(DiretorioEnum.ENTRADA.getPath());
		File[] arquivosParaProcessar = file.listFiles();
		return arquivosParaProcessar;
	}
}
