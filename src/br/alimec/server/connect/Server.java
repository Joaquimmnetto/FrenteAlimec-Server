package br.alimec.server.connect;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import br.alimec.server.commands.Command;

public class Server {

	private int port;
	private ServerSocket sSock;
	private Socket sock;

	private Command lastCommand;

	public synchronized static boolean atomicAction(int port) {
		Server server = new Server(port);
		boolean ret = false;
		try {
			server.listen();
			String response = server.doCommand();
			server.sendResponse(response);
			ret = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public Server(int port) {
		this.port = port;
	}

	public ConexaoInfo listen() throws IOException {
		sSock = new ServerSocket(port);
		sock = sSock.accept(); // block

		return new ConexaoInfo(sock.getRemoteSocketAddress().toString(),
				sock.getPort());
	}
	//comando: FUNC_NAME JSON_ARGS
	public String doCommand() throws IOException {
		Scanner scan = new Scanner(sock.getInputStream());
		StringWriter inJson = new StringWriter();
		String commandToken = scan.next();
		try {
			Command command = Command.createCommand(commandToken);
			lastCommand = command;

			while (scan.hasNext()) {
				inJson.append(scan.nextLine());
			}
			JSONObject obj = command.processCommand(createJSONParams(inJson
					.toString()));

			return obj.toString();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Command getLastCommand() {
		return lastCommand;
	}

	public void sendResponse(String toSend) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(
				sock.getOutputStream());

		out.write(toSend.getBytes());
	}

	public void close() throws IOException {
		if (sock != null) {
			sock.close();
		}
		if (sSock != null) {
			sSock.close();
		}
	}

	private JSONObject[] createJSONParams(String stringParams) {
		JSONObject params = new JSONObject(stringParams);
		List<JSONObject> separatedParams = new ArrayList<JSONObject>();
		for (String name : JSONObject.getNames(params)) {
			separatedParams.add(params.getJSONObject(name));
		}

		return separatedParams.toArray(new JSONObject[separatedParams.size()]);
	}

}
