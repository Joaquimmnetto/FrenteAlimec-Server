package br.alimec.server.main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Arrays;





import br.alimec.server.connect.LookupServer;
import br.alimec.server.connect.Server;

public class Main {
	
	private static int portaLookup = 9008;
	private static int porta = 9009;
	private static int threadPoolSize = 5;

	public static void main(String[] args) throws InterruptedException, UnknownHostException {

		parseArgs(args);
		Log.generateNewStandardLog();
		
		Log.getStandardLog().println("O IP local é: "+InetAddress.getLocalHost());
		
		Log.getStandardLog().println("Iniciando lookup na porta " + portaLookup);
		final LookupServer lookup = new LookupServer(portaLookup);
		lookup.start();
		
		Log.getStandardLog().println("Iniciando servidor na porta " + porta);
		final Server server = new Server(porta);
		server.start();
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				lookup.stop();
				server.stop();
				Log.getStandardLog().println("Servidor encerrado com sucesso.");
			}
		}));
		
	
		while(true){Thread.sleep(500);Thread.yield();}
	
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
