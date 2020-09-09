package desafio.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Optional;

import desafio.enums.DiretorioEnum;

public class MonitoradorArquivoService {

	private static int quantidadeInstancia = 0;
	private GerenciadorProcessamento gerenciadorProcessamento;
	private Path diretorio;

	public static void execute() throws Exception {	
		System.out.println("Iniciando monitorador de de arquivo de vendas...");
		MonitoradorArquivoService monitorador = new MonitoradorArquivoService();
		
		System.out.println("Monitorando novos arquivos para processamento...");
		monitorador.monitoraNovosArquivos();
	}

	private MonitoradorArquivoService() throws Exception {
		quantidadeInstancia++;

		if (quantidadeInstancia > 1) {
			throw new Exception("Só pode existir um serviço de monitoramento ativo.");
		}

		diretorio = Paths.get(DiretorioEnum.ENTRADA.getPath());
		gerenciadorProcessamento = GerenciadorProcessamento.getInstancia();
		gerenciadorProcessamento.ativar();
	}

	private void monitoraNovosArquivos() throws IOException, InterruptedException {
		WatchService monitorador = FileSystems.getDefault().newWatchService();
		diretorio.register(monitorador, StandardWatchEventKinds.ENTRY_CREATE);

		while (true) {
			WatchKey key = monitorador.take();
			Optional<WatchEvent<?>> watchEvent = key.pollEvents().stream().findFirst();
			if (watchEvent.isPresent()) {
				if (watchEvent.get().kind() == StandardWatchEventKinds.OVERFLOW) {
					continue;
				}

				gerenciadorProcessamento.ativar();
			}

			boolean valido = key.reset();
			if (!valido) {
				break;
			}
		}

		monitorador.close();
	}
}
