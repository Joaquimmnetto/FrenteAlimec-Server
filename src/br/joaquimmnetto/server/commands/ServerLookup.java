package br.joaquimmnetto.server.commands;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.joaquimmnetto.server.main.Main;
import br.joaquimmnetto.server.utils.JSONUtils;
import br.joaquimmnetto.simpleserver.core.Service;
import br.joaquimmnetto.simpleserver.logger.ServerLog;
import br.joaquimmnetto.simpleserver.logger.ServerLogImpl;

public class ServerLookup implements Service{
	
	private ServerLog log = ServerLogImpl.getStandardLog();
	
	@Override
	public JSONObject processCommand(JSONArray params) {
		JSONObject result = null;
		try {
			JSONObject retorno = JSONUtils.criarJSONSucesso();

			retorno.put("endereco", InetAddress.getLocalHost().getHostAddress());
			retorno.put("porta", Main.getPorta());
			retorno.put("versao", 0.1); // TODO: Versionamento(futuro).
			
			result = JSONUtils.criarJSONSucesso().put("lookup", retorno);

		} catch (JSONException | UnknownHostException e) {
			log.printException(e, "ServerLookup");
			result = JSONUtils.criarJSONFalha(e);
		}
		
		return result;
	}

}
