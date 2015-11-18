package br.alimec.server.connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.alimec.server.main.Log;
import br.alimec.server.main.Main;

public class Server {

	private ServerSocket sSock;
	private Executor workerExec = Executors.newFixedThreadPool(Main
			.getThreadPoolSize());
	private Thread serverThread;
	private boolean running;
	private Log log = Log.getStandardLog();

	public Server(int porta) {
		try {
			sSock = new ServerSocket(porta);
		} catch (IOException e) {
			Log.getStandardLog().printException(e);
		}
	}

	public void start() {
		if (serverThread == null) {

			serverThread = new Thread(new Runnable() {

				@Override
				public void run() {
					log.println("Aguardando conexoes...");
					while (!serverThread.isInterrupted() && running) {
						ServerWorker worker = listen();
						if (worker == null) {
							continue;
						}

						log.println("Conexao com " + worker.getAddress() + ":"
								+ worker.getPort() + " estabelecida.");

						workerExec.execute(worker);
					}
					try {
						sSock.close();
					} catch (IOException e) {
						Log.getStandardLog().printException(e);
					}
				}
			});
			running = true;
			serverThread.start();
		}
	}

	public void stop() {
		running = false;
	}

	public ServerWorker listen() {
		try {
			return new ServerWorker(sSock.accept());
		} catch (IOException e) {
			Log.getStandardLog().printException(e);
		}

		return null;

	}

	public void close() throws IOException {
		if (sSock != null) {
			sSock.close();
		}
	}

}
