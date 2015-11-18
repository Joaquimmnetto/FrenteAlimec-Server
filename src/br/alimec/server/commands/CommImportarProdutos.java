package br.alimec.server.commands;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import br.alimec.poiDAO.ProdutoDAO;
import br.alimec.pojo.Categoria;
import br.alimec.pojo.Produto;
import br.alimec.pojo.ProdutoComposite;
import br.alimec.server.main.JSONUtils;

public class CommImportarProdutos extends Command {

	// Param: nenhum

	@Override
	public JSONObject processCommand(JSONArray params) {
		try {
			ProdutoDAO dao = ProdutoDAO.getInstance();
			List<ProdutoComposite> produtos = dao.getAllProdutos();

			JSONArray retorno = new JSONArray();
			
			Categoria raiz = new Categoria(ProdutoDAO.RAIZ_COD, "Raiz", null);
			retorno.put(produtoToJSON(raiz));
			for (ProdutoComposite p : produtos) {
				retorno.put(produtoToJSON(p));
			}
			

			return JSONUtils.criarJSONSucesso().put("produtos", retorno);
		} catch (IOException e) {
			e.printStackTrace();
			return JSONUtils.criarJSONFalha(e);
		}

	}

	private JSONObject produtoToJSON(ProdutoComposite produto) {
		JSONObject json = new JSONObject();

		json.put("codigo", produto.getCodigo());
		json.put("descricao", removerAcentos(produto.getDescricao()));
		json.putOpt("parenteCod", produto.getParenteCod());
		json.put("categoria", produto instanceof Categoria);

		return json;

	}

	public String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll(
				"[^\\p{ASCII}]", "");
	}
}
