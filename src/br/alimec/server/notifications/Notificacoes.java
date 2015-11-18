package br.alimec.server.notifications;

public class Notificacoes {
	
	private static final String VENDA_SUCESSO_ICONE = "images/confirm.png";

	public static void vendaSucedida(){
		NotificationWindow.mostrarNotificacao(VENDA_SUCESSO_ICONE,"Venda realizada", "Venda realizada com sucesso, clique aqui para mais detalhes.");
	}
	
}
