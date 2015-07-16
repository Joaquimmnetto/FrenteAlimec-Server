package br.alimec.server.commands;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.alimec.server.main.JSONUtils;
import br.alimec.server.main.Main;

public class CommServerLookup extends Command {

	@Override
	public JSONObject processCommand(JSONArray params) {
		JSONObject result = null;
		try {
			JSONObject retorno = JSONUtils.gerarJSONSucesso();

			retorno.put("endereco", InetAddress.getLocalHost().getHostAddress());
			retorno.put("porta", Main.getPorta());
			retorno.put("versao", 0.0); // TODO: Versionamento(futuro).
			
			result = JSONUtils.gerarJSONSucesso().put("lookup", retorno);

		} catch (JSONException | UnknownHostException e) {
			e.printStackTrace();
			result = JSONUtils.gerarJSONFalha(e);
		}
		
		return result;
	}

}
