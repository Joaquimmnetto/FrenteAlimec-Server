package br.alimec.server.main;

import java.util.ArrayDeque;
import java.util.Arrays;


import br.alimec.server.connect.LookupServer;
import br.alimec.server.connect.Server;

public class Main {

	// TODO: STATUS ATUAL: TESTAR O DAO DAS PLANILHAS, CLIENTE-SERVIDOR ESTA
	// OKAY :D
	
	private static int portaLookup = 9008;
	private static int porta = 9009;
	private static int threadPoolSize = 5;

	public static void main(String[] args) {

		parseArgs(args);

		Log log = new Log(System.out, System.err);
		
		log.println("Iniciando lookup na porta " + portaLookup);
		final LookupServer lookup = new LookupServer(portaLookup);
		lookup.start();
		
		log.println("Iniciando Servidor na porta " + porta);
		final Server server = new Server(porta);
		server.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				lookup.stop();
				server.stop();
			}
		}));
		
	}

	//
	// public static Log getLog() {
	// return log;
	// }

	private static void parseArgs(String[] args) {
		ArrayDeque<String> argsQueue = new ArrayDeque<String>(
				Arrays.asList(args));

		while (!argsQueue.isEmpty()) {
			if (argsQueue.peek().equals("-u")) {
				// TODO: atualizar os nomes das planilhas
			}
			// listen
			if (argsQueue.peek().equals("-l")) {
				argsQueue.removeFirst();
				porta = Integer.parseInt(argsQueue.removeFirst());
			}
			// thread pool
			if (argsQueue.peek().equals("-tp")) {
				argsQueue.removeFirst();
				threadPoolSize = Integer.parseInt(argsQueue.removeFirst());
			}
		}
	}

	public static int getPortaLookup(){
		return portaLookup;
	}
	
	public static int getPorta() {
		return porta;
	}
	
	public static int getThreadPoolSize() {
		return threadPoolSize;
	}
}
