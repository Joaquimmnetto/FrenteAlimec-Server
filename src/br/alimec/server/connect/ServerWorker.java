package br.alimec.server.connect;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import br.alimec.server.commands.Command;
import br.alimec.server.main.JSONUtils;
import br.alimec.server.main.Log;

public class ServerWorker implements Runnable {

	private Socket comm;
	private Command command;
	private Log log;

	public ServerWorker(Socket comm) {
		this.comm = comm;
		this.log = Log.getStandardLog();
	}

	public void run() {

		JSONObject response;

		// recv
		try {
			BufferedInputStream in = new BufferedInputStream(
					comm.getInputStream());
			StringWriter inJson = new StringWriter();
			int readen = -1;
			while (true) {
				readen = in.read();

				if (readen < 0 || readen > 254) {
					break;
				}
				inJson.append((char) readen);
			}
			JSONObject clientMsg = new JSONObject(inJson.toString());
			log.println("Mensagem recebida:\n" + clientMsg.toString(4));

			command = Command.createCommand(clientMsg.getString("comando"));

			response = command.processCommand(clientMsg.optJSONArray("args"));
			log.println("Resposta processada:\n" + response.toString(4));

		} catch (IOException e) {
			log.printException(e, command);
			response = JSONUtils.gerarJSONFalha(e);
		} catch (ClassNotFoundException e) {
			log.printException(e, command);
			response = JSONUtils.gerarJSONFalha(e);
		} catch (JSONException e) {
			log.printException(e, command);
			response = JSONUtils.gerarJSONFalha(e);
		}
		// send
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					comm.getOutputStream());
			out.write(response.toString().getBytes());
			out.write(255);
			out.flush();
		} catch (IOException e) {
			log.printException(e, command);
		}
		try {
			comm.close();
		} catch (IOException e) {
			log.printException(e, command);
		}
	}

	public synchronized InetAddress getAddress() {
		return comm.getInetAddress();
	}

	public synchronized Command getLastCommand() {
		return command;
	}

	public int getPort() {
		return comm.getPort();
	}

	public void setLog(Log log) {
		this.log = log;
	}

}
