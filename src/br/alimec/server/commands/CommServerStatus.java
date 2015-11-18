package br.alimec.server.commands;

import org.json.JSONArray;
import org.json.JSONObject;

import br.alimec.server.main.JSONUtils;

public class CommServerStatus extends Command{

	@Override
	public JSONObject processCommand(JSONArray params) {
		return JSONUtils.criarJSONSucesso();
	}

}
