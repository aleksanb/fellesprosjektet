package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;

import db.MeetingPoint;
import db.User;

import java.awt.Insets;
import java.util.ArrayList;

public class MeetingPanel extends JPanel {
	
	private MeetingPoint[] places = {};
	protected JComboBox comboBox;
	protected ParticipantListPanel participantListPanel;
	
	
	public MeetingPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Participants");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		participantListPanel = new ParticipantListPanel();
		GridBagConstraints gbc_participantListPanel = new GridBagConstraints();
		gbc_participantListPanel.insets = new Insets(0, 0, 5, 0);
		gbc_participantListPanel.fill = GridBagConstraints.BOTH;
		gbc_participantListPanel.gridx = 0;
		gbc_participantListPanel.gridy = 1;
		add(participantListPanel, gbc_participantListPanel);
		
		JLabel lblPlace = new JLabel("Place");
		GridBagConstraints gbc_lblPlace = new GridBagConstraints();
		gbc_lblPlace.anchor = GridBagConstraints.WEST;
		gbc_lblPlace.insets = new Insets(0, 0, 5, 0);
		gbc_lblPlace.gridx = 0;
		gbc_lblPlace.gridy = 3;
		add(lblPlace, gbc_lblPlace);
		
		comboBox = new JComboBox(places);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 4;
		add(comboBox, gbc_comboBox);
	}
	
	public ArrayList<User> getParticipants(){
		return this.participantListPanel.getParticipantList();
	}
	
	public MeetingPoint getMeetingPoint(){
		return (MeetingPoint) comboBox.getSelectedItem();
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		MeetingPanel mp = new MeetingPanel();
		frame.getContentPane().add(mp);
		frame.pack();
		frame.setSize(300, 100);
		frame.setVisible(true);
	}

}
