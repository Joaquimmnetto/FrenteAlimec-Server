package br.alimec.server.connect;

public class ConexaoInfo {

	private String endereco;
	private int porta;

	public ConexaoInfo(String endereco, int porta) {
		this.endereco = endereco;
		this.porta = porta;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	@Override
	public String toString() {
		return endereco + ":" + porta;
	}

}
