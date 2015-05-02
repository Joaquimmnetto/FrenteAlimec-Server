package br.alimec.server.connect;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	private ServerSocket sSock;

	public Server(int port) {
		try {
			sSock = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ServerWorker listen() throws IOException {
		return new ServerWorker(sSock.accept());
	}

	public void close() throws IOException {
		if (sSock != null) {
			sSock.close();
		}
	}

}
