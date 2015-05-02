package br.alimec.server.commands;
import java.util.Date;

import br.alimec.poiDAO.ItemVendaDAO;


public class ItemVendaTO {
	
	private Date data;
	private double valorTotal;
	private double[] modosPgto;
	private double quantidade;
	private String unidade;
	private String codItem;
	private String cliente;
	private String complemento;
	
	
	public ItemVendaTO(Date data, double valorTotal,
			double[] modosPgto, double quantidade, String unidade,
			String codItem, String cliente, String complemento) {
		super();
		this.data = data;
		this.valorTotal = valorTotal;
		this.modosPgto = modosPgto;
		this.quantidade = quantidade;
		this.unidade = unidade;
		this.codItem = codItem;
		this.cliente = cliente;
		this.complemento = complemento;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}

	public double getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}


	public double[] getModosPgto() {
		return modosPgto;
	}


	public void setModosPgto(double[] modosPgto) {
		this.modosPgto = modosPgto;
	}


	public double getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}


	public String getUnidade() {
		return unidade;
	}


	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}


	public String getCodItem() {
		return codItem;
	}


	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}


	public String getCliente() {
		return cliente;
	}


	public void setCliente(String cliente) {
		this.cliente = cliente;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	
}
