package br.alimec.server.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import br.alimec.poiDAO.ItemVendaDAO;
import br.alimec.server.main.JSONUtils;

public class CommEnviarVenda extends Command {

	static enum ArgumentosVenda {
		DATA, CLIENTE, CPFCNPJ, ITENS;
		@Override
		public String toString() {
			return super.name().toLowerCase();
		}
	}

	static enum ArgumentosItem {
		DATA, QUANTIDADE, UNIDADE, CODITEM, CLIENTE, COMPLEMENTO, MEIO_PGTO, VALOR_TOTAL;
		@Override
		public String toString() {
			return super.name().toLowerCase();
		}

	};

	static enum MeiosPgto {
		DINHEIRO, CHEQUE, BOLETO, DUPLICATA;

		@Override
		public String toString() {
			return super.name().toLowerCase();
		}

	};

	@Override
	public JSONObject processCommand(JSONArray vendas) {

		List<ItemVendaTO> itemTOs = new ArrayList<>();

		JSONObject resp = new JSONObject();
		try {
			for (int i = 0; i < vendas.length(); i++) {
				JSONObject venda = vendas.getJSONObject(i);
				
				JSONArray itens = venda.getJSONArray(ArgumentosVenda.ITENS.toString());
				
				for (int j = 0; j < itens.length(); j++) {

					JSONObject item = itens.getJSONObject(j);

					Date data = new Date(item.getLong(ArgumentosItem.DATA
							.name()));

					MeiosPgto meioPgto = MeiosPgto.valueOf(item
							.getString(ArgumentosItem.MEIO_PGTO.toString()));
					double valorTotal = item
							.getDouble(ArgumentosItem.VALOR_TOTAL.toString());
					double[] modosPgto = new double[4];
					modosPgto[meioPgto.ordinal()] = valorTotal;

					double quantidade = item
							.getDouble(ArgumentosItem.QUANTIDADE.toString());
					String unidade = item.getString(ArgumentosItem.UNIDADE
							.toString());
					String codItem = item.getString(ArgumentosItem.CODITEM
							.toString());
					String cliente = item.getString(ArgumentosItem.CLIENTE
							.toString());
					String complemento = item
							.getString(ArgumentosItem.COMPLEMENTO.toString());

					itemTOs.add(new ItemVendaTO(data, valorTotal, modosPgto,
							quantidade, unidade, codItem, cliente, complemento));
				}
				for (ItemVendaTO itemTO : itemTOs) {
					ItemVendaDAO dao = ItemVendaDAO.getInstance(itemTO.getData());

					dao.addItemVenda(itemTO.getData(), itemTO.getModosPgto(),
							itemTO.getQuantidade(), itemTO.getUnidade(),
							itemTO.getCodItem(), itemTO.getCliente(),
							itemTO.getComplemento());
				}
			}
			ItemVendaDAO.comitarTodos();
			resp = JSONUtils.gerarJSONSucesso();

		} catch (Exception e) {
			e.printStackTrace();
			resp = JSONUtils.gerarJSONFalha(e);
		}

		return resp;
	}

}
