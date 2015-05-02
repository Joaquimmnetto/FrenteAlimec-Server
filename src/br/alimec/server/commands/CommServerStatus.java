package br.alimec.server.commands;

import org.json.JSONArray;
import org.json.JSONObject;

import br.alimec.server.main.JSONUtils;

public class CommServerStatus extends Command {

	@Override
	public JSONObject processCommand(JSONArray args) {
		
		//TODO: passar informações sobre o servidor(versão, ping, data, etc). Por enquanto, só diz que há conexão.
		return JSONUtils.gerarJSONSucesso();
	}

}
