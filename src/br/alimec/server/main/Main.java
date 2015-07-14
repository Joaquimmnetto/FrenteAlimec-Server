package br.alimec.server.main;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.sun.jmx.remote.internal.ArrayQueue;

import br.alimec.server.connect.Server;
import br.alimec.server.connect.ServerWorker;

public class Main {

	// TODO: STATUS ATUAL: TESTAR O DAO DAS PLANILHAS, CLIENTE-SERVIDOR ESTA
	// OKAY :D

	private static int porta = 9009;
	private static int threadPoolSize = 5;

	public static void main(String[] args) {

		parseArgs(args);

		Executor exec = Executors.newFixedThreadPool(threadPoolSize);

		Log log = new Log(System.out, System.err);

		log.println("Iniciando Servidor na porta " + porta);
		Server server = new Server(porta);
		boolean running = true;
		while (running) {
			try {
				log.println("\nAguardando conexoes...");
				ServerWorker worker = server.listen();
				log.println("Conexao com " + worker.getAddress() + ":"
						+ worker.getPort() + " estabelecida.");

				exec.execute(worker);

			} catch (Exception e) {
				e.printStackTrace();
				// log.printException(e, server.getLastCommand());

			}
		}

		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//
	// public static Log getLog() {
	// return log;
	// }

	private static void parseArgs(String[] args) {
		ArrayDeque<String> argsQueue = new ArrayDeque<String>(
				Arrays.asList(args));

		while (!argsQueue.isEmpty()) {
			if(argsQueue.peek().equals("-u")){
				//TODO: atualizar os nomes das planilhas
			}
			//listen
			if (argsQueue.peek().equals("-l")) {
				argsQueue.removeFirst();
				porta = Integer.parseInt(argsQueue.removeFirst());
			}
			//thread pool
			if (argsQueue.peek().equals("-tp")) {
				argsQueue.removeFirst();
				threadPoolSize = Integer.parseInt(argsQueue.removeFirst());
			}
		}
	}
}
