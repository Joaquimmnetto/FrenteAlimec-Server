package br.alimec.server.main;
import org.json.JSONObject;


public class JSONUtils {
	
	
	
	public static JSONObject gerarJSONSucesso(){
		return new JSONObject().put("success", true)
						.put("message", "Operacao realizada com sucesso");
	}
	
	
	public static JSONObject gerarJSONFalha(String message){
		return new JSONObject().put("success", false)
				.put("message", message);
	}
	
	public static JSONObject gerarJSONFalha(Exception e){
		return new JSONObject().put("success", false)
				.put("message", e.getMessage());
	}
	

}
