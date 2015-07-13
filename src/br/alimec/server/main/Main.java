package br.alimec.server.main;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.alimec.server.connect.Server;
import br.alimec.server.connect.ServerWorker;

public class Main {

	//TODO: STATUS ATUAL: TESTAR O DAO DAS PLANILHAS, CLIENTE-SERVIDOR ESTA OKAY :D

	public static void main(String[] args) {
		int porta = 9009;
		Executor exec = Executors.newFixedThreadPool(5);
		
		Log log = new Log(System.out, System.err);
		
		log.println("Iniciando Servidor na porta " + porta);
		Server server = new Server(porta);
		boolean running = true;	
		while(running){
			try {
				log.println("\nAguardando conexoes...");
				ServerWorker worker = server.listen();
				log.println("Conexao com " + worker.getAddress()+":"+worker.getPort() + " estabelecida.");
				
				exec.execute(worker);
				
			} catch (Exception e) {
				e.printStackTrace();
	//			log.printException(e, server.getLastCommand());
			
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
//	public static Log getLog() {
//		return log;
//	}

}
