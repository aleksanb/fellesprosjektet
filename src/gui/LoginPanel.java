package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import db.Factory;

public class LoginPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private Factory factory;

	/**
	 * Create the panel.
	 */
	public LoginPanel(Factory factory) {
		this.factory = factory;
		setBackground(new Color(255, 153, 0));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 50, 206, 0};
		gridBagLayout.rowHeights = new int[]{30, 20, 20, 23, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblUsername = new JLabel("Email:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 2;
		add(lblUsername, gbc_lblUsername);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(25);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 3;
		add(lblPassword, gbc_lblPassword);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 3;
		add(textField_1, gbc_textField_1);
		textField_1.setColumns(25);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new LoginAction(this));
		
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogin.anchor = GridBagConstraints.WEST;
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 4;
		add(btnLogin, gbc_btnLogin);

	} 
	class LoginAction implements ActionListener {
		LoginPanel lp;
		public LoginAction(LoginPanel lp){
			this.lp = lp;
		}
		public void actionPerformed(ActionEvent e) {
			if(factory.checkValid(textField.getText(),textField_1.getText())){
				lp.setVisible(false);
		}
	}

}
	 public static void main(String args[]){
		 	Factory f = new db.Factory();
			JFrame frame = new JFrame("...");
			frame.add(new LoginPanel(f));
			frame.pack();
			frame.setVisible(true);
		}    
}
