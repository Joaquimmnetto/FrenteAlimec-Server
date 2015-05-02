package br.alimec.server.commands;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.xml.internal.ws.util.StringUtils;

public abstract class Command {

	public static Command createCommand(String commandToken)
			throws ClassNotFoundException {
		commandToken = StringUtils.capitalize(commandToken);
		String packName = Command.class.getPackage().getName();

		Class<? extends Command> commClass = (Class<? extends Command>) Class
				.forName(packName + ".Comm" + commandToken);

		try {
			return commClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;

	}

	public abstract JSONObject processCommand(JSONArray params);

}
