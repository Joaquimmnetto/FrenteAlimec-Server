package br.joaquimmnetto.server.notifications;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotificationWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8207792478091715059L;
	private JLabel headerLabel;
	private JLabel messageLabel;
	
	private JButton closeButton;
	
	public static final long NOTIFICATION_TIME = 5000;
	
	//TODO:
	//not a real SO notification, just a JFrame well positioned.
	//assumes task bar is on bottom, do real notifications later(maybe invoke a C# program?)
	public static void mostrarNotificacao(String iconeLink, String cabecalho,String mensagem){
		final JFrame notification = new NotificationWindow(iconeLink, cabecalho, mensagem);	
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(notification.getGraphicsConfiguration());// height of the task bar
		notification.setLocation(scrSize.width - notification.getWidth(), scrSize.height - toolHeight.bottom - notification.getHeight());
		
		notification.setVisible(true);
		
		Timer closeTimer = new Timer();
		closeTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				notification.dispose();
			}
		}, NOTIFICATION_TIME);
		
	}
	
	
	private NotificationWindow(String iconeLink, String header, String message){
		
		
		setSize(300, 125);
		setResizable(false);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setLayout(new GridBagLayout());
		
		
		headerLabel = new JLabel(header);
		messageLabel = new JLabel("<HTML>"+message);
		
		closeButton = new JButton("X");
		closeButton.setMargin(new Insets(1, 4, 1, 4));
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationWindow.this.dispose();
			}
		});
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		headerLabel.setIcon(new ImageIcon(iconeLink));
		add(headerLabel,constraints);
		
		
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.NORTH;
		add(closeButton,constraints);
		
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(messageLabel,constraints);
		
	}
	
	

}
