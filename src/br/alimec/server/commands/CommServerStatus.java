package br.alimec.server.commands;

import org.json.JSONArray;
import org.json.JSONObject;

import br.alimec.server.main.JSONUtils;

public class CommServerStatus extends Command {

	@Override
	public JSONObject processCommand(JSONArray args) {
		
		//TODO: passar informa��es sobre o servidor(vers�o, ping, data, etc). Por enquanto, s� diz que h� conex�o.
		return JSONUtils.gerarJSONSucesso();
	}

}
