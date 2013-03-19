package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;

import db.MeetingPoint;

import java.awt.Insets;

public class MeetingPanel extends JPanel {
	
	private final ParticipantListPanel participantListPanel = new ParticipantListPanel();
	private MeetingPoint[] places = {};
	private JComboBox comboBox;
	
	
	public MeetingPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Participants");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
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
	
	public ParticipantListPanel getParticipants(){
		return this.participantListPanel.getParticipantList();
	}
	
	public MeetingPoint getMeetingPoint(){
		return (MeetingPoint) comboBox.getSelectedItem();
	}

}
