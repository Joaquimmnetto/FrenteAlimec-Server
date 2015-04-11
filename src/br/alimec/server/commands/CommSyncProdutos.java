package br.alimec.server.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import br.alimec.poiDAO.ProdutoDAO;
import br.alimec.pojo.Produto;
import br.alimec.server.main.JSONUtils;

public class CommSyncProdutos extends Command {

	// Param: nenhum

	private static final String LISTA_PRODUTO_PATH = "ListaProduto.xls";

	@Override
	public JSONObject processCommand(JSONObject[] params) {
		try {
			ProdutoDAO dao = new ProdutoDAO(LISTA_PRODUTO_PATH);
			List<Produto> produtos = dao.getAllProdutos();
			JSONArray retorno = new JSONArray();
			for (Produto p : produtos) {
				retorno.put(produtoToJSON(p));
			}
			return JSONUtils.gerarJSONSucesso().put("Produtos", retorno);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return JSONUtils.gerarJSONFalha(e);
		} catch (IOException e) {
			e.printStackTrace();
			return JSONUtils.gerarJSONFalha(e);
		}
	}

	
	private JSONObject produtoToJSON(Produto produto) {
		JSONObject json = new JSONObject();

		json.put("codigo", produto.getCodigo());
		json.put("descricao", produto.getDescricao());

		return json;

	}

}
