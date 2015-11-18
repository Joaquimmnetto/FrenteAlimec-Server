package br.alimec.server.connect;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.json.JSONException;
import org.json.JSONObject;

import br.alimec.server.commands.Command;
import br.alimec.server.main.Log;
import br.alimec.server.main.JSONUtils;

public class LookupWorker implements Runnable {

	private DatagramPacket recvPack;
	private DatagramSocket sock;
	private Command command;
	private Log log = Log.getStandardLog();

	public LookupWorker(DatagramSocket sock, DatagramPacket pack) {
		this.sock = sock;
		this.recvPack = pack;
	}

	@Override
	public void run() {
		try {
			JSONObject request = new JSONObject(new String(recvPack.getData()));

			log.println("Lookup recebido:\n" + request.toString(4));

			command = Command.createCommand(request
					.getString("comando"));
			JSONObject response = command.processCommand(request
					.optJSONArray("args"));
			
			String responseStr = response.toString(4);
			
			DatagramPacket sendPack = new DatagramPacket(
					responseStr.getBytes(), responseStr.length(),
					recvPack.getAddress(), recvPack.getPort());
			log.println("Enviando resposta:\n" + response.toString(4));
			sock.send(sendPack);
			
			

		} catch (IOException | ClassNotFoundException | JSONException e) {
			log.printException(e, command);
			JSONUtils.criarJSONFalha(e);
		}
		
	}
}
