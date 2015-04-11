package br.alimec.server.main;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import br.alimec.server.connect.ConexaoInfo;
import br.alimec.server.connect.Server;

public class Main {

	

	public static void main(String[] args) {
		int porta = 9009;
		PrintStream log = System.out;
		
		log.println("Iniciando Servidor na porta " + porta);
		Server server = new Server(porta);
		log.println("Aguardando conexão...");

		try {
			ConexaoInfo info = server.listen();
			log.println("Conexão com " + info.toString() + " estabelecida.");

			String response = server.doCommand();
			log.println("Comando executado: " + server.getLastCommand());
			log.println("Resposta: " + response);
			log.println("Enviando resposta...");
			server.sendResponse(response);

		} catch (Exception e) {
			e.printStackTrace();
//			log.printException(e, server.getLastCommand());
		
		} finally {
			log.println("Closing connection...");
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
//				log.printException(e, server.getLastCommand());
			}
		}

	}
//
//	public static Log getLog() {
//		return log;
//	}

}
