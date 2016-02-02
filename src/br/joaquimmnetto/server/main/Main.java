package br.joaquimmnetto.server.main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Arrays;

import br.joaquimmnetto.server.commands.EnviarVenda;
import br.joaquimmnetto.server.commands.ImportarProdutos;
import br.joaquimmnetto.server.commands.ServerLookup;
import br.joaquimmnetto.server.commands.ServerStatus;
import br.joaquimmnetto.simpleserver.core.TCPServer;
import br.joaquimmnetto.simpleserver.core.UDPServer;
import br.joaquimmnetto.simpleserver.logger.ServerLog;
import br.joaquimmnetto.simpleserver.logger.ServerLogImpl;

public class Main {
	
	private static int portaLookup = 9008;
	private static int porta = 9009;
	private static int threadPoolSize = 5;
	
	private static ServerLog log;
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException {

		parseArgs(args);
		ServerLogImpl.generateNewStandardLog();
		log = ServerLogImpl.getStandardLog();
		
		log.println("O IP local é: "+InetAddress.getLocalHost());
		
		log.println("Iniciando lookup na porta " + portaLookup);
		
		final UDPServer lookupServer = new UDPServer(portaLookup,threadPoolSize);
		lookupServer.registerService("ServerLookup", new ServerLookup());
		lookupServer.start();
		
		
		log.println("Iniciando servidor na porta " + porta);
		final TCPServer appServer = new TCPServer(porta,threadPoolSize);
		appServer.registerService("EnviarVenda", new EnviarVenda());
		appServer.registerService("ImportarProdutos", new ImportarProdutos());
		appServer.registerService("ServerStatus", new ServerStatus());
		
		appServer.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				lookupServer.stop();
				appServer.stop();
				log.println("Servidor encerrado com sucesso.");
			}
		}));
		
	
		while(true){Thread.yield();Thread.sleep(500);Thread.yield();}
	
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
