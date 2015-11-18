package br.alimec.server.connect;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import br.alimec.server.main.Log;
import br.alimec.server.main.Main;

public class LookupServer {

	private DatagramSocket sock;
	private byte[] buffer = new byte[512];

	private Executor workerExec = Executors.newFixedThreadPool(Main
			.getThreadPoolSize());
	private Thread serverThread;
	private boolean running;

	public LookupServer(int lookupPort) {
		try {
			sock = new DatagramSocket(lookupPort);
			buffer = new byte[512];
		} catch (SocketException e) {
			Log.getStandardLog().printException(e, null);
		}
	}

	public void start() {
		if (serverThread == null) {

			serverThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (!serverThread.isInterrupted() && running) {
						LookupWorker worker = listen();
						
						if (worker != null) {
							workerExec.execute(worker);
						}

					}
					sock.close();
				}
			});
			running = true;
			serverThread.start();
		}
	}

	public void stop() {
		running = false;
		serverThread = null;

	}

	public LookupWorker listen() {
		DatagramPacket pack = new DatagramPacket(buffer, 512);
		try {
			sock.receive(pack);
			new JSONObject(new String(pack.getData()));
			return new LookupWorker(sock, pack);

		} catch (IOException e1) {
			
			Log.getStandardLog().printException(e1, null);
		} catch (JSONException e) {/*DO nothing*/}

		return null;

	}

}
