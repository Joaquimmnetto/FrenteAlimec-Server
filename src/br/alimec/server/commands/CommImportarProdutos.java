package br.alimec.server.commands;

import java.text.Normalizer;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import br.alimec.poiDAO.ProdutoDAO;
import br.alimec.pojo.Produto;
import br.alimec.server.main.JSONUtils;

public class CommImportarProdutos extends Command {

	// Param: nenhum

	@Override
	public JSONObject processCommand(JSONArray params) {

		ProdutoDAO dao = ProdutoDAO.getInstance();
		List<Produto> produtos = dao.getAllProdutos();
		JSONArray retorno = new JSONArray();
		for (Produto p : produtos) {
			retorno.put(produtoToJSON(p));
		}
		return JSONUtils.gerarJSONSucesso().put("produtos", retorno);
	}

	private JSONObject produtoToJSON(Produto produto) {
		JSONObject json = new JSONObject();

		json.put("codigo", produto.getCodigo());
		json.put("descricao", removerAcentos(produto.getDescricao()));

		return json;

	}

	public String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll(
				"[^\\p{ASCII}]", "");
	}
}
