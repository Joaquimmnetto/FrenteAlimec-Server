package br.alimec.server.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONObject;

import br.alimec.poiDAO.ItemVendaDAO;
import br.alimec.server.main.JSONUtils;

public class CommEnviarVenda extends Command {

	public static final String PLANILHA_1_QUADRIMESTRE = "VENDAS %d - Jan-Abr.xls";
	public static final String PLANILHA_2_QUADRIMESTRE = "VENDAS %d - Mai-Ago.xls";
	public static final String PLANILHA_3_QUADRIMESTRE = "VENDAS %d - Set-Dez.xls";

	// params
	static enum Argumentos {
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
	public JSONObject processCommand(JSONObject[] params) {

		JSONObject obj = params[0];
		JSONObject resp = new JSONObject();

		Date data = new Date(obj.getLong(Argumentos.DATA.name()));

		try {
			ItemVendaDAO dao = new ItemVendaDAO(escolherPlanilha(data));

			MeiosPgto meioPgto = MeiosPgto.valueOf(obj
					.getString(Argumentos.MEIO_PGTO.toString()));
			double valorTotal = obj
					.getDouble(Argumentos.VALOR_TOTAL.toString());
			double[] modosPgto = new double[4];
			modosPgto[meioPgto.ordinal()] = valorTotal;

			double quantidade = obj.getDouble(Argumentos.QUANTIDADE.toString());
			String unidade = obj.getString(Argumentos.UNIDADE.toString());
			String codItem = obj.getString(Argumentos.CODITEM.toString());
			String cliente = obj.getString(Argumentos.CLIENTE.toString());
			String complemento = obj.getString(Argumentos.COMPLEMENTO
					.toString());

			dao.addItemVenda(data, modosPgto, quantidade, unidade, codItem,
					cliente, complemento);
			resp = JSONUtils.gerarJSONSucesso();
		} catch (FileNotFoundException e) {
			resp = JSONUtils
					.gerarJSONFalha("Arquivo para essa data não existe");
			e.printStackTrace();
		} catch (IOException e) {
			resp = JSONUtils.gerarJSONFalha("Erro inesperado de IO");
			e.printStackTrace();
		}

		return resp;
	}

	private String escolherPlanilha(Date data) {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(data);

		switch ((cal.get(Calendar.MONTH) - 1) / 4) {
		case 0:
			return String.format(PLANILHA_1_QUADRIMESTRE,
					cal.get(Calendar.YEAR));
		case 1:
			return String.format(PLANILHA_2_QUADRIMESTRE,
					cal.get(Calendar.YEAR));
		case 2:
			return String.format(PLANILHA_3_QUADRIMESTRE,
					cal.get(Calendar.YEAR));

		}

		return null;
	}

}
