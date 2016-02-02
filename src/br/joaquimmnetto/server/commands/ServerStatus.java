package br.joaquimmnetto.server.commands;

import org.json.JSONArray;
import org.json.JSONObject;

import br.joaquimmnetto.server.utils.JSONUtils;
import br.joaquimmnetto.simpleserver.core.Service;


public class ServerStatus implements Service{

	@Override
	public JSONObject processCommand(JSONArray params) {
		return JSONUtils.criarJSONSucesso();
	}

}
