package br.alimec.server.notifications;

public class NotificationTest {
	
	public static void main(String[] args) throws InterruptedException {
		Notificacoes.vendaSucedida();
		
		while(true){Thread.sleep(500);}
	}
	

}
